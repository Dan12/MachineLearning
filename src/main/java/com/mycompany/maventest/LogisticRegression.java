package com.mycompany.maventest;

import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;

public class LogisticRegression {

    //# Training examples
    private int m;
    
    //# of features
    private int n;
    
    //Inputs (m*n)
    private Matrix X;
    
    //Outputs (m*1)
    private Matrix y;
    
    //Values to fit training data (n*1)
    private Matrix Theta;
    
    private double lambda;
    
    public LogisticRegression(Matrix x, Matrix y){
        this.X = x;
        this.y = y;
        this.m = X.numRows();
        this.n = X.numColumns();
        Theta = MTJExt.Zeros(n, 1);
    } 
    
    //add column of ones to X
    public void addBias(){
        X = MTJExt.concat(MTJExt.Ones(m, 1), X, 1);
        n++;
        Theta = MTJExt.Zeros(n, 1);
    }
    
    //set lambda, regularization parameter
    public void setLambda(double l){
        lambda = l;
    }
    
    //cost function, convex
    public double costFunction(){
        Matrix tempY = new DenseMatrix(y,true);
        double sumPos = (((tempY.scale(-1)).transpose(new DenseMatrix(1,m))).mult(MTJExt.logExtend(GenFunc.sigmoid(X.mult(Theta, new DenseMatrix(m,1)))), new DenseMatrix(1,1))).get(0,0);
        double sumNeg = (((MTJExt.minusExtend(MTJExt.single(1), y)).transpose(new DenseMatrix(1,m))).mult(MTJExt.logExtend(MTJExt.minusExtend(MTJExt.single(1), GenFunc.sigmoid(X.mult(Theta, new DenseMatrix(m,1))))), new DenseMatrix(1,1))).get(0,0);
        return (((double)1)/m)*(sumPos-sumNeg)+(lambda/(2*m))*((MTJExt.sum(MTJExt.powExtend(GenFunc.splitMatrix(Theta, 1, -1, 0, -1), MTJExt.single(2)), 2)).get(0,0));
        //return (((double)1)/m)*(sumPos-sumNeg);
    }
    
    public Matrix gradients(double alpha){
        Matrix temp = (X.transpose(new DenseMatrix(n,m))).mult(MTJExt.minusExtend(GenFunc.sigmoid(X.mult(Theta, new DenseMatrix(m,1))),y), new DenseMatrix(n,1));
        Matrix thetaMult = MTJExt.concat(MTJExt.single(0),MTJExt.Const(n-1, 1, lambda/m), 2);
        return (temp.scale(alpha/m)).add(MTJExt.timesExtend(Theta, thetaMult));
    }
    
    public Matrix gradientDescent(int iterations, double alpha, boolean rec){
        double[][] costHist = new double[iterations][1];
        for(int i = 0; i < iterations; i++){
            Theta = MTJExt.minusExtend(Theta,gradients(alpha));
            if(rec)
                costHist[i][0] = costFunction();
        }
        return new DenseMatrix(costHist);
    }
    
    public Matrix predict(Matrix z){
        return MTJExt.roundExtend(GenFunc.sigmoid(z.mult(Theta, new DenseMatrix(z.numRows(),1))));
    }
    
    public Matrix getTheta(){
        return Theta;
    }
    
    public Matrix getX(){
        return X;
    }
    
    public Matrix getY(){
        return y;
    }
    
    public int getM(){
        return m;
    }
    
    public int getN(){
        return n;
    }

}
