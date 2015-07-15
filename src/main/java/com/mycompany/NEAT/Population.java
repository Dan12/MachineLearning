package com.mycompany.NEAT;

import java.util.ArrayList;
import java.util.Random;

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
                    feedForward();
                    double result = getOutputs()[0];
                    ret+= (1-result)*(1-result);
                    setInputs(new double[]{1,1});
                    nextStep();
                    feedForward();
                    result = getOutputs()[0];
                    ret+= (1-result)*(1-result);
                    setInputs(new double[]{1,0});
                    nextStep();
                    feedForward();
                    result = getOutputs()[0];
                    ret+= (result)*(result);
                    setInputs(new double[]{0,1});
                    nextStep();
                    feedForward();
                    result = getOutputs()[0];
                    ret+= (result)*(result);
                    nextStep();
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
        //System.out.println("----End Initialize----");
    }
    
    public void nextGen(boolean last){
        ArrayList<Organism> organisms = new ArrayList<Organism>();
        double totalFitness = 0.0;
        for(Species s : species){
            s.cullSpecies();
            s.setFitness();
            s.selectRepresentative();
            totalFitness+=s.averageFitness();
        }
        for(Species s : species){
            int children = (int) Math.floor(s.averageFitness()/totalFitness*NEAT.populationSize)-1;
            for(int i = 0; i < children; i++)
                organisms.add(s.crossOver());
        }
        Random rand = new Random();
        int addNum = NEAT.populationSize-organisms.size();
        for(int i = 0; i < addNum; i++)
            organisms.add(species.get(rand.nextInt(species.size())).crossOver());
        
        orgLoop: for(int i = 0; i < NEAT.populationSize; i++){
            //System.out.println(organsims.get(i));
            for(int j = 0; j < species.size(); j++){
                if(species.get(j).checkCompatibility(organisms.get(i)))
                    continue orgLoop;
            }
            species.add(new Species(organisms.get(i)));
        }
        for(int i = 0; i < species.size(); i++){
            if(species.get(i).getSize() == 0){
                species.remove(i);
                i--;
                System.out.println("Removed Species");
            }
        }
        
        for(Organism org : organisms){
            org.setFitness();
            org.shareFitnessSort(false);
        }

        organisms.sort(null);
        System.out.println(organisms.get(organisms.size()-1).getFitness());
        if(last){
            System.out.println(organisms.get(organisms.size()-1).toString());
            System.out.println(organisms.get(organisms.size()-2).getFitness());
            System.out.println(organisms.get(organisms.size()-3).getFitness());
            System.out.println(organisms.get(organisms.size()-20).getFitness());
            System.out.println(organisms.get(0).getFitness());
        }
        //System.out.println("----End Next Gen----");
    }

}
