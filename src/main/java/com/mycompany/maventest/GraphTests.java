package com.mycompany.maventest;

public class GraphTests {

    public GraphTests(){}
    
    public static void runTests(){
        double[] graphTest = new double[]{0,2,4,6,5,6,7,8,9,15};
        Graph2D g2d = new Graph2D(graphTest,2);
        g2d.setMode(2);
        g2d.setTitle("Test");
        Equation fTest = new Equation(){
            
            @Override
            public double getYValue(double x){
                return x*2;
            }
            
        };
        g2d.setEquation(fTest);
        g2d.showGraph();
    }

}
