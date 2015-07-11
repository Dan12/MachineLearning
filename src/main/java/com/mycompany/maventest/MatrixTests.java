package com.mycompany.maventest;

import java.io.IOException;
import java.util.Arrays;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrices;
import no.uib.cipr.matrix.Matrix;

public class MatrixTests {
    
    private static double test = 15.0;

    public MatrixTests(){}
    
    public static void MTJTests(){
        Matrix origA = new DenseMatrix(new double[][]{{2,3},{5,4},{6,10}}); //3*2
        Matrix origB = new DenseMatrix(new double[][]{{1,13},{11,6},{20,17}});  //3*2
        Matrix origC = new DenseMatrix(new double[][]{{1},{2}});    //2*1
        Matrix origD = new DenseMatrix(new double[][]{{1},{2},{3}});    //3*1
        Matrix matA = new DenseMatrix(origA, true); Matrix matB = new DenseMatrix(origB, true); Matrix matC = new DenseMatrix(origC, true); Matrix matD = new DenseMatrix(origD, true);
        //returns matA*matC, stores value in matD matD affected
        System.out.println("Test 1");
        System.out.println(matA.mult(matC, matD));
        System.out.println(matA+"\n"+matC); matD = new DenseMatrix(origD, true);
        //Adds matB to matA, returns result, matA affected
        System.out.println("Test 2");
        System.out.println(matA.add(matB));
        System.out.println(matA+"\n"+matB); matA = new DenseMatrix(origA, true);
        //adds 5*matB to matA, matA affected
        System.out.println("Test 3");
        System.out.println(matA.add(5, matB));
        System.out.println(matA+"\n"+matB); matA = new DenseMatrix(origA, true);
        //gets double value of matA at index 0,2, unnafected
        System.out.println("Test 4");
        System.out.println(matA.get(0, 1));
        //returns matA*(5*matC), stores value in matD, matD afected
        System.out.println("Test 5");
        System.out.println(matA.mult(5, matC, matD));
        System.out.println(matA+"\n"+matC); matD = new DenseMatrix(origD, true);
        //returns (matA*matC)+matD, stores result in matD, matD affected
        System.out.println("Test 6");
        System.out.println(matA.multAdd(matC, matD));
        System.out.println(matA+"\n"+matC+"\n"+matD); matD = new DenseMatrix(origD, true);
        //returns norm of matA (sqrt(sum(matA.^2))), unaffected
        System.out.println("Test 7");
        System.out.println(matA.norm(Matrix.Norm.Frobenius));
        System.out.println(matA);
        //returns matA*5, matA affected
        System.out.println("Test 8");
        System.out.println(matA.scale(5));
        System.out.println(matA); matA = new DenseMatrix(origA, true);
        //returns matB, sets matA to deep copy of matB
        System.out.println("Test 9");
        System.out.println(matA.set(matB));
        System.out.println(matA+"\n"+matB); matA = new DenseMatrix(origA, true);
        //returns x where a*x = b, in this case the inverse as b is identity matrix
        System.out.println("Test 10");
        System.out.println(new DenseMatrix(new double[][]{{5,1},{6,7}}).solve(Matrices.identity(2), new DenseMatrix(2,2)));
        //returns matA'*matB, both unaffected
        System.out.println("Test 11");
        System.out.println(matA.transAmult(matB, new DenseMatrix(2,2)));
        System.out.println(matA+"\n"+matB);
        //returns matA'*(2*matB), both unaffected
        System.out.println("Test 12");
        System.out.println(matA.transAmult(2, matB, new DenseMatrix(2,2)));
        System.out.println(matA+"\n"+matB);
        //returns matA*matB', both unaffected
        System.out.println("Test 13");
        System.out.println(matA.transBmult(matB, new DenseMatrix(3,3)));
        System.out.println(matA+"\n"+matB);
        //returns matA*(matB*2)', both unaffected
        System.out.println("Test 14");
        System.out.println(matA.transBmult(2, matB, new DenseMatrix(3,3)));
        System.out.println(matA+"\n"+matB);
        //returns (matA'*matB)+matE, stores value in matE, matE affected
        System.out.println("Test 15");
        Matrix matE = new DenseMatrix(new double[][]{{1,2},{3,4}});
        System.out.println(matA.transAmultAdd(matB, matE));
        System.out.println(matA+"\n"+matB+"\n"+matE);
        //returns matC'*matA', both unaffected
        System.out.println("Test 16");
        System.out.println(matC.transABmult(matA, new DenseMatrix(1,3)));
        System.out.println(matC+"\n"+matA);
        //returns matC'*(2*matA'), both unaffected
        System.out.println("Test 17");
        System.out.println(matC.transABmult(2, matA, new DenseMatrix(1,3)));
        System.out.println(matC+"\n"+matA);
        //returns (matC'*matA')+matE, value stored in matE, matE affected
        System.out.println("Test 18");
        matE = new DenseMatrix(new double[][]{{1,2,3}});
        System.out.println(matC.transABmultAdd(matA, matE));
        System.out.println(matC+"\n"+matA+"\n"+matE);
        //returns matA*zeros, sets matA to result, matA affected
        System.out.println("Test 19");
        System.out.println(matA.zero());
        System.out.println(matA); matA = new DenseMatrix(origA, true);
        //returns matA', matA unaffected
        System.out.println("Test 20");
        System.out.println(matA.transpose(new DenseMatrix(matA.numColumns(), matA.numRows())));
        System.out.println(matA);
        //adds 20 to matA at position 0,0
        System.out.println("Test 21");
        matA.add(0, 0, 20);
        System.out.println(matA); matA.set(origA);
        //sets matA position 0,0 to 51
        System.out.println("Test 22");
        matA.set(0, 0, 51);
        System.out.println(matA); matA = new DenseMatrix(origA, true);
        //returns x where a'*x = b, inverse of transpose
        System.out.println("Test 23");
        System.out.println(new DenseMatrix(new double[][]{{1,2},{3,4}}).transSolve(Matrices.identity(2), new DenseMatrix(2,2)));
    }
    
