package com.mycompany.NEAT;

import java.util.ArrayList;

public class Organism {
    
    private Genome genome;

    public Organism(int inputs, int outputs){
        genome = new Genome(inputs,outputs);
    }
    
    public int getGenomeSize(){
        return genome.getSize();
    }
    
    public ArrayList<ConnectionGene> getConnections(){
        return genome.getConnections();
    }
    
    public ArrayList<ConnectionGene> synapsis(){
        return Functions.synapsis(genome);
    }
    
    public double[] getOutputs(){
        return genome.getOutputs();
    }
    
    public void nextStep(){
        genome.nextStep();
    }
    
    public void mutConn(){
        genome.mutAddConnection();
    }
    
    public void mutNode(){
        genome.mutAddNode();
    }
    
    public void setInputs(double[] inputs){
        genome.setInputs(inputs);
    }
    
    public void feedForward(){
        genome.feedForward();
    }
    
    @Override
    public String toString(){
        return genome.toString();
    }

}
