package com.mycompany.maventest;

public class GraphTests {

    public GraphTests(){}
    
    public static void runTests(){
        double[] graphTest = new double[]{0,2,4,6,5,6,7,8,9,15};
        double[] graphTest2 = new double[]{15,21,14,25,30,27,19,18,17,15};
        Graph2D g2d = new Graph2D();
        g2d.addPlot(graphTest2,2, java.awt.Color.RED);
        g2d.setMode(1);
        g2d.setTitle("Test");
        Equation fTest = new Equation(java.awt.Color.BLUE){
            
            @Override
            public double getYValue(double x){
                return 2+1*x+0.1*x*x;
            }
            
        };
        g2d.setEquation(fTest);
        g2d.showGraph();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {}
        g2d.addPlot(graphTest,2, java.awt.Color.GREEN);
        g2d.setXExtremes(new double[]{3,8});
        g2d.setYExtremes(new double[]{0,15});
        //g2d.removePlot(0);
        g2d.update();
    }

}
