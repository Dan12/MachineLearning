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
        FmincgRet ret = new FmincgRet(new DenseMatrix(1,1), new DenseMatrix(1,1), 0);
        
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
        double d1 = (((s.transpose(new DenseMatrix(s.numColumns(), s.numRows()))).scale(-1)).mult(s, new DenseMatrix(1,1))).get(0,0);
        double z1 = red/(1-d1);
        
        while(i < length){  //while not finished
            i++;    //count iterations
            
            Matrix X0 = new DenseMatrix(init,true); double f0 = f1; Matrix df0 = new DenseMatrix(df1);  //make a copy of current values
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
