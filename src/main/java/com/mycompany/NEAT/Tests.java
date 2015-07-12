package com.mycompany.NEAT;

public class Tests {

    public Tests(){}
    
    public static void runRests(){
        Genome g = new Genome(3,1);
        System.out.println(g);
        g.setInputs(new double[]{0,0});
        g.feedForward();
        System.out.println("*----*\n");
        System.out.println(g);
    }

}
