package com.mycompany.NEAT;

import java.util.ArrayList;

public class Population {
    
    private ArrayList<Species> species;
    public static int innovationNumber = 1;
    public static int inputs = 2;
    public static int outputs = 1;

    public Population(int i, int o){
        inputs = i;
        outputs = o;
        innovationNumber += inputs*outputs+1;
        species = new ArrayList<Species>();
    }

}
