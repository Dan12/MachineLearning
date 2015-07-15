package com.mycompany.NEAT;

import java.util.Arrays;

public class Tests {

    public Tests(){}
    
    public static void runRests(){
        Population p = new Population(2, 1);
        for(int i = 0; i < NEAT.maxGenerations; i++){
            if(i < NEAT.maxGenerations-1)
                p.nextGen(false);
            else
                p.nextGen(true);
        }
        
//        Species s1 = new Species(new Organism(Population.inputs, Population.outputs));
//        Organism org1 = new Organism(Population.inputs, Population.outputs);
//        Organism org3 = new Organism(Population.inputs, Population.outputs);
//        System.out.println(org1);
//        org1.mutNode();
//        org3.setGenome(org1.copyGenome());
//        org1.mutConn();
//        System.out.println(org1);
//        System.out.println(org3);
//        org1.setInputs(new double[]{0,0});
//        org1.feedForward();
//        System.out.println(Arrays.toString(org1.getOutputs()));
//        System.out.println(org1.synapsis().toString());
//        System.out.println(org1);
//        System.out.println(Functions.compatibilityDistance(s1, org1));
//        Organism org2 =  new Organism(Population.inputs, Population.outputs);
//        System.out.println(org2);
//        System.out.println(Functions.compatibilityDistance(s1,org2));
    }

}
