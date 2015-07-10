package com.mycompany.maventest;

import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;

public class LinearRegressionTests {

    Matrix X, y, Xval, yval, Xtest, ytest; 
    Matrix Theta;
    Matrix mu, sigma;
    int m; int mval; int mtest;
    double lambda;
    CostGradient cg;
    
    public LinearRegressionTests(Matrix x, Matrix y){
        int numExamples = x.numRows();
        this.m = (int) (numExamples*0.6);
        this.mval = (int) (numExamples*0.2);
        this.mtest = numExamples-(m+mval);
        this.X = GenFunc.splitMatrix(x, 0, m-1, 0, -1);
        this.y = GenFunc.splitMatrix(y, 0, m-1, 0, -1);
        this.Xval = GenFunc.splitMatrix(x, m, m+mval-1, 0, -1);
        this.yval = GenFunc.splitMatrix(y, m, m+mval-1, 0, -1);
        this.Xtest = GenFunc.splitMatrix(x, m+mval, -1, 0, -1);
        this.ytest = GenFunc.splitMatrix(y, m+mval, -1, 0, -1);
    }
    
    public LinearRegressionTests(Matrix x, Matrix y, Matrix xv, Matrix yv, Matrix xt, Matrix yt){
        this.X = x;
        this.y = y;
        this.Xval = xv;
        this.yval = yv;
        this.Xtest = xt;
        this.ytest = yt;
        this.m = x.numRows();
        this.mval = xv.numRows();
        this.mtest = xt.numRows();
    }
    
    public void runTests(){
        visualizeTrain();
        //System.out.println(polyFeatures(X, 4));
    }
    
    private Matrix polyFeatures(Matrix x, int p){
        Matrix ret = GenFunc.splitMatrix(x, 0, -1, 0, 0).mult(MTJExt.Ones(1, p), new DenseMatrix(x.numRows(),p));
        return MTJExt.powExtend(ret, MTJExt.Range(1, 1, p));
    }
    
    public void visualizeTrain(){
        final int deg = 8;
        Graph2D g2d = new Graph2D(X, y);
        g2d.setMode(2);
        setCostGradient(biased(featureNoramlize(polyFeatures(X, deg))), y, 0);
        Fmincg min = new Fmincg(cg);
        Fmincg.FmincgRet temp = min.runRoutine(MTJExt.Zeros(deg+1, 1), 500);
        final Matrix theta = temp.getX();
        System.out.println(theta);
        g2d.setEquation(new Equation(){
            @Override
            public double getYValue(double x){
                Matrix xMat = GenFunc.featureNormalize(polyFeatures(MTJExt.single(x), deg), mu, sigma);
                xMat = MTJExt.concat(MTJExt.Ones(1, 1), xMat, 1);
                return xMat.mult(theta, new DenseMatrix(1,1)).get(0,0);
            }
        });
        g2d.showGraph();
    }
    
    public void setLambda(double l){
        lambda = l;
        cg.lambda = l;
    }
    
    //Normalize features
    public Matrix featureNoramlize(Matrix x){
        mu = MTJExt.mean(x, 2);
        sigma = MTJExt.std(x, 2);
        System.out.println(mu.numRows()+","+mu.numColumns());
        return GenFunc.featureNormalize(x, mu, sigma);
    }
    
    public Matrix biased(Matrix x){
        return MTJExt.concat(MTJExt.Ones(x.numRows(),1), x, 1);
    }
    
    private void setCostGradient(Matrix x, Matrix y, double l){
        cg = new CostGradient(x, y, l){
            
            @Override
            public double Cost(Matrix Theta){
                double cost = ((double)1/(2*m))*(MTJExt.sum(MTJExt.powExtend(MTJExt.minusExtend(X.mult(Theta, new DenseMatrix(m,1)), y), MTJExt.single(2)), 2)).get(0, 0);
                cost+=(lambda/(2*m))*(MTJExt.sum(MTJExt.powExtend(GenFunc.splitMatrix(Theta, 1, -1, 0, -1), MTJExt.single(2)), 2)).get(0,0);
                return cost;
            }
            
            @Override
            public Matrix Gradient(Matrix Theta){
                Matrix ret = X.transpose(new DenseMatrix(n,m)).mult(MTJExt.minusExtend(X.mult(Theta, new DenseMatrix(m,1)), y), new DenseMatrix(n,1));
                ret.scale((double)1/m);
                Matrix thetaMult = MTJExt.concat(MTJExt.single(0),MTJExt.Const(n-1, 1, lambda/m), 2);
                ret.add(MTJExt.timesExtend(thetaMult, Theta));
                return ret;
            }
        };
    }

}
