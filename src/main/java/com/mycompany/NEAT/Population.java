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
        initializePopulation();
    }
    
    private void initializePopulation(){
        species.add(new Species(new Organism(inputs, outputs)));
        orgLoop: for(int i = 1; i < NEAT.populationSize; i++){
            Organism temp = new Organism(inputs, outputs){
                
                @Override
                public double fitness(){
                    setInputs(new double[]{0,0});
                    double result = getOutputs()[0];
                    return (1-result)*(1-result);
                }
            };
            for(int j = 0; j < species.size(); j++){
                if(species.get(j).checkCompatibility(temp))
                    continue orgLoop;
            }
            species.add(new Species(temp));
        }
    }
    
    public void nextGen(){
        ArrayList<Organism> organsim = new ArrayList<Organism>();
        for(Species s : species)
            organsim.addAll(s.nextGen());
    }

}
