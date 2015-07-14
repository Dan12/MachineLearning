package com.mycompany.NEAT;

public class NodeGene {
    
    private int number;
    private double zValue;
    private double aValue;
    private int dependancies;
    private int depEval;
    private Type type;

    public NodeGene(int n, Type t, double v){
        number = n;
        aValue = v;
        zValue = 0;
        type = t;
        dependancies = 0;
        depEval = 0;
    }
    
    public enum Type{
        INPUT,
        OUTPUT,
        HIDDEN
    }
    
    public void nextStep(){
        depEval = 0;
    }
    
    public boolean evaluated(){
        return depEval == dependancies;
    }
    
    public void addDependancy(){
        dependancies++;
    }
    
    public void removeDependancy(){
        dependancies--;
    }
    
    public double getValue(){
        return aValue;
    }
    
    public int getNumber(){
        return number;
    }
    
    public Type getType(){
        return type;
    }
    
    public void addInput(double v){
        zValue+=v; 
        depEval++;
        if(evaluated()){
            aValue = Functions.Sigmoid(zValue);
            zValue = 0;
        }
    }
    
    public void setInput(double a){
        if(type == Type.INPUT){
            aValue = a;
        }
    }
    
    public NodeGene copyGene(){
        NodeGene ret = new NodeGene(this.number, this.type, this.aValue);
        ret.zValue = this.zValue;
        ret.dependancies = this.dependancies;
        ret.depEval = this.depEval;
        return ret;
    }
    
    @Override
    public String toString(){
        return number+" | "+type+" | "+aValue+" | "+dependancies;
    }

}
