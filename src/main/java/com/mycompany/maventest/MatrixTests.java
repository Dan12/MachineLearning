package com.mycompany.maventest;

import java.io.IOException;
import java.util.Arrays;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrices;
import no.uib.cipr.matrix.Matrix;

public class MatrixTests {
    
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
//        System.out.println(lg.costFunction()+"\n"+lg.getTheta());
        //Passed
        lg.gradientDescent(400, 0.01, false);
        System.out.println(lg.getTheta());
        //Passed
        System.out.println(lg.predict(MTJExt.concat(MTJExt.Ones(1, 1),GenFunc.featureNormalize(new DenseMatrix(new double[][]{{1650,3}}), lg.getMu(), lg.getSigma()), 1)));
        
        //Linear regression visulaizations
        //Passed
        LinearRegressionTests lrtests = new LinearRegressionTests(MatFileInt.readFile("ex5data1.mat", "X"),MatFileInt.readFile("ex5data1.mat", "y"),MatFileInt.readFile("ex5data1.mat", "Xval"),MatFileInt.readFile("ex5data1.mat", "yval"),MatFileInt.readFile("ex5data1.mat", "Xtest"),MatFileInt.readFile("ex5data1.mat", "ytest"));
        lrtests.runTests();
    }
    
    public static void logisticRegressionTests(){
        double[][] data = null;
        try {
            data = Readfile.getFileArray("logrdata1.txt");
        } catch (IOException e) {System.out.println(e);}
        //Passed
        LogisticRegression lr = new LogisticRegression(new DenseMatrix(GenFunc.splitDouble(data, 0, -1, 0, 1)), new DenseMatrix(GenFunc.splitDouble(data, 0, -1, 2, 2))); 
        //Passed
        lr.addBias();
        //Passed    
        lr.setLambda(1);
        //Passed
        System.out.println(lr.costFunction());
        //Passed
        System.out.println(lr.gradients(1));
        //Passed (I guess), not very good
        System.out.println(lr.gradientDescent(20, 0.001, true));
        System.out.println(lr.getTheta());
        //Passed
        System.out.println(lr.runFmincg(20));
        System.out.println(lr.getTheta());
        //Passed
        System.out.println(lr.predict(new DenseMatrix(new double[][]{{1,78,78}})));
        System.out.println(lr.predict(new DenseMatrix(new double[][]{{1,32,88}})));
        
        //One vs All
        //Passed
        Matrix tempX = MatFileInt.readFile("ex3data1.mat", "X");
        System.out.println(tempX.get(0, 130));
        Matrix tempY = MatFileInt.readFile("ex3data1.mat", "y");
        System.out.println(tempY.get(1300, 0));
        //Passed
        tempY = MTJExt.moduloExtend(tempY, MTJExt.single(10));
        OneVsAll handWriting = new OneVsAll(tempX, tempY, 10, 0.1);
        //Passed
        handWriting.addBias();
        //Passed
//        handWriting.runRoutine();
        //Passed
//        System.out.println(handWriting.getTheta());
//        try {
//            WriteFile.writeMatrix("weights.txt", handWriting.getTheta());
//        } catch (IOException e) { System.out.println(e);}
        handWriting.loadTheta("weights.txt");
        //Passed
        System.out.println(handWriting.predict(MTJExt.concat(MTJExt.single(1), GenFunc.splitMatrix(tempX, 100, 100, 0, -1), 1)));
        System.out.println(handWriting.predict(MTJExt.concat(MTJExt.single(1), GenFunc.splitMatrix(tempX, 600, 600, 0, -1), 1)));
        System.out.println(handWriting.predict(MTJExt.concat(MTJExt.single(1), GenFunc.splitMatrix(tempX, 1100, 1100, 0, -1), 1)));
        System.out.println(handWriting.predict(MTJExt.concat(MTJExt.single(1), GenFunc.splitMatrix(tempX, 1600, 1600, 0, -1), 1)));
        System.out.println(handWriting.predict(MTJExt.concat(MTJExt.single(1), GenFunc.splitMatrix(tempX, 2100, 2100, 0, -1), 1)));
        System.out.println("Training Accuracy: "+GenFunc.matrixToString(MTJExt.mean(MTJExt.equalsExtend(handWriting.predict(handWriting.getX()), handWriting.getY()), 2)));
    }
    
    public static void neuralNetwordTests(){
        //Passed
        Matrix tempX = MatFileInt.readFile("ex3data1.mat", "X");
        Matrix tempY = MatFileInt.readFile("ex3data1.mat", "y");
        NeuralNetwork nn = new NeuralNetwork(tempX, tempY, 10, 1, new int[]{25}, 1);
        //Passed
        nn.loadWeights("ex3weights.mat");
        //Passed
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 100, 100, 0, -1)));
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 1100, 1100, 0, -1)));
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 1600, 1600, 0, -1)));
        System.out.println(GenFunc.splitMatrix(tempY, 495, 505, 0, 0));
        System.out.println("Training Accuracy: "+GenFunc.matrixToString(MTJExt.mean(MTJExt.equalsExtend(nn.predict(tempX), MTJExt.minusExtend(tempY, MTJExt.single(1))), 2)));
        //Passed
        System.out.println(nn.getCostGradient().Cost(GenFunc.unroll(nn.getAllTheta())));
        //Passed
        //nn.getCostGradient().Gradient(GenFunc.unroll(nn.getAllTheta()));
        //Passed, takes about 4 mins for 50 iterations
        //nn.runRoutine(50);
        //Passed
        //System.out.println(nn.getAllTheta()[1]);
        //MatFileInt.writeMatrixArr(nn.getAllTheta(), new String[]{"Theta1","Theta2"}, "nnweights.mat");
        //System.out.println(MatFileInt.readFile("nnweights.mat", "Theta2"));
        //Passed
        nn.loadWeights("nnweights.mat");
        System.out.println("Training Accuracy: "+GenFunc.matrixToString(MTJExt.mean(MTJExt.equalsExtend(nn.predict(tempX), MTJExt.minusExtend(tempY, MTJExt.single(1))), 2)));
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 100, 100, 0, -1)));
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 600, 600, 0, -1)));
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 1100, 1100, 0, -1)));
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 1600, 1600, 0, -1)));
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 2200, 2200, 0, -1)));
        System.out.println(nn.predict(GenFunc.splitMatrix(tempX, 2700, 2700, 0, -1)));
        
        //2 hidden layers test
        Matrix xData = new DenseMatrix(new double[][]{
            {5,2},
            {4,1},
            {6,2},
            {7,1},
            {6,1},
            {6,0},
            {8,2},
            {7,2},
            {6,3},
            {8,1},
            {2,5},
            {1,4},
            {2,6},
            {1,7},
            {1,6},
            {0,6},
            {2,8},
            {2,7},
            {3,6},
            {1,8}
        });
        Matrix yData = new DenseMatrix(new double[][]{
            {1},
            {1},
            {1},
            {1},
            {1},
            {1},
            {1},
            {1},
            {1},
            {1},
            {2},
            {2},
            {2},
            {2},
            {2},
            {2},
            {2},
            {2},
            {2},
            {2}
        });
        //Passed, the more layers, the lower you have to set lambda
        nn = new NeuralNetwork(xData, yData, 2, 3, new int[]{5,4,3}, 0.001);
        //Passed
        System.out.println(nn.getTheta(0));
        System.out.println(nn.getTheta(1));
        System.out.println(nn.getTheta(2));
        System.out.println(nn.getAllTheta().length);
        //Passed
        nn.runRoutine(50);
        //Passed
        System.out.println(nn.predict(new DenseMatrix(new double[][]{{5,1}})));
        System.out.println(nn.predict(new DenseMatrix(new double[][]{{8,1}})));
        System.out.println(nn.predict(new DenseMatrix(new double[][]{{10,5}})));
        System.out.println(nn.predict(new DenseMatrix(new double[][]{{4,9}})));
        System.out.println(nn.predict(new DenseMatrix(new double[][]{{2,6}})));
        System.out.println(nn.predict(new DenseMatrix(new double[][]{{1,7}})));
        //Passed
        System.out.println(nn.getTheta(0));
        System.out.println(nn.getTheta(1));
        System.out.println(nn.getTheta(2));
    }
    
    private static double test = 15.0;
    public static void miscTests(){
        //Passed
        CostGradient testGrad = new CostGradient(MTJExt.single(1), MTJExt.single(1), 1){
            
            @Override
            public double Cost(Matrix Theta){
                return test;
            }
        };
        test = 10.0;
        System.out.println(testGrad.Cost(null));
        
        //Passed
        Matrix matC = GenFunc.unroll(new Matrix[]{new DenseMatrix(new double[][]{{1,2,3,4},{5,10,15,20}}), new DenseMatrix(new double[][]{{13,21,35,43,31},{51,102,150,201,32}})});
        System.out.println(matC);
        int sumRows = 0;
        Matrix tempmatC = GenFunc.reshape(matC, sumRows, sumRows+2*4-1, 2, 4);
        System.out.println(tempmatC);
        sumRows+=2*4;
        matC = GenFunc.reshape(matC, sumRows, sumRows+2*5-1, 2, 5);
        System.out.println(matC);
    }
}
