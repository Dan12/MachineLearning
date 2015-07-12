package com.mycompany.NEAT;

public class NEAT {
    
    public static int initPopulation = 100;
    public static double mutNode = 0.03;
    public static double mutConn = 0.05;
    public static double mutWeightRand = 0.01;
    public static double mutWeightPur = 0.02;

    public NEAT(){}
    
    public void runTests(){
        Tests.runRests();
    }

}
