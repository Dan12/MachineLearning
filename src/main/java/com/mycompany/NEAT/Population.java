package com.mycompany.NEAT;

import java.util.ArrayList;

public class Population {
    
    private ArrayList<Species> species;
    public static int innovationNumber = 1;

    public Population(int inputs, int outputs){
        innovationNumber += inputs*outputs;
        species = new ArrayList<Species>();
    }

}