    public static void GenFuncTests(){
        Matrix origA = new DenseMatrix(new double[][]{{2,3},{5,4},{6,10}}); //3*2
        Matrix origB = new DenseMatrix(new double[][]{{1},{2},{3}});    //3*1
        Matrix matA = new DenseMatrix(origA, true); Matrix matB = new DenseMatrix(origB, true);
        //Passed
        //returns new matrix split (row start index, row finish, column start, column finish), -1 means everything
        System.out.println("Test 1");
        System.out.println(GenFunc.splitMatrix(matA, 0, -1, 0, 0));
        System.out.println(GenFunc.splitMatrix(matB, 1, 2, 0, -1));
        //Passed
        //returns two dimensional double array with same values as the matrix
        System.out.println("Test 2");
        System.out.println(Arrays.deepToString(GenFunc.getMatrixArray(matA)));
        //Passed
        //Maps 6 which is in range 0-10, to range 0-1
        System.out.println("Test 3");
        System.out.println(GenFunc.map(6, 0, 10, 0, 1));
        //Passed
        //returns a string containing all values of array to highest prescision in proper format to write to a file
        System.out.println("Test 4");
        System.out.println(GenFunc.matrixToString(matA));
        //Passed
        //Maps two x*1 matricies to degree 4, see actual function for more info
        System.out.println("Test 5");
        System.out.println(GenFunc.mapFeature(matB, matB, 4));
        //Passed
        //returns vector with matA(:) concatenated to matB(:)
        System.out.println("Test 6");
        System.out.println(GenFunc.unroll(new Matrix[]{matA, matB}));
        //Passed
        //returns new matrix with 2 rows and 1 column reshaped from rows 0-1 of matB(vector)
        System.out.println("Test 7");
        System.out.println(GenFunc.reshape(matB, 0, 1, 2, 1));
        //Passed
        //returns sigmoid of all values of matA
        System.out.println("Test 8");
        System.out.println(GenFunc.sigmoid(matA));
        //Passed
        //returns sigmoid of all values of matA, uses BigDecimal for high precision(100)
        System.out.println("Test 9");
        System.out.println(GenFunc.sigmoidEx(matA));
        //Passed
        //returns 1-sigmoid of all values of matA, uses BigDecimal for high precision(100)
        System.out.println("Test 10");
        System.out.println(GenFunc.invSigmoidEx(matA));
        //Passed
        //returns the value of the derivative of sigmoid function at all values in matA
        System.out.println("Test 11");
        System.out.println(GenFunc.sigmoidGradient(matA));
    }
    
