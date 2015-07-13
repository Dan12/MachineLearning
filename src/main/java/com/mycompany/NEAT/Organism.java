package com.mycompany.NEAT;

import java.util.ArrayList;

public class Organism implements Comparable<Organism>{
    
    private Genome genome;
    private Species species;

    public Organism(int inputs, int outputs){
        genome = new Genome(inputs,outputs);
    }
    
    public void setSpecies(Species s){
        species = s;
    }
    
    public double sharedFitness(){
        return fitness()/species.getSize();
    }
    
    public double fitness(){
        return 0.0;
    }
    
    public int getGenomeSize(){
        return genome.getSize();
    }
    
    public ArrayList<ConnectionGene> getConnections(){
        return genome.getConnections();
    }
    
    public ArrayList<InnovWeight> synapsis(){
        return Functions.synapsis(genome.getInnovWeight());
    }
    
    public double[] getOutputs(){
        return genome.getOutputs();
    }
    
    public void nextStep(){
        genome.nextStep();
    }
    
    public void nextGen(){
        nextStep();
        species = null;
    }
    
    public void mutConn(){
        genome.mutAddConnection();
    }
    
    public void mutNode(){
        genome.mutAddNode();
    }
    
    public void mutWeights(){
        genome.mutWeights();
    }
    
    public void setInputs(double[] inputs){
        genome.setInputs(inputs);
    }
    
    public void feedForward(){
        genome.feedForward();
    }
    
    public ArrayList<InnovWeight> getInnovWeight(){
        return genome.getInnovWeight();
    }
    
    @Override
    public String toString(){
        return genome.toString();
    }

    @Override
    public int compareTo(Organism o) {
        if(this.sharedFitness() > o.sharedFitness())
            return 1;
        else if(this.sharedFitness() < o.sharedFitness())
            return -1;
        else
            return 0;
    }

}
