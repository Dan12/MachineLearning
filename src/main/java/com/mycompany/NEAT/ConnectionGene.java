package com.mycompany.NEAT;

import java.util.Random;

public class ConnectionGene implements Comparable<ConnectionGene>{
    
    private int innovationNumber;
    private NodeGene inputNode;
    private NodeGene outputNode;
    private double weight;
    private boolean enabled;

    public ConnectionGene(NodeGene i, NodeGene o, int inv, double w, boolean e){
        innovationNumber = inv;
        inputNode = i;
        outputNode = o;
        outputNode.addDependancy();
        weight = w;
        enabled = e;
    }
    
    public void activate(){
        if(enabled){
            outputNode.addInput(weight*inputNode.getValue());
            if(!inputNode.evaluated())
                System.out.println("Node "+inputNode+" not evaluated for Node "+outputNode);
        }
    }
    
    public void mutate(){
        Random rand = new Random();
        double checkVal = rand.nextDouble();
        //Perturb weight
        if(checkVal < NEAT.mutWeightPer){
            weight += rand.nextDouble()*2*NEAT.perturbStep-NEAT.perturbStep;
        }
        //New random value
        else{
            weight = rand.nextDouble()*2*NEAT.epsilon-NEAT.epsilon;
        }
    }
    
    public int getOutput(){
        return outputNode.getNumber();
    }
    
    public NodeGene getOutputGene(){
        return outputNode;
    }
    
    public NodeGene getInputGene(){
        return inputNode;
    }
    
    public boolean isActive(){
        return enabled;
    }
    
    public int getInnovation(){
        return innovationNumber;
    }
    
    public double getWeight(){
        return weight;
    }
    
    public void disable(){
        enabled = false;
        outputNode.removeDependancy();
    }
    
    public ConnectionGene copyGene(NodeGene i, NodeGene o){
        ConnectionGene ret = new ConnectionGene(i, o, this.innovationNumber, this.weight, this.enabled);
        o.removeDependancy();
        return ret;
    }
    
    @Override
    public String toString(){
        return "----\n"+innovationNumber+"\n"+inputNode+"\n"+outputNode+"\n"+weight+"\n"+enabled+"\n----";
    }

    @Override
    public int compareTo(ConnectionGene cg) {
        if(this.innovationNumber > cg.innovationNumber)
            return 1;
        else if(this.innovationNumber < cg.innovationNumber)
            return -1;
        else
            return 0;
    }

}
