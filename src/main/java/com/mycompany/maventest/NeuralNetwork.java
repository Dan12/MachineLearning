package com.mycompany.maventest;

import java.util.Random;
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
    private Matrix[] zs;
    private Matrix[] activations;
    private double lambda;
    private CostGradient lrCostGrad;
    private Matrix[] allTheta;
    
    public NeuralNetwork(Matrix x, Matrix y, int n, int h, int[] hs, double l){
        this.X = x;
        this.y = y;
        this.m = this.X.numRows();
        this.X = MTJExt.concat(MTJExt.Ones(m, 1), X, 1);
        this.n = this.X.numColumns();
        this.numLabels = n;
        this.hiddenLayers = h;
        this.hiddenSize = hs;
        this.lambda = l;
        //map y to values (0 or 1)
        if(this.y.numColumns() == 1)
            this.y = MTJExt.equalsExtend(this.y.mult(MTJExt.Ones(1, this.numLabels), new DenseMatrix(this.m,this.numLabels)), MTJExt.Ones(this.m, 1).mult(MTJExt.Range(1, 1, numLabels), new DenseMatrix(this.m, this.numLabels)));
        this.Theta = new Matrix[hiddenLayers+1];
        this.allTheta = new Matrix[hiddenLayers+1];
        this.zs = new Matrix[hiddenLayers+1];
        this.activations = new Matrix[hiddenLayers+1];
        Theta[0] = MTJExt.Zeros(hiddenSize[0],this.n);
        for(int i = 1; i < Theta.length; i++){
            if(i == Theta.length-1)
                Theta[i] = MTJExt.Zeros(numLabels, hiddenSize[i-1]+1);
            else
                Theta[i] = MTJExt.Zeros(hiddenSize[i], hiddenSize[i-1]+1);
        }
        setCostGradient();
    }
    
    //Load weigths from file fname, file must contain matirx saved in format Theta1, Theta2,...
    public void loadWeights(String fname){
        for(int i = 0; i < Theta.length; i++){
            //System.out.println(Theta[i].numRows()+","+Theta[i].numColumns());
            Theta[i] = MatFileInt.readFile(fname, "Theta"+(i+1));
            //System.out.println(Theta[i].numRows()+","+Theta[i].numColumns());
            //System.out.println("--------");
        }
    }
    
    //Set cost gradient to be used
    private void setCostGradient(){
        lrCostGrad = new CostGradient(this.X, this.y, this.lambda){
            
            @Override
            public double Cost(Matrix Theta){
                //Matrix[] allTheta = new Matrix[NeuralNetwork.this.hiddenLayers+1];
                int sumRows = 0;
                for(int i = 0; i < allTheta.length; i++){
                    allTheta[i] = GenFunc.reshape(Theta, sumRows, sumRows+NeuralNetwork.this.Theta[i].numRows()*NeuralNetwork.this.Theta[i].numColumns()-1, NeuralNetwork.this.Theta[i].numRows(), NeuralNetwork.this.Theta[i].numColumns());
                    sumRows+=NeuralNetwork.this.Theta[i].numRows()*NeuralNetwork.this.Theta[i].numColumns();
                }
                zs[0] = X.transBmult(allTheta[0], new DenseMatrix(X.numRows(),allTheta[0].numRows()));
                activations[0] = MTJExt.concat(MTJExt.Ones(m, 1), GenFunc.sigmoid(zs[0]), 1);
                zs[0] = MTJExt.concat(MTJExt.Ones(m, 1), zs[0], 1);
                for(int i = 1; i < allTheta.length; i++){
                    zs[i] = activations[i-1].transBmult(allTheta[i], new DenseMatrix(activations[i-1].numRows(), allTheta[i].numRows()));
                    //zs[i] = activations[i-1].mult(allTheta[i].transpose(new DenseMatrix(allTheta[i].numColumns(), allTheta[i].numRows())), new DenseMatrix(activations[i-1].numRows(), allTheta[i].numRows()));
                    activations[i] = GenFunc.sigmoid(zs[i]);
                    if(i < allTheta.length-1){
                        activations[i] = MTJExt.concat(MTJExt.Ones(activations[i].numRows(), 1), activations[i], 1);
                        zs[i] = MTJExt.concat(MTJExt.Ones(zs[i].numRows(), 1), zs[i], 1);
                    }
                }
                Matrix sumPos = MTJExt.sum(MTJExt.timesExtend(new DenseMatrix(y,true).scale(-1), MTJExt.logExtend(GenFunc.sigmoidEx(zs[zs.length-1]))), 1);
                Matrix sumNeg = MTJExt.sum(MTJExt.timesExtend(MTJExt.minusExtend(MTJExt.single(1), y), MTJExt.logExtend(GenFunc.invSigmoidEx(zs[zs.length-1]))), 1);
                double cost = ((double)1/m)*(MTJExt.sum(MTJExt.minusExtend(sumPos, sumNeg), 2)).get(0,0);
                for(int i = 0; i < allTheta.length; i++){
                    cost+=(lambda/(2*m))*MTJExt.sum(MTJExt.toVector(MTJExt.powExtend(GenFunc.splitMatrix(allTheta[i], 0, -1, 1, -1), MTJExt.single(2))), 2).get(0, 0);
                }
                return cost;
            }
            
            @Override
            public Matrix Gradient(Matrix Theta){
                Matrix[] ThetaGrad = new Matrix[NeuralNetwork.this.Theta.length];
                for(int i = 0; i < ThetaGrad.length; i++){
                    ThetaGrad[i] = MTJExt.Zeros(allTheta[i].numRows(), allTheta[i].numColumns());
                }
                for(int i = 0; i < m; i++){
//                    long st = System.nanoTime();
                    Matrix[] delta = new Matrix[ThetaGrad.length];
                    delta[delta.length-1] = MTJExt.minusExtend(MTJExt.toVector(GenFunc.splitMatrix(activations[activations.length-1], i, i, 0, -1)), MTJExt.toVector(GenFunc.splitMatrix(y, i, i, 0, -1)));
//                    System.out.println("MS Time: "+(System.nanoTime()-st)/(double)1000000);
//                    st = System.nanoTime();
                    for(int d = delta.length-2; d >= 0; d--){
                        delta[d] = GenFunc.splitMatrix(MTJExt.timesExtend(allTheta[d+1].transAmult(delta[d+1], new DenseMatrix(allTheta[d+1].numColumns(), delta[d+1].numColumns())), GenFunc.sigmoidGradient(GenFunc.splitMatrix(zs[d], i, i, 0, -1)).transpose(new DenseMatrix(zs[d].numColumns(),1))), 1, -1, 0, -1);   
                    }
//                    System.out.println("MS Time: "+(System.nanoTime()-st)/(double)1000000);
//                    st = System.nanoTime();
                    ThetaGrad[0].add(delta[0].mult(GenFunc.splitMatrix(X, i, i, 0, -1), new DenseMatrix(delta[0].numRows(),X.numColumns())));
//                    System.out.println("MS Time: "+(System.nanoTime()-st)/(double)1000000);
//                    st = System.nanoTime();
                    for(int t = 1; t < ThetaGrad.length; t++){
                        ThetaGrad[t].add(delta[t].mult(GenFunc.splitMatrix(activations[t-1], i, i, 0, -1), new DenseMatrix(delta[t].numRows(),activations[t-1].numColumns())));
                    }
//                    System.out.println("MS Time: "+(System.nanoTime()-st)/(double)1000000);
//                    System.out.println("---------");
                }
                for(int t = 0; t < ThetaGrad.length; t++){
                    ThetaGrad[t].scale((double)1/m);
                    Matrix thetaMult = MTJExt.concat(MTJExt.single(0), MTJExt.Const(1, allTheta[t].numColumns()-1, lambda/m), 1);
                    ThetaGrad[t].add(MTJExt.timesExtend(allTheta[t], thetaMult));
                }
                return GenFunc.unroll(ThetaGrad);
            }
        };
    }
    
    //initialize random starting points with variance epsilon
    public void initRandomTheta(){
        double epsilonInit = 0.12;
        Random rand = new Random();
        for(int t = 0; t < Theta.length; t++){
            for(int r = 0; r < Theta[t].numRows(); r++){
                for(int c = 0; c < Theta[t].numColumns(); c++){
                    Theta[t].set(r, c, rand.nextDouble()*2*epsilonInit-epsilonInit);
                }
            }
        }
    }
    
    //run fmincg routine on the neural network and store result in Theta
    public void runRoutine(int iterns){
        initRandomTheta();
        Fmincg mincg = new Fmincg(lrCostGrad);
        Fmincg.FmincgRet temp = mincg.runRoutine(GenFunc.unroll(Theta), iterns);
        Matrix t = temp.getX();
        int sumRows = 0;
        for(int i = 0; i < Theta.length; i++){
            Theta[i] = GenFunc.reshape(t, sumRows, sumRows+Theta[i].numRows()*Theta[i].numColumns()-1, Theta[i].numRows(), Theta[i].numColumns());
            sumRows+=Theta[i].numRows()*Theta[i].numColumns();
        }
    }
    
    //Forward propegate input z through the neural network with the current weights
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
    
    //predict the output when z is the input
    public Matrix predict(Matrix z){
        Matrix result = forwardPropagate(z);
        Matrix temp = MTJExt.max(result, 1);
        return GenFunc.splitMatrix(temp, 0, -1, 1, 1);
    }
    
    public CostGradient getCostGradient(){
        return lrCostGrad;
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
