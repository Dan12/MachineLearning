package com.mycompany.NEAT;

public class NEAT {
    
    //Population Size
    public static int populationSize = 100;
    //Probability that offspring results from crossover instead of mutation
    public static double offspringCross = 0.75;
    //Probability that inherited gene is disabled if it was disabled in either parent
    public static double inheritDisable = 0.75;
    //Interspecies mating rate
    public static double interSpecies = 0.001;
    //Slope of sigmoid activation function
    public static double sigmoidSlope = 1.0;
    //Probability to add a new node
    public static double mutNode = 0.03;
    //Probability to add a new connection
    public static double mutConn = 0.05;
    //Probability whole genome's weights will mutate
    public static double mutWeight = 0.8;
    //Probability for weight to be uniformly perturbed instead of randomly reassigned
    public static double mutWeightPer = 0.9;
    //Perturb step
    public static double perturbStep = 0.1;
    //Weight random initialization range +-
    public static double epsilon = 2.0;
    //Delta Excess coefficient
    public static double c1 = 1.0;
    //Delta Disjoint coefficient
    public static double c2 = 1.0;
    //Delta weight coefficient
    public static double c3 = 0.1;
    //Delta threshold for species acceptance
    public static double deltaThreshold = 3.0;

    public NEAT(){}
    
    public void runTests(){
        Tests.runRests();
    }

}