    public static void MTJExtTests(){
        Matrix origA = new DenseMatrix(new double[][]{{2,3},{5,4},{6,10}}); //3*2
        Matrix origB = new DenseMatrix(new double[][]{{1},{2},{3}});    //3*1
        Matrix origC = new DenseMatrix(new double[][]{{1},{2}});    //2*1
        Matrix matA = new DenseMatrix(origA, true); Matrix matB = new DenseMatrix(origB, true); Matrix matC = new DenseMatrix(origC, true);
        //Passd
        //returns 2*3 matrix of all 5's
        System.out.println("Test 1");
        System.out.println(MTJExt.Const(2, 3, 5));
        //Passed
        //returns 1*5 matrix of ones
        System.out.println("Test 2");
        System.out.println(MTJExt.Ones(1, 5));
        //Passed
        //returns 1*x matrix with values from 3-10 at interval 2
        System.out.println("Test 3");
        System.out.println(MTJExt.Range(3, 2, 10));
        //Passed
        //returns 2*1 matrix of zeros
        System.out.println("Test 4");
        System.out.println(MTJExt.Zeros(2, 1));
        //Passed
        //returns matrix with matB concatenated to matA along dim
        System.out.println("Test 5");
        System.out.println(MTJExt.concat(matA, matB, 1));
        System.out.println(MTJExt.concat(matB, matC, 2));
        //Passed
        //Special elementwise division of matA by matB, matA./matB, ret(0,:) = matA(0,:)./matB(0,:), ret(1,:) = matA(1,:)./matB(0,:)
        System.out.println("Test 6");
        System.out.println(MTJExt.divideExtend(matA, matB));
        //Passed
        //Checks equality, 0-false, 1-true, matC==[1;1]
        System.out.println("Test 7");
        System.out.println(MTJExt.equalsExtend(matC, MTJExt.Ones(2, 1)));
        //Passed
        //returns the ln of all values of matA
        System.out.println("Test 8");
        System.out.println(MTJExt.logExtend(matA));
        //Passed 
        //returns the max of matA along dim and index of max values
        System.out.println("Test 9");
        System.out.println(MTJExt.max(matA, 1));
        System.out.println(MTJExt.max(matA, 2));
        //Passed
        //returns the mean of matA along dim
        System.out.println("Test 10");
        System.out.println(MTJExt.mean(matA, 1));
        System.out.println(MTJExt.mean(matA, 2));
        //Passed
        //Elementwise subtraction, matA.-matB
        System.out.println("Test 11");
        System.out.println(MTJExt.minusExtend(matA, matB));
        //Passed
        //Elementwise modulo, matA.%matB
        System.out.println("Test 12");
        System.out.println(MTJExt.moduloExtend(matA, matB));
        //Passed
        //Elementwise addition matA.+matB
        System.out.println("Test 13");
        System.out.println(MTJExt.plusExtend(matA, matB));
        //Passed
        //Elementwise Math.pow(matA, matB)
        System.out.println("Test 14");
        System.out.println(MTJExt.powExtend(matA, matB));
        //Passed
        //Round every number >=0.5, round up, else round down
        System.out.println("Test 15");
        System.out.println(MTJExt.roundExtend(new DenseMatrix(new double[][]{{2.435, 2.5632, 4.5},{1.1111, 1.0, 6.99}})));
        //Passed
        //returns 1*1 matrix with value set to 3
        System.out.println("Test 16");
        System.out.println(MTJExt.single(3));
        //Passed
        //returns the standard deviation of matA along dim
        System.out.println("Test 17");
        System.out.println(MTJExt.std(matA, 1));
        System.out.println(MTJExt.std(matA, 2));
        //Passed
        //returns the sum of matA along dim
        System.out.println("Test 18");
        System.out.println(MTJExt.sum(matA, 1));
        System.out.println(MTJExt.sum(matA, 2));
        //Passed
        //Elementwise multiplication, matA.*matB
        System.out.println("Test 19");
        System.out.println(MTJExt.timesExtend(matA, matB));
        //Passed
        //returns matA as a vector, matA(:)
        System.out.println("Test 20");
        System.out.println(MTJExt.toVector(matA));
    }
    
