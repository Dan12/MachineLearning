package com.mycompany.maventest;

public class Main {

    public Main(){}
    
    public static void main( String[] args ){
        NumberRecognition nr = new NumberRecognition();
        nr.startApplication();
        MTJTests.runTests();  
        //GraphTests.runTests();
    }

}
