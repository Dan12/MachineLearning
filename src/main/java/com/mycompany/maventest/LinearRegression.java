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
    
    public LinearRegression(Matrix x, Matrix y){
        this.X = x;
        this.y = y;
        this.m = x.getRowDimension();
        this.n = x.getColumnDimension();
        this.Theta = JAMAExtensions.Zeros(n, 1);
    }
    
    //add column of ones to X
    public void addBias(){
        X = JAMAExtensions.concat(JAMAExtensions.Ones(m, 1), X, 1);
        n++;
        Theta = JAMAExtensions.Zeros(n, 1);
    }
    
    //(1/2m)*(sum((X*Theta-y)^2))
    public double costFunction(){
        Matrix temp = (X.times(Theta)).minus(y);
        //Should be 1*1 matrix
        Matrix squaredSum = ((temp.transpose()).times(temp));
        return squaredSum.get(0,0)/(2*m);
    }
    
    //Normalize features
    public Matrix featureNoramlize(boolean set){
        mu = JAMAExtensions.mean(X, 2);
        sigma = JAMAExtensions.std(X, 2);
        Matrix muTemp = JAMAExtensions.Ones(m, 1).times(mu);
        Matrix sigTemp = JAMAExtensions.Ones(m, 1).times(sigma);
        Matrix ret = (X.minus(muTemp)).arrayRightDivide(sigTemp);
        if(set)
            X = ret;
        return ret;
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
    
    public double predict(Matrix a){
        return a.times(Theta).get(0, 0);
    }
    
    public Matrix getTheta(){
        return Theta;
    }
}
