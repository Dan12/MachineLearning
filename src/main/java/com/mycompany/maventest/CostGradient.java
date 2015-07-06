package com.mycompany.maventest;

import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;

public class CostGradient {

    public int m;
    public int n;
    public Matrix X;
    public Matrix y;
    public double lambda;

    public CostGradient(Matrix x, Matrix y, double l){
        this.X = x;
        this.y = y;
        this.m = X.numRows();
        this.n = X.numColumns();
        lambda = l;
    }

    public double Cost(Matrix Theta){
        return 0.0;
    }

    public Matrix Gradient(Matrix Theta){
        return new DenseMatrix(1,1);
    }

}