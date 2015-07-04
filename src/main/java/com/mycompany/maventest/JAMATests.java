package com.mycompany.maventest;

import com.mycompany.JAMA.Matrix;
import java.io.IOException;

public class JAMATests {

    public JAMATests(){}
    
    public static void runTests(){
        Matrix matX = new Matrix(new double[][]{
            {100,223522121,0.312},
            {400,523434553,0.642},
            {700,845456634,0.942}
        });
        Matrix matY = new Matrix(new double[][]{
            {10},
            {15},
            {24}
        });
        LinearRegression lg = new LinearRegression(matX, matY);
        System.out.println(JAMAExt.matrixToString(lg.featureNoramlize(true)));
        //Tests.runTests();
        //System.out.println(JAMAExtensions.matrixToString(JAMAExtensions.std(matA, 2)));
        //System.out.println(JAMAExtensions.matrixToString(JAMAExtensions.minusExtend(matA, JAMAExtensions.Ones(1, 1))));
        //System.out.println(JAMAExtensions.matrixToString(JAMAExtensions.mean(JAMAExtensions.concat(JAMAExtensions.Ones(1, 3),matA, 2),2)));
        Matrix data = null;
        try {
            data = new Matrix(Readfile.getFileArray("data1.txt"));
        } catch (IOException e) {System.out.println(e);}
        //System.out.println(JAMAExtensions.matrixToString(data.getMatrix(0, data.getRowDimension()-1, 0, 1)));
        //System.out.println(JAMAExtensions.matrixToString(data.getMatrix(0, data.getRowDimension()-1, 2, 2)));
        lg = new LinearRegression(data.getMatrix(0, data.getRowDimension()-1, 0, 1), data.getMatrix(0, data.getRowDimension()-1, 2, 2));
        System.out.println(JAMAExt.matrixToString(lg.featureNoramlize(true)));
        lg.addBias();
        //System.out.println(lg.predict(new Matrix(new double[][]{{1,1650,3}})));
        lg.gradientDescent(400, 0.01);
        //lg.normalEquation();
        System.out.println(JAMAExt.matrixToString(lg.getTheta()));
        System.out.println(lg.predict(new Matrix(new double[][]{{1,2104,3}})));
        System.out.println(lg.costFunction());
    }

}
