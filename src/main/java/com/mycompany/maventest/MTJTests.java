package com.mycompany.maventest;

import java.io.IOException;
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
        System.out.println(LogisticRegression.sigmoid(new DenseMatrix(new double[][]{{0,1,2,3},{-1,-2,-3,-4}})));
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
    }

}
