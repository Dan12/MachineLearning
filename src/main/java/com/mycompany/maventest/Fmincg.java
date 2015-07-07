/*Minimize a continuous differentialble multivariate function. Starting point
* is given by "X" (D by 1), and the function named in the string "f", must
* return a function value and a vector of partial derivatives. The Polack-
* Ribiere flavour of conjugate gradients is used to compute search directions,
* and a line search using quadratic and cubic polynomial approximations and the
* Wolfe-Powell stopping criteria is used together with the slope ratio method
* for guessing initial step sizes. Additionally a bunch of checks are made to
* make sure that exploration is taking place and that extrapolation will not
* be unboundedly large. The "length" gives the length of the run: if it is
* positive, it gives the maximum number of line searches, if negative its
* absolute gives the maximum allowed number of function evaluations. You can
* (optionally) give "length" a second component, which will indicate the
* reduction in function value to be expected in the first line-search (defaults
* to 1.0). The function returns when either its length is up, or if no further
* progress can be made (ie, we are at a minimum, or so close that due to
* numerical problems, we cannot get any closer). If the function terminates
* within a few iterations, it could be an indication that the function value
* and derivatives are not consistent (ie, there may be a bug in the
* implementation of your "f" function). The function returns the found
* solution "X", a vector of function values "fX" indicating the progress made
* and "i" the number of iterations (line searches or function evaluations,
* depending on the sign of "length") used.
*
* Usage: [X, fX, i] = fmincg(CostCradient function, initial values, iterations)
*
* Copyright (C) 2001 and 2002 by Carl Edward Rasmussen. Date 2002-02-13
*
*
* (C) Copyright 1999, 2000 & 2001, Carl Edward Rasmussen
* 
* Permission is granted for anyone to copy, use, or modify these
* programs and accompanying documents for purposes of research or
* education, provided this copyright notice is retained, and note is
* made of any changes that have been made.
* 
* These programs and documents are distributed without any warranty,
* express or implied.  As the programs were written for research
* purposes only, they have not been tested to the degree that would be
* advisable in any important application.  All use of these programs is
* entirely at the user's own risk.
*
* [ml-class] Changes Made:
* 1) Function name and argument specifications
* 2) Output display
*
* Daniel Weber Changes Made:
* 1) Changed and added output format
* 2) Changed input format
* 3) Ported to java*/


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
                    //break;  //TODO: get rid of break
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
                //break;  //TODO: get rid of break
            }   //end of line search
            
            if(success){    //if line search succeeded
                f1 = f2; 
                if(ret.getfX().numColumns()== 0)    //fX is empty
                    ret.setfX(MTJExt.single(f1));
                else
                    ret.setfX(MTJExt.concat(ret.getfX(), MTJExt.single(f1), 2));
                System.out.println("Iteration: "+i+" | Cost: "+f1);
                double part1 = (MTJExt.minusExtend(df2.transpose(new DenseMatrix(df2.numColumns(), df2.numRows())).mult(df2, new DenseMatrix(1,1)), df1.transpose(new DenseMatrix(df1.numColumns(),df1.numRows())).mult(df2, new DenseMatrix(1,1)))).get(0,0);
                double part2 = (df1.transpose(new DenseMatrix(df1.numColumns(), df1.numRows())).mult(df1, new DenseMatrix(1,1))).get(0,0);
                s = MTJExt.minusExtend(new DenseMatrix(s,true).scale(part1/part2), df2);    //Polack-Ribiere direction
                Matrix tmp = df1; df1 = df2; df2 = tmp;    //swap derivatives
                d2 = (df1.transpose(new DenseMatrix(df1.numColumns(),df1.numRows())).mult(s, new DenseMatrix(1,1))).get(0,0);
                if(d2 > 0){ //new slope must be negative
                    s = new DenseMatrix(df1,true).scale(-1);    //otherwise use steepest direction
                    d2 = (((s.transpose(new DenseMatrix(s.numColumns(), s.numRows()))).scale(-1)).mult(s, new DenseMatrix(1,1))).get(0,0);
                }
                z1 = z1 * Math.min(RATIO, d1/(d2-0)); //slope ratio but max RATIO, 0 supposed to be realmin(2.2251e-308 for double precision and 1.1755e-38 for single precision)
                d1 = d2;
                ls_failed = false;  //this line search did not fail
            }
            else{
                init = X0; f1 = f0; df1 = df0; //restore point from before failed line search
                if (ls_failed || i > length) //line search failed twice in a row
                  break;    //or we ran out of time, so we give up
                Matrix tmp = df1; df1 = df2; df2 = tmp;    //swap derivatives
                s = new DenseMatrix(df1, true).scale(-1);   //try steepest
                d1 = (((s.transpose(new DenseMatrix(s.numColumns(), s.numRows()))).scale(-1)).mult(s, new DenseMatrix(1,1))).get(0,0);
                z1 = 1/(1-d1);                     
                ls_failed = true;   //this line search failed
            }
            //break;  //TODO: get rid of break
        }
        ret.setI(i);
        ret.setX(init);
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
