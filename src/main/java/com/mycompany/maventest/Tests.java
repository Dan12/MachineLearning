package com.mycompany.maventest;

import java.io.IOException;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;

public class Tests {

    public Tests(){}
    
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
    }

}
