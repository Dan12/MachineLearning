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
                    double ret = 0.0;
                    setInputs(new double[]{0,0});
                    double result = getOutputs()[0];
                    ret+= (1-result)*(1-result);
                    setInputs(new double[]{1,1});
                    result = getOutputs()[0];
                    ret+= (1-result)*(1-result);
                    setInputs(new double[]{1,0});
                    result = getOutputs()[0];
                    ret+= (result)*(result);
                    setInputs(new double[]{0,1});
                    result = getOutputs()[0];
                    ret+= (result)*(result);
                    return ret;
                }
            };
            //System.out.println(temp);
            for(int j = 0; j < species.size(); j++){
                if(species.get(j).checkCompatibility(temp))
                    continue orgLoop;
            }
            species.add(new Species(temp));
        }
        System.out.println("----End Initialize----");
    }
    
    public void nextGen(){
        ArrayList<Organism> organsims = new ArrayList<Organism>();
        for(Species s : species)
            organsims.addAll(s.nextGen());
        
        orgLoop: for(int i = 0; i < NEAT.populationSize; i++){
            //System.out.println(organsims.get(i));
            for(int j = 0; j < species.size(); j++){
                if(species.get(j).checkCompatibility(organsims.get(i)))
                    continue orgLoop;
            }
            species.add(new Species(organsims.get(i)));
        }
        for(int i = 0; i < species.size(); i++){
            if(species.get(i).getSize() == 0){
                species.remove(i);
                i--;
                System.out.println("Removed Species");
            }
        }
        //System.out.println("----End Next Gen----");
    }

}
