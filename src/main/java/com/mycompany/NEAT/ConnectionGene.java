package com.mycompany.NEAT;

public class ConnectionGene {
    
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
                System.out.println("Node Not Evaluated\n"+inputNode);
        }
    }
    
    @Override
    public String toString(){
        return "----\n"+innovationNumber+"\n"+inputNode+"\n"+outputNode+"\n"+weight+"\n"+enabled+"\n----";
    }

}
