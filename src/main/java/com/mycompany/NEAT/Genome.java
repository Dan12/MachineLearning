package com.mycompany.NEAT;

import java.util.ArrayList;
import java.util.Random;

public class Genome {
    
    private ArrayList<ConnectionGene> connectionGenes;
    private ArrayList<NodeGene> nodeGenes;
    private int nodeNum = 1;
    private Random rand = new Random();

    public Genome(int inputs, int outputs){
        connectionGenes = new ArrayList<ConnectionGene>();
        nodeGenes = new ArrayList<NodeGene>();
        for(int i = 0; i < inputs+1; i++)
            addNodeGene(NodeGene.Type.INPUT, 1);
        for(int o = 0; o < outputs; o++)
            addNodeGene(NodeGene.Type.OUTPUT, 0);
        
        int inv = 1;
        for(int i = 0; i < inputs+1; i++){
            for(int o = inputs+1; o < inputs+outputs+1; o++)
                addConectionGene(nodeGenes.get(i), nodeGenes.get(o), inv++, rand.nextDouble()*2*NEAT.epsilon-NEAT.epsilon);
        }
    }
    
    private void addNodeGene(NodeGene.Type t, double v){
        nodeGenes.add(new NodeGene(nodeNum, t, v));
        nodeNum++;
    }
    
    private int addConectionGene(NodeGene i, NodeGene o, int inv, double w){
        int addIndex = connectionGenes.size();
        for(int k = 0; k < connectionGenes.size(); k++){
            if(connectionGenes.get(k).getOutput() == o.getNumber()){
                addIndex = k+1;
                break;
            }
        }
        connectionGenes.add(addIndex, new ConnectionGene(i, o, inv, w));
        return addIndex;
    }
    
    private void addConectionGene(NodeGene i, NodeGene o, int inv, double w, int addIndex){
        connectionGenes.add(addIndex, new ConnectionGene(i, o, inv, w));
    }
    
    public void setInputs(double[] inputs){
        for(int i = 1; i <= Population.inputs; i++)
            nodeGenes.get(i).setInput(inputs[i-1]);
    }
    
    public double[] getOutputs(){
        double[] ret = new double[Population.outputs];
        int retI = 0;
        for(int i = Population.inputs+1; i < Population.inputs+Population.outputs+1; i++){
            ret[retI] = nodeGenes.get(i).getValue();
            retI++;
        }
        return ret;
    }
    
    public void mutWeights(){
        for(ConnectionGene cg : connectionGenes)
            cg.mutate();
    }
    
    public void mutAddNode(){
        int index = rand.nextInt(connectionGenes.size());
        connectionGenes.get(index).disable();
        NodeGene input = connectionGenes.get(index).getInputGene();
        NodeGene output = connectionGenes.get(index).getOutputGene();
        double weight = connectionGenes.get(index).getWeight();
        addNodeGene(NodeGene.Type.HIDDEN, 0);
        int add = addConectionGene(nodeGenes.get(nodeGenes.size()-1), output, Population.innovationNumber, weight);
        Population.innovationNumber++;
        addConectionGene(input, nodeGenes.get(nodeGenes.size()-1), Population.innovationNumber, 1, add);
        Population.innovationNumber++;
    }
    
    public void mutAddConnection(){
        if(nodeGenes.size()*(nodeGenes.size()-(Population.inputs+1)) > connectionGenes.size()){
            checker: while(true){
                int input = rand.nextInt(nodeGenes.size());
                int output = rand.nextInt(nodeGenes.size()-(Population.inputs+1))+Population.inputs+1;
                for(ConnectionGene cg : connectionGenes){
                    if(cg.getInputGene() == nodeGenes.get(input)){
                        if(cg.getOutputGene()== nodeGenes.get(output))
                            continue checker;
                    }
                }
                addConectionGene(nodeGenes.get(input), nodeGenes.get(output), Population.innovationNumber, rand.nextDouble()*2*NEAT.epsilon-NEAT.epsilon);
                Population.innovationNumber++;
                break;
            }
        }
        else
            System.out.println("No Available connections");
    }   
    
    public void nextStep(){
        for(NodeGene ng : nodeGenes)
            ng.nextStep();
    }
    
    public void feedForward(){
        for(ConnectionGene cg : connectionGenes)
            cg.activate();
    }
    
    public ArrayList<ConnectionGene> getConnections(){
        return connectionGenes;
    }
    
    public int getSize(){
        return connectionGenes.size();
    }
    
    @Override
    public String toString(){
        String ret = "";
        for(int i = 0; i < connectionGenes.size(); i++)
            ret+=connectionGenes.get(i)+"\n";
        return ret;
    }

}
