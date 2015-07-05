package com.mycompany.maventest;

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
    
    public LogisticRegression(Matrix x, Matrix y){
        this.X = x;
        this.y = y;
        this.m = X.numRows();
        this.n = X.numColumns();
        Theta = MTJExt.Zeros(n, 1);
    }
    
    public Matrix sigmoid(Matrix z){
        return z;
    }

}