    public static void linearRegressionTests(){
    
    }
    
    public static void logisticRegressionTests(){
    
    }
    
    public static void neuralNetwordTests(){
    
    }
    
    public static void miscTests(){
    
    }
    
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
        Matrix tempX = MatFileInt.readFile("ex3data1.mat", "X");
        System.out.println(tempX.get(0, 130));
        Matrix tempY = MatFileInt.readFile("ex3data1.mat", "y");
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
        
        //Passed
        tempY = MatFileInt.readFile("ex3data1.mat", "y");
        NeuralNetwork nn = new NeuralNetwork(tempX, tempY, 10, 1, new int[]{25}, 1);
        //Passed
        nn.loadWeights("ex3weights.mat");
        //Passed
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 100, 100, 0, -1)));
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 1100, 1100, 0, -1)));
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 1600, 1600, 0, -1)));
        System.out.println(GenFunc.splitMatrix(tempY, 495, 505, 0, 0));
        System.out.println("Training Accuracy: "+GenFunc.matrixToString(MTJExt.mean(MTJExt.equalsExtend(nn.predict(tempX), MTJExt.minusExtend(nn.getY(), MTJExt.single(1))), 2)));
        //Passed
        matC = GenFunc.unroll(new Matrix[]{new DenseMatrix(new double[][]{{1,2,3,4},{5,10,15,20}}), new DenseMatrix(new double[][]{{13,21,35,43,31},{51,102,150,201,32}})});
        System.out.println(matC);
        //Passed
        int sumRows = 0;
        Matrix tempmatC = GenFunc.reshape(matC, sumRows, sumRows+2*4-1, 2, 4);
        System.out.println(tempmatC);
        sumRows+=2*4;
        matC = GenFunc.reshape(matC, sumRows, sumRows+2*5-1, 2, 5);
        System.out.println(matC);
        //Passed
        CostGradient testGrad = new CostGradient(matC, matC, 1){
            
            @Override
            public double Cost(Matrix Theta){
                return test;
            }
        };
        test = 10.0;
        System.out.println(testGrad.Cost(null));
        //Passed
        System.out.println(nn.getCostGradient().Cost(GenFunc.unroll(nn.getAllTheta())));
        //Passed
        System.out.println(MTJExt.Range(0, 1, 19));
        //Passed
        System.out.println(GenFunc.sigmoidGradient(new DenseMatrix(new double[][]{{1, -0.5, 0, 0.5, 1}})));
        //Passed
        //nn.getCostGradient().Gradient(GenFunc.unroll(nn.getAllTheta()));
        //Passed, takes about 4 mins for 50 iterations
        //nn.runRoutine();
        //Passed
        //System.out.println(nn.getAllTheta()[1]);
        //MatFileInt.writeMatrixArr(nn.getAllTheta(), new String[]{"Theta1","Theta2"}, "nnweights.mat");
        //System.out.println(MatFileInt.readFile("nnweights.mat", "Theta2"));
        //Passed
        nn.loadWeights("nnweights.mat");
        System.out.println("Training Accuracy: "+GenFunc.matrixToString(MTJExt.mean(MTJExt.equalsExtend(nn.predict(tempX), MTJExt.minusExtend(nn.getY(), MTJExt.single(1))), 2)));
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 100, 100, 0, -1)));
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 600, 600, 0, -1)));
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 1100, 1100, 0, -1)));
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 1600, 1600, 0, -1)));
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 2200, 2200, 0, -1)));
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 2700, 2700, 0, -1)));
        
        //Passed
        LinearRegressionTests lrtests = new LinearRegressionTests(MatFileInt.readFile("ex5data1.mat", "X"),MatFileInt.readFile("ex5data1.mat", "y"),MatFileInt.readFile("ex5data1.mat", "Xval"),MatFileInt.readFile("ex5data1.mat", "yval"),MatFileInt.readFile("ex5data1.mat", "Xtest"),MatFileInt.readFile("ex5data1.mat", "ytest"));
        lrtests.runTests();
    }

}
