package com.mycompany.maventest;

import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;

public class OneVsAll {

    Matrix X;
    Matrix y;
    int num_labels;
    double lambda;
    int m;
    int n;
    Matrix Theta;
    CostGradient lrCostGrad;
    
    public OneVsAll(Matrix x, Matrix y, int n, double l){
        this.X = x;
        this.y = y;
        this.num_labels = n;
        this.lambda = l;
        this.m = x.numRows();
        this.n = x.numColumns();
        this.Theta = MTJExt.Zeros(this.n, num_labels);
        
        lrCostGrad = new CostGradient(this.X, this.y, this.lambda){
            
            @Override
            public double Cost(Matrix Theta){
                Matrix tempY = new DenseMatrix(y,true);
                double sumPos = (((tempY.scale(-1)).transpose(new DenseMatrix(1,m))).mult(MTJExt.logExtend(GenFunc.sigmoidEx(X.mult(Theta, new DenseMatrix(m,1)))), new DenseMatrix(1,1))).get(0,0);
                double sumNeg = (((MTJExt.minusExtend(MTJExt.single(1), y)).transpose(new DenseMatrix(1,m))).mult(MTJExt.logExtend(GenFunc.invSigmoidEx(X.mult(Theta, new DenseMatrix(m,1)))), new DenseMatrix(1,1))).get(0,0);
                return (((double)1)/m)*(sumPos-sumNeg)+(lambda/(2*m))*((MTJExt.sum(MTJExt.powExtend(GenFunc.splitMatrix(Theta, 1, -1, 0, -1), MTJExt.single(2)), 2)).get(0,0));
            }
            
            @Override
            public Matrix Gradient(Matrix Theta){
                Matrix temp = (X.transpose(new DenseMatrix(n,m))).mult(MTJExt.minusExtend(GenFunc.sigmoidEx(X.mult(Theta, new DenseMatrix(m,1))),y), new DenseMatrix(n,1));
                Matrix thetaMult = MTJExt.concat(MTJExt.single(0),MTJExt.Const(n-1, 1, lambda/m), 2);
                return (temp.scale((double)1/m)).add(MTJExt.timesExtend(Theta, thetaMult));
            }
        };
    }
    
    //add column of ones to X
    public void addBias(){
        X = MTJExt.concat(MTJExt.Ones(m, 1), X, 1);
        n++;
        Theta = MTJExt.Zeros(num_labels, n);
    }
    
    public void runRoutine(){
        Fmincg mincg = new Fmincg(lrCostGrad);
        for(int i = 0; i < num_labels; i++){
            Fmincg.FmincgRet temp = mincg.runRoutine(MTJExt.Zeros(this.n, 1), 50);
            Matrix t = temp.getX();
            for(int c = 0; c < this.n; c++)
                Theta.set(i, c, t.get(c, 0));
        }
    }
    
    public Matrix predict(Matrix z){
        Matrix temp = MTJExt.max(GenFunc.sigmoid(X.mult(Theta.transpose(new DenseMatrix(n,num_labels)), new DenseMatrix(m,num_labels))), 1);
        return GenFunc.splitMatrix(temp, 0, -1, 1, 1);
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
