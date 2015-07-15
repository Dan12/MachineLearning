package com.mycompany.NEAT;

import java.util.ArrayList;
import java.util.Random;

public class Species {
    
    private ArrayList<Organism> organisms;
    private ArrayList<InnovWeight> representative;
    Random rand = new Random();

    public Species(Organism o){
        organisms = new ArrayList<Organism>();
        organisms.add(o);
        o.setSpecies(this);
        representative = o.getInnovWeight();
    }
    
    public boolean checkCompatibility(Organism o){
        if(Functions.compatibilityDistance(this, o) <= NEAT.deltaThreshold){
            organisms.add(o);
            o.setSpecies(this);
            return true;
        }
        return false;
    }
    
    public double averageFitness(){
        double ret = 0;
        for (Organism org : organisms) {
            ret += org.sharedFitness();
        }
        return ret/organisms.size();
    }
    
    public int getSize(){
        return organisms.size();
    }
    
    public void selectRepresentative(){
        int selectInd = rand.nextInt(organisms.size());
        representative = organisms.get(selectInd).getInnovWeight();
    }
    
    public int getRepSize(){
        return representative.size();
    }
    
    public void cullSpecies(){
        for(Organism org : organisms)
            org.setFitness();
        organisms.sort(null);
        int remNum = organisms.size()/2;
        for(int i = 0; i < remNum; i++)
            organisms.remove(0);
    }
    
    public void setFitness(){
        for(Organism org : organisms)
            org.setFitness();
    }
    
    public Organism crossOver(){
        Organism temp = new Organism(1, 1){

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
        Organism org1 = organisms.get(rand.nextInt(organisms.size()));
        if(rand.nextDouble() < NEAT.offspringCross){
            Organism org2 = organisms.get(rand.nextInt(organisms.size()));
            temp.setGenome(org1.copyGenome().crossover(org2.copyGenome(), org1.fitness(), org2.fitness()));
        }
        else{
            temp.setGenome(org1.copyGenome());
        }
        //System.out.println(temp);
        temp.mutate();
        return temp;
    }
    
    public ArrayList<InnovWeight> synapsis(){
        return Functions.synapsis(representative);
    }

}
