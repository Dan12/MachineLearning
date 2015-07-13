package com.mycompany.NEAT;

public class NEAT {
    
    public static int initPopulation = 100;
    public static double mutNode = 0.03;
    public static double mutConn = 0.05;
    public static double mutWeightRand = 0.01;
    public static double mutWeightPur = 0.02;
    public static double epsilon = 0.12;
    public static double c1 = 3.0;
    public static double c2 = 3.0;
    public static double c3 = 0.1;
    public static double deltaThreshold = 3.0;

    public NEAT(){}
    
    public void runTests(){
        Tests.runRests();
    }

}
