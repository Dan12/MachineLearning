package com.mycompany.maventest;

import com.mycompany.JAMA.Matrix;

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
    
    public LinearRegression(Matrix x, Matrix y, Matrix t){
        this.X = x;
        this.y = y;
        this.Theta = t;
        this.m = x.getRowDimension();
        this.n = x.getColumnDimension();
    }
    
    //add column of ones to X
    public void addBias(){
        X = JAMAExtensions.concat(JAMAExtensions.Ones(m, 1), X, 1);
    }
    
    //(1/2m)*(sum((X*Theta-y)^2))
    public double costFunction(){
        Matrix temp = (X.times(Theta)).minus(y);
        //Should be 1*1 matrix
        double squaredSum = (temp.transpose().times(temp)).get(0, 0);
        return squaredSum/(2*m);
    }
    
    
    public Matrix featureNoramlize(){
        mu = JAMAExtensions.mean(X, 2);
        sigma = JAMAExtensions.std(X, 2);
        return (X.minus(JAMAExtensions.Ones(m, 1).times(mu))).arrayRightDivide(JAMAExtensions.Ones(m, 1).times(sigma));
    }

    public Matrix gradientDescent(int iterations, double alpha){
        Matrix costHist = new Matrix(iterations,1);
        for(int i = 0; i < iterations; i++){
            Matrix temp = (X.transpose()).times((X.times(Theta)).minus(y));
            Theta = Theta.minus(temp.times(alpha/m));
            costHist.getArray()[i][0] = costFunction();
        }
        return costHist;
    }
    
    public void normalEquation(){
        Matrix temp = X.transpose();
        Theta = (((temp.times(X)).inverse()).times(temp)).times(y);
    }
}
