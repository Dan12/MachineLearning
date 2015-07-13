package com.mycompany.NEAT;

public class InnovWeight implements Comparable<InnovWeight>{
    
    private int innovationNumber;
    private double weight;

    public InnovWeight(int inv, double w){
        innovationNumber = inv;
        weight = w;
    }
    
    public int getInv(){
        return innovationNumber;
    }
    
    public double getWght(){
        return weight;
    }
    
    @Override
    public String toString(){
        return "----\n"+innovationNumber+"\n"+weight+"\n----";
    }

    @Override
    public int compareTo(InnovWeight inv) {
        if(this.innovationNumber > inv.innovationNumber)
            return 1;
        else if(this.innovationNumber < inv.innovationNumber)
            return -1;
        else
            return 0;
    }

}
