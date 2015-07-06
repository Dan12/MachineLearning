package com.mycompany.maventest;

import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;

public class Fmincg {

    private CostGradient cg;
    
    public Fmincg(CostGradient cg){
        this.cg = cg;
    }
    
    public double getCost(Matrix Theta){
        return cg.Cost(Theta);
    }
    
    public Matrix getGradient(Matrix Theta){
        return cg.Gradient(Theta);
    }
    
    public FmincgRet runRoutine(Matrix init, int iter){
        FmincgRet ret = new FmincgRet(new DenseMatrix(new double[][]{{}}), new DenseMatrix(new double[][]{{}}), 0);
        
        int length = iter;
        
        double RHO = 0.01;  //a bunch of constants for line searches
        double SIG = 0.5;   //RHO and SIG are the constants in the Wolfe-Powell conditions
        double INT = 0.1;   //don't reevaluate within 0.1 of the limit of the current bracket
        double EXT = 3.0;   //extrapolate maximum 3 times the current bracket
        double MAX = 20;    //max 20 function evaluations per line search
        double RATIO = 100; //maximum allowed slope ratio
        
        double red = 1.0;
        
        int i = 0;  //zero the run length counter
        boolean ls_failed = false;  //no previous line search has failed
        double f1 = getCost(init);  Matrix df1 = getGradient(init); //get function value and gradient
        Matrix s = new DenseMatrix(df1,true).scale(-1); //search direction is steepest
        double d1 = (((s.transpose(new DenseMatrix(s.numColumns(), s.numRows()))).scale(-1)).mult(s, new DenseMatrix(1,1))).get(0,0);   //this is the slope
        double z1 = red/(1-d1); //initial step is red/(|s|+1)
        
        while(i < length){  //while not finished
            i++;    //count iterations
            
            Matrix X0 = new DenseMatrix(init,true); double f0 = f1; Matrix df0 = new DenseMatrix(df1);  //make a copy of current values
            init.add(new DenseMatrix(s,true).scale(z1));    //begin line search
            double f2 = getCost(init);  Matrix df2 = getGradient(init);
            double d2 = ((df2.transpose(new DenseMatrix(df2.numColumns(), df2.numRows()))).mult(s, new DenseMatrix(1,1))).get(0,0);
            double f3 = f1; double d3 = d1; double z3 = -z1;    //initialize point 3 equal to point 1
            double M = MAX;
            boolean success = false; double limit = -1; //initialize quanteties
            while(true){
                while (((f2 > f1+z1*RHO*d1) || (d2 > -SIG*d1)) && (M > 0)){
                    limit = z1; //tighten the bracket
                    double z2 = -1;
                    if(f2 > f1)
                        z2 = z3 - (0.5*d3*z3*z3)/(d3*z3+f2-f3); //quadratic fit
                    else{
                        double A = 6*(f2-f3)/z3+3*(d2+d3);  //cubic fit
                        double B = 3*(f3-f2)-z3*(d3+2*d2);
                        z2 = (Math.sqrt(B*B-A*d2*z3*z3)-B)/A;    //numerical error possible - ok!
                    }
                    if(Double.isNaN(z2) || Double.isInfinite(z2))
                        z2 = z3/2;  //if we had a numerical problem then bisect
                    z2 = Math.max(Math.min(z2, INT*z3),(1-INT)*z3); //don't accept too close to limits
                    z1 = z1 + z2;   //update the step
                    init.add(new DenseMatrix(s,true).scale(z2));
                    f2 = getCost(init); df2 = getGradient(init);
                    M--;
                    d2 = ((df2.transpose(new DenseMatrix(df2.numColumns(), df2.numRows()))).mult(s, new DenseMatrix(1,1))).get(0,0);
                    z3 = z3-z2; //z3 is now relative to the location of z2
                    break;
                }
                if (f2 > f1+z1*RHO*d1 || d2 > -SIG*d1)
                    break;  //this is a failure
                else if (d2 > SIG*d1){
                    success = true; 
                    break; //% success
                }
                else if (M == 0)
                    break;  //failure
                double A = 6*(f2-f3)/z3+3*(d2+d3); //make cubic extrapolation
                double B = 3*(f3-f2)-z3*(d3+2*d2);
                double z2 = -d2*z3*z3/(B+Math.sqrt(B*B-A*d2*z3*z3)); //num. error possible - ok!
                if(Double.isNaN(z2) || Double.isInfinite(z2) || z2 < 0){  //% num prob or wrong sign?
                    if (limit < -0.5) //if we have no upper limit
                        z2 = z1 * (EXT-1);  //the extrapolate the maximum amount
                    else
                      z2 = (limit-z1)/2;    //otherwise bisect  
                }
                else if((limit > -0.5) && (z2+z1 > limit)) //extraplation beyond max?
                    z2 = (limit-z1)/2;  //bisect
                else if((limit < -0.5) && (z2+z1 > z1*EXT)) //extrapolation beyond limit
                    z2 = z1*(EXT-1.0);  //set to extrapolation limit
                else if(z2 < -z3*INT)
                    z2 = -z3*INT;
                else if((limit > -0.5) && (z2 < (limit-z1)*(1.0-INT))) //too close to limit?
                    z2 = (limit-z1)*(1.0-INT);
                f3 = f2; d3 = d2; z3 = -z2; //set point 3 equal to point 2
                z1 = z1 + z2; init.add(new DenseMatrix(s,true).scale(z2)); //update current estimates
                f2 = getCost(init); df2 = getGradient(init);
                M--;
                d2 = ((df2.transpose(new DenseMatrix(df2.numColumns(), df2.numRows()))).mult(s, new DenseMatrix(1,1))).get(0,0);
                break;
            }   //end of line search
            
            if(success){    //if line search succeeded
                f1 = f2; ret.setfX(MTJExt.concat(ret.getfX(), MTJExt.single(f1), 2));
                System.out.println("Iteration: "+i+" | Cost: "+f1);
            }
            break;
        }
        
        return ret;
    }
    
    public class FmincgRet{
        
        private Matrix X;
        
        private Matrix fX;
        
        private int i;
        
        public FmincgRet(Matrix x, Matrix f, int i){
            this.X = x;
            this.fX = f;
            this.i = i;
        }
        
        public void setX(Matrix x){
            this.X = x;
        }
        
        public Matrix getX(){
            return this.X;
        }
        
        public void setfX(Matrix f){
            this.fX = f;
        }
        
        public Matrix getfX(){
            return this.fX;
        }
        
        public void setI(int i){
            this.i = i;
        }
        
        public int getI(){
            return this.i;
        }
    
    }

}
