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
    
    public ArrayList<Organism> nextGen(){
        selectRepresentative();
        crossOver();
        for(Organism org : organisms)
            org.nextGen();
        ArrayList<Organism> ret = (ArrayList<Organism>) organisms.clone();
        organisms.clear();
        return ret;
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
    
    private void crossOver(){
        ArrayList<Organism> newList = new ArrayList<Organism>();
        organisms.sort(null);
        System.out.println("Max Fitness: "+organisms.get(organisms.size()-1).fitness()+"\n"+organisms.get(organisms.size()-1).toString());
        int remNum = organisms.size()/2;
        int size = organisms.size();
        for(int i = 0; i < remNum; i++)
            organisms.remove(0);
        if(size >= 5){
            newList.add(organisms.get(organisms.size()-1));
            size--;
        }
        for(int i = 0; i < size; i++){
            Organism temp = new Organism(1, 1){
                
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
            newList.add(temp);
        }
        organisms = newList;
        //System.out.println("----End Crossover----");
    }
    
    public ArrayList<InnovWeight> synapsis(){
        return Functions.synapsis(representative);
    }

}
