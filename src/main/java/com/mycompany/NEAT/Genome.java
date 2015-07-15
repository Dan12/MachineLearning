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
                addConectionGene(nodeGenes.get(i), nodeGenes.get(o), inv++, rand.nextDouble()*2*NEAT.epsilon-NEAT.epsilon, true);
        }
    }
    
    private void addNodeGene(NodeGene.Type t, double v){
        nodeGenes.add(new NodeGene(nodeNum, t, v));
        nodeNum++;
    }
    
    private void addNodeGene(NodeGene.Type t, double v, int i){
        if(i < nodeGenes.size())
            nodeGenes.set(i, new NodeGene(i+1, t, v));
        else
            addNodeGene(t, v);
    }
    
    private int addConectionGene(NodeGene i, NodeGene o, int inv, double w, boolean e){
        int addIndex = connectionGenes.size();
        boolean chosen = false;
        for(int k = connectionGenes.size()-1; k >= 0 ; k--){
            if(connectionGenes.get(k).getInputGene().getNumber() == o.getNumber()){
                addIndex = k;
                chosen = true;
                break;
            }
        }
        if(!chosen){
            for(int k = connectionGenes.size()-1; k >= 0 ; k--){
                if(connectionGenes.get(k).getOutput() == o.getNumber()){
                    addIndex = k+1;
                    break;
                }
            }
        }
        connectionGenes.add(addIndex, new ConnectionGene(i, o, inv, w, e));
        return addIndex;
    }
    
    private void addConectionGene(NodeGene i, NodeGene o, int inv, double w, boolean e, int addIndex){
        connectionGenes.add(addIndex, new ConnectionGene(i, o, inv, w, e));
    }
    
    public void setInputs(double[] inputs){
        for(int i = 1; i <= Population.inputs; i++)
            nodeGenes.get(i).setInput(inputs[i-1]);
    }
    
    public void resetNodes(){
        for(NodeGene ng : nodeGenes)
            ng.reset();
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
        int add = addConectionGene(nodeGenes.get(nodeGenes.size()-1), output, Population.innovationNumber, weight, true);
        Population.innovationNumber++;
        addConectionGene(input, nodeGenes.get(nodeGenes.size()-1), Population.innovationNumber, 1, true, add);
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
                addConectionGene(nodeGenes.get(input), nodeGenes.get(output), Population.innovationNumber, rand.nextDouble()*2*NEAT.epsilon-NEAT.epsilon, true);
                Population.innovationNumber++;
                break;
            }
        }
        else{
            //System.out.println("No Available connections");
        }
    }
    
    public Genome crossover(Genome g, double f1, double f2){
        Genome ret = new Genome(0, 0);
        ret.nodeGenes = new ArrayList<NodeGene>();
        ret.nodeNum = 1;
        for(int i = 0; i < Population.inputs+Population.outputs+1; i++){
            ret.nodeGenes.add(null);
            ret.nodeNum++;
        }
        
        ArrayList<ConnectionGene> cg1 = Functions.synapsis(this);
        ArrayList<ConnectionGene> cg2 = Functions.synapsis(this);
        
        if(cg1.size() < cg2.size()){
            ArrayList<ConnectionGene> temp = cg1;
            cg1 = cg2;
            cg2 = temp;
            double tempD = f1;
            f1 = f2;
            f2 = tempD;
        }
        
        int smallI = 0;
        for(int i = 0; i < cg1.size(); i++){
            //disjoint
            if(cg1.get(i).getInnovation() != cg2.get(smallI).getInnovation()){
                if(f1 > f2)
                    addCrossConnection(ret, cg1.get(i), cg1.get(i).isActive());
                else
                    addCrossConnection(ret, cg2.get(smallI), cg2.get(i).isActive());
                if(cg1.get(i).getInnovation()> cg2.get(smallI).getInnovation()){
                    smallI++;
                    i--;
                    if(smallI >= cg2.size()){
                        //add excess
                        if(f1 > f2){
                            for(int j = i+1; j < cg1.size(); j++)
                                addCrossConnection(ret, cg1.get(j), cg1.get(i).isActive());
                        }
                        break;
                    }
                }     
            }
            //matching
            else{
                double random = rand.nextDouble();
                boolean enable = true;
                if(!cg1.get(i).isActive() || !cg2.get(smallI).isActive()){
                    if(rand.nextDouble() < NEAT.inheritDisable)
                        enable = false;
                }
                if(random > 0.5)
                    addCrossConnection(ret, cg1.get(i), enable);
                else
                    addCrossConnection(ret, cg2.get(smallI), enable);
                smallI++;
                if(smallI >= cg2.size()){
                    //add excess
                    if(f1 > f2){
                        for(int j = i+1; j < cg1.size(); j++)
                            addCrossConnection(ret, cg1.get(j), cg1.get(i).isActive());
                    }
                    break;
                }
            }
        }
        
        return ret;
    }
    
    private void addCrossConnection(Genome g, ConnectionGene cg, boolean e){
        if(!containsGene(g, cg.getInputGene()))
            g.addNodeGene(cg.getInputGene().getType(), cg.getInputGene().getValue(), cg.getInputGene().getNumber()-1);
        if(!containsGene(g, cg.getOutputGene()))
            g.addNodeGene(cg.getOutputGene().getType(), cg.getOutputGene().getValue(), cg.getOutputGene().getNumber()-1);
        g.addConectionGene(g.nodeGenes.get(cg.getInputGene().getNumber()-1), g.nodeGenes.get(cg.getOutputGene().getNumber()-1), cg.getInnovation(), cg.getWeight(), e);
    }
    
    private boolean containsGene(Genome g, NodeGene ng){
        boolean ret = false;
        for(NodeGene n : g.nodeGenes){
            if(n != null && n.getNumber() == ng.getNumber()){
                ret = true;
                break;
            }
        }
        return ret;
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
    
    public ArrayList<InnovWeight> getInnovWeight(){
        ArrayList<InnovWeight> ret = new ArrayList<InnovWeight>();
        for(ConnectionGene cg : connectionGenes)
            ret.add(new InnovWeight(cg.getInnovation(), cg.getWeight()));
        return ret;
    }
    
    public Genome copyGenome(){
        Genome ret = new Genome(Population.inputs, Population.outputs);
        ret.nodeNum = this.nodeNum;
        ret.nodeGenes.clear();
        ret.connectionGenes.clear();
        for(NodeGene ng : this.nodeGenes)
            ret.nodeGenes.add(ng.copyGene());
        for(ConnectionGene cg : this.connectionGenes)
            ret.connectionGenes.add(cg.copyGene(ret.nodeGenes.get(cg.getInputGene().getNumber()-1), ret.nodeGenes.get(cg.getOutputGene().getNumber()-1)));
        return ret;
    }
    
    @Override
    public String toString(){
        String ret = "";
        for(int i = 0; i < connectionGenes.size(); i++)
            ret+=connectionGenes.get(i)+"\n";
        return ret;
    }

}
