package com.mycompany.NEAT;

import java.util.ArrayList;
import java.util.Random;

public class Genome {
    
    public ArrayList<ConnectionGene> connectionGenes;
    public ArrayList<NodeGene> nodeGenes;
    private int nodeNum = 1;

    public Genome(int inputs, int outputs){
        connectionGenes = new ArrayList<ConnectionGene>();
        nodeGenes = new ArrayList<NodeGene>();
        for(int i = 0; i < inputs; i++)
            addNodeGene(NodeGene.Type.INPUT, 1);
        for(int o = 0; o < outputs; o++)
            addNodeGene(NodeGene.Type.OUTPUT, 0);
        
        Random rand = new Random();
        int inv = 1;
        for(int i = 0; i < inputs; i++){
            for(int o = inputs; o < inputs+outputs; o++)
                addConectionGene(nodeGenes.get(i), nodeGenes.get(o), inv++, rand.nextDouble());
        }
    }
    
    private void addNodeGene(NodeGene.Type t, double v){
        nodeGenes.add(new NodeGene(nodeNum, t, v));
        nodeNum++;
    }
    
    private void addConectionGene(NodeGene i, NodeGene o, int inv, double w){
        connectionGenes.add(new ConnectionGene(i, o, inv, w));
    }
    
    public void setInputs(double[] inputs){
        for(int i = 1; i <= inputs.length; i++)
            nodeGenes.get(i).setInput(inputs[i-1]);
    }
    
    public void feedForward(){
        for(ConnectionGene cg : connectionGenes)
            cg.activate();
    }
    
    @Override
    public String toString(){
        String ret = "";
        for(int i = 0; i < connectionGenes.size(); i++)
            ret+=connectionGenes.get(i)+"\n";
        return ret;
    }

}
