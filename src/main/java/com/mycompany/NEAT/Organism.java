package com.mycompany.NEAT;

import java.util.ArrayList;
import java.util.Random;

public class Organism implements Comparable<Organism>{
    
    private Genome genome;
    private Species species;
    private Random rand = new Random();
    private double sharedFitness;
    private double fitness;
    private boolean regFitness;

    public Organism(int inputs, int outputs){
        genome = new Genome(inputs,outputs);
        sharedFitness = 0;
        fitness = 0;
        regFitness = false;
    }
    
    public void setSpecies(Species s){
        species = s;
    }
    
    public double sharedFitness(){
        return sharedFitness;
    }
    
    public double getFitness(){
        return fitness;
    }
    
    public void setFitness(){
        fitness = fitness();
        sharedFitness = fitness/species.getSize();
    }
    
    public void resetNodes(){
        genome.resetNodes();
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
        if(!NEAT.keepMemory)
            resetNodes();
    }
    
    public void mutate(){
        double prob = rand.nextDouble();
        if(prob < NEAT.mutWeight)
            mutWeights();
        prob = rand.nextDouble();
        if(prob < NEAT.mutNode)
            mutNode();
        prob = rand.nextDouble();
        if(prob < NEAT.mutConn)
            mutConn();
    }
    
    public void nextGen(){
        nextStep();
        species = null;
    }
    
    public void mutConn(){
        //System.out.println("Mut Conn");
        genome.mutAddConnection();
    }
    
    public void mutNode(){
        //System.out.println("Mut Node");
        genome.mutAddNode();
    }
    
    public void mutWeights(){
        //System.out.println("Mut Wieghts");
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
    
    public void setGenome(Genome g){
        genome = g;
    }
    
    public Genome copyGenome(){
        return genome.copyGenome();
    }
    
    public void shareFitnessSort(boolean s){
        regFitness = !s;
    }
    
    @Override
    public String toString(){
        return genome.toString();
    }

    @Override
    public int compareTo(Organism o) {
        if(regFitness){
            if(this.fitness > o.fitness)
                return 1;
            else if(this.fitness < o.fitness)
                return -1;
            else
                return 0;
        }
        else{
            if(this.sharedFitness() > o.sharedFitness())
                return 1;
            else if(this.sharedFitness() < o.sharedFitness())
                return -1;
            else
                return 0;
        }
    }

}
