package com.mycompany.NEAT;

public class ConnectionGene implements Comparable<ConnectionGene>{
    
    private int innovationNumber;
    private NodeGene inputNode;
    private NodeGene outputNode;
    private double weight;
    private boolean enabled;

    public ConnectionGene(NodeGene i, NodeGene o, int inv, double w){
        innovationNumber = inv;
        inputNode = i;
        outputNode = o;
        outputNode.addDependancy();
        weight = w;
        enabled = true;
    }
    
    public void activate(){
        if(enabled){
            outputNode.addInput(weight*inputNode.getValue());
            if(!inputNode.evaluated())
                System.out.println("Node "+inputNode+" not evaluated for Node "+outputNode);
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
