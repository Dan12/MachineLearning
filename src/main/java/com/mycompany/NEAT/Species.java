package com.mycompany.NEAT;

import java.util.ArrayList;
import java.util.Random;

public class Species {
    
    private ArrayList<Organism> organisms;
    private ArrayList<InnovWeight> representative;

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
        mutate();
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
        Random rand = new Random();
        int selectInd = rand.nextInt(organisms.size());
        representative = organisms.get(selectInd).getInnovWeight();
    }
    
    public int getRepSize(){
        return representative.size();
    }
    
    private void crossOver(){
        ArrayList<Organism> newList = new ArrayList<Organism>();
    }
    
    private void mutate(){
        
    }
    
    public ArrayList<InnovWeight> synapsis(){
        return Functions.synapsis(representative);
    }

}
