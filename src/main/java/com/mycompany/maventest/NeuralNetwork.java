package com.mycompany.maventest;

import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;

public class NeuralNetwork {

    private Matrix X;
    private Matrix y;
    private int m;
    private int n;
    private int numLabels;
    private int[] hiddenSize;
    private int hiddenLayers;
    private Matrix[] Theta;
    
    public NeuralNetwork(Matrix x, Matrix y, int n, int h, int[] hs){
        this.X = x;
        this.y = y;
        this.m = this.X.numRows();
        this.X = MTJExt.concat(MTJExt.Ones(m, 1), X, 1);
        this.n = this.X.numColumns();
        this.numLabels = n;
        this.hiddenLayers = h;
        this.hiddenSize = hs;
        Theta = new Matrix[hiddenLayers+1];
        Theta[0] = MTJExt.Zeros(hiddenSize[0],this.n);
        for(int i = 1; i < Theta.length; i++){
            if(i == Theta.length-1)
                Theta[i] = MTJExt.Zeros(numLabels, hiddenSize[i-1]+1);
            else
                Theta[i] = MTJExt.Zeros(hiddenSize[i], hiddenSize[i-1]+1);
        }
    }
    
    public void loadWeights(String fname){
        for(int i = 0; i < Theta.length; i++){
            //System.out.println(Theta[i].numRows()+","+Theta[i].numColumns());
            Theta[i] = ReadMatFile.readFile(fname, "Theta"+(i+1));
            //System.out.println(Theta[i].numRows()+","+Theta[i].numColumns());
            //System.out.println("--------");
        }
    }
    
    public Matrix forwardPropagate(Matrix z){
        Matrix input = MTJExt.concat(MTJExt.Ones(z.numRows(), 1), z, 1);
        Matrix tempPrev = MTJExt.concat(MTJExt.Ones(z.numRows(), 1), GenFunc.sigmoid(input.mult((Theta[0]).transpose(new DenseMatrix(Theta[0].numColumns(), Theta[0].numRows())), new DenseMatrix(input.numRows(), Theta[0].numRows()))), 1);
        for(int i = 1; i < Theta.length; i++){
            tempPrev = GenFunc.sigmoid(tempPrev.mult((Theta[i]).transpose(new DenseMatrix(Theta[i].numColumns(), Theta[i].numRows())), new DenseMatrix(tempPrev.numRows(), Theta[i].numRows())));
            if(i < Theta.length-1)
                tempPrev = MTJExt.concat(MTJExt.Ones(tempPrev.numRows(), 1), tempPrev, 1);
        }
        return tempPrev;
    }
    
    public Matrix predict(Matrix z){
        Matrix result = forwardPropagate(z);
        Matrix temp = MTJExt.max(result, 1);
        return GenFunc.splitMatrix(temp, 0, -1, 1, 1);
    }
    
    public Matrix getTheta(int i){
        return Theta[i];
    }
    
    public Matrix[] getAllTheta(){
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
