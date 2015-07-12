package com.mycompany.maventest;

import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrices;
import no.uib.cipr.matrix.Matrix;

public class LinearRegression {

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
    
    //Matrix of average X values for each feature
    private Matrix mu;
    
    //Matrix of standard deviations of each feature
    private Matrix sigma;
    
    public LinearRegression(Matrix x, Matrix y){
        this.X = x;
        this.y = y;
        this.m = x.numRows();
        this.n = x.numColumns();
        this.Theta = MTJExt.Zeros(n, 1);
    }
    
    //add column of ones to X
    public void addBias(){
        X = MTJExt.concat(MTJExt.Ones(m, 1), X, 1);
        n++;
        Theta = MTJExt.Zeros(n, 1);
    }
    
    //(1/2m)*(sum((X*Theta-y)^2))
    public double costFunction(){
        Matrix temp = MTJExt.minusExtend(X.mult(Theta, new DenseMatrix(m,1)), y);
        //Should be 1*1 matrix
        Matrix squaredSum = temp.transAmult(temp, new DenseMatrix(1,1));
        return squaredSum.get(0,0)/(2*m);
    }
    
    //Normalize features
    public void featureNoramlize(){
        mu = MTJExt.mean(X, 2);
        sigma = MTJExt.std(X, 2);
        X = GenFunc.featureNormalize(X, mu, sigma);
    }

    //perform gradient descent with learning rate alpha for iterns iterations, returns cost history if rec is true
    public Matrix gradientDescent(int iterns, double alpha, boolean rec){
        double[][] costHist = new double[iterns][1];
        for(int i = 0; i < iterns; i++){
            Matrix temp = X.transAmult(MTJExt.minusExtend(X.mult(Theta, new DenseMatrix(m,1)),y), new DenseMatrix(n,1));
            Theta = MTJExt.minusExtend(Theta,temp.scale(alpha/m));
            if(rec)
                costHist[i][0] = costFunction();
        }
        return new DenseMatrix(costHist);
    }
    
    //caluclate minumum using the normal equation
    public void normalEquation(){
        Theta = (((X.transAmult(X, new DenseMatrix(n,n))).solve(Matrices.identity(n), new DenseMatrix(n,n))).transBmult(X, new DenseMatrix(n,m))).mult(y, new DenseMatrix(n,1));
    }
    
    //predict output of a using current values of theta
    public double predict(Matrix a){
        return a.mult(Theta,new DenseMatrix(1,1)).get(0, 0);
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
    
    public Matrix getMu(){
        return mu;
    }
    
    public Matrix getSigma(){
        return sigma;
    }
    
    public int getM(){
        return m;
    }
    
    public int getN(){
        return n;
    }
    
}
