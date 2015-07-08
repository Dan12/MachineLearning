package com.mycompany.maventest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;

public class MTJTests {

    public MTJTests(){}
    
    public static void runTests(){
        System.out.println( "Hello World!" );
        //2*2 identity
        Matrix matA = new DenseMatrix(new double[][]{{1,0},{0,1}});
        Matrix matB = new DenseMatrix(new double[][]{{2,5},{4,1}});
        Matrix matC = new DenseMatrix(matA.numRows(),matB.numColumns());
        //inverse
        matB.solve(new DenseMatrix(new double[][]{{1,0},{0,1}}), matC);
        DenseMatrix result = new DenseMatrix(matA.numRows(),matB.numColumns());
        matB.mult(matA,result);
        System.out.println(result);
        try {
            String[] temp = Readfile.fileLines("data1.txt");
            System.out.println(temp[temp.length-1]);
            WriteFile.writeMatrix("dataOut.txt", result);
        } catch (IOException e) {System.out.println(e);}
        System.out.println(matC.toString());
        
        matA.mult(-1, matB, matC);
        System.out.println(matC.toString());
        matC = new DenseMatrix(matA.add(matC));
        System.out.println(matC);
        
        //Passed
        System.out.println(MTJExt.sum(matB, 2));
        //Passed
        System.out.println(MTJExt.Ones(3, 2));
        //Passed
        System.out.println(MTJExt.Zeros(4, 5));
        //Passed
        System.out.println(MTJExt.concat(matB, MTJExt.Ones(1, 2), 2));
        //Passed
        System.out.println(MTJExt.minusExtend(matB, new DenseMatrix(new double[][]{{4},{3}})));
        //Passed
        System.out.println(MTJExt.plusExtend(matB, new DenseMatrix(new double[][]{{4},{3}})));
        //Passed
        System.out.println(MTJExt.timesExtend(matB, new DenseMatrix(new double[][]{{4},{3}})));
        //Passed
        System.out.println(MTJExt.divideExtend(matB, new DenseMatrix(new double[][]{{2},{4}})));
        //Passed
        System.out.println(MTJExt.powExtend(new DenseMatrix(new double[][]{{5,4}}), new DenseMatrix(new double[][]{{0.5}})));
        //Passed
        System.out.println(MTJExt.mean(matB,1));
        //Passed
        System.out.println(MTJExt.std(new DenseMatrix(new double[][]{{4,8,12}}),1));
        //Passed
        System.out.println(MTJExt.minusExtend(new DenseMatrix(new double[][]{{4,3},{3,1}}), matB));
        System.out.println(matB);
        //Passed
        System.out.println(matB.mult(matA, new DenseMatrix(matB.numRows(),matA.numColumns())));
        System.out.println(matC);
        //Passed
        System.out.println((new DenseMatrix(new double[][]{{5,2}})).transpose(new DenseMatrix(2,1)));
        
        double[][] data = null;
        try {
            data = Readfile.getFileArray("data1.txt");
        } catch (IOException e) {System.out.println(e);}
        
        //Passed
        LinearRegression lg = new LinearRegression(new DenseMatrix(GenFunc.splitDouble(data, 0, -1, 0, 1)), new DenseMatrix(GenFunc.splitDouble(data, 0, -1, 2, 2)));
        //Passed
        lg.featureNoramlize();
        lg.addBias();
        System.out.println(lg.getX());
        //Passed
        System.out.println(lg.costFunction());
        //Passed
//        lg.normalEquation();
//        System.out.println(lg.costFunction()+"  ,  "+lg.getTheta());
        //Passed
        lg.gradientDescent(400, 0.01, false);
        System.out.println(lg.getTheta());
        //All Linear Regression Tests Passed
        
        try {
            data = Readfile.getFileArray("logrdata1.txt");
        } catch (IOException e) {System.out.println(e);}
        LogisticRegression lr = new LogisticRegression(new DenseMatrix(GenFunc.splitDouble(data, 0, -1, 0, 1)), new DenseMatrix(GenFunc.splitDouble(data, 0, -1, 2, 2)));
        
        //Passed
        System.out.println(matC);
        System.out.println(matC.scale(-1));
        
        System.out.println((double)1/50);
        //Passed
        System.out.println(GenFunc.sigmoid(new DenseMatrix(new double[][]{{0,1,2,3},{-1,-2,-3,-4}})));
        //Passed
        System.out.println(MTJExt.logExtend(new DenseMatrix(new double[][]{{0,1,2,3},{-1,-2,-3,-4}})));
        //Passed
        lr.addBias();
        //Passed    
        lr.setLambda(5);
        //Passed
        System.out.println(lr.costFunction());
        //Passed
        System.out.println(lr.gradients(1));
        //Passed (I guess)
        System.out.println(lr.gradientDescent(20, 0.002, true));
        System.out.println(lr.getTheta());
        //All logistic regression tests passed, not happy with gradient descent
        //Passed
        CostGradient lrCostGrad = new CostGradient(lr.getX(), lr.getY(), 0){
            
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
        
        //Passed
        Fmincg mincg = new Fmincg(lrCostGrad);
        //Passed
        System.out.println(mincg.getCost(new DenseMatrix(new double[][]{{-21.53},{0.18},{0.17}})));
        //Passed
        System.out.println(GenFunc.matrixToString(mincg.getGradient(new DenseMatrix(new double[][]{{-21.53},{0.18},{0.17}}))));
        //Passed
        Fmincg.FmincgRet temp = mincg.runRoutine(MTJExt.Zeros(lrCostGrad.n, 1), 20);
        System.out.println(temp.getX());    //Theta, or optimal value
        System.out.println(temp.getI());    //Iteration on break
        System.out.println(temp.getfX());   //Cost History
        //Passed
        System.out.println(GenFunc.mapFeature(new DenseMatrix(new double[][]{{1},{2}}), new DenseMatrix(new double[][]{{1},{2}}), 6));
        System.out.println(matC);
        System.out.println(MTJExt.toVector(matC));
        //Passed
        System.out.println(MTJExt.max(new DenseMatrix(new double[][]{{4,5,2,3,12,4},{5,2,16,2,3,10}}), 1));
        //Passed
        Matrix tempX = ReadMatFile.readFile("ex3data1.mat", "X");
        System.out.println(tempX.get(0, 130));
        Matrix tempY = ReadMatFile.readFile("ex3data1.mat", "y");
        System.out.println(tempY.get(1300, 0));
        //Passed
        System.out.println(MTJExt.equalsExtend(new DenseMatrix(new double[][]{{1,3,4,2},{2,2,4,3}}), MTJExt.single(2)));
        //Passed
        System.out.println(MTJExt.roundExtend(new DenseMatrix(new double[][]{{1.123,3.5,4.1,2.9},{2.8,2.72354,4.111,3.643}})));
        //Passed
        tempY = MTJExt.moduloExtend(tempY, MTJExt.single(10));
        OneVsAll handWriting = new OneVsAll(tempX, tempY, 10, 0.1);
        //Passed
        handWriting.addBias();
        //Passed
        //handWriting.runRoutine();
        //Passed
//        System.out.println(handWriting.getTheta());
//        try {
//            WriteFile.writeMatrix("weights.txt", handWriting.getTheta());
//        } catch (IOException e) { System.out.println(e);}
        handWriting.loadTheta("weights.txt");
        //Passed
        System.out.println(handWriting.predict(MTJExt.concat(MTJExt.single(1), GenFunc.splitMatrix(tempX, 600, 600, 0, -1), 1)));
        System.out.println("Training Accuracy: "+GenFunc.matrixToString(MTJExt.mean(MTJExt.equalsExtend(handWriting.predict(handWriting.getX()), handWriting.getY()), 2)));
        //System.out.println(GenFunc.splitMatrix(tempY, 490, 510, 0, 0));
    }

}
