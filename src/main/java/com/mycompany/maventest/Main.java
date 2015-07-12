package com.mycompany.maventest;

import com.mycompany.NEAT.NEAT;

public class Main {

    public Main(){}
    
    public static void main( String[] args ){
        //NumberRecognition nr = new NumberRecognition();
        //nr.startApplication();
        
        //All passed
        //GraphTests.runTests();
        
        //All Passed
        //MatrixTests.MTJTests();
        
        //All Passed
        //MatrixTests.GenFuncTests();
        
        //All Passed
        //MatrixTests.MTJExtTests();
        
        //All Passed
        //MatrixTests.linearRegressionTests();
        
        //All Passed
        //MatrixTests.logisticRegressionTests();
        
        //All Passed
        //MatrixTests.neuralNetwordTests();
        
        
        NEAT neat= new NEAT();
        neat.runTests();
    }

}
