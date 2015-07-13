package com.mycompany.NEAT;

import java.util.ArrayList;
import java.util.Arrays;

public class Functions {

    public Functions(){}
    
    public static double Sigmoid(double z){
        return (double)1/(1+Math.exp(-z));
    }
    
    public static double compatibilityDistance(Organism o1, Organism o2){
        int N = Math.max(o1.getGenomeSize(), o2.getGenomeSize());
        double[] DEW = disjointExcessWeights(o1.synapsis(), o2.synapsis());
        System.out.println(Arrays.toString(DEW));
        return (NEAT.c1*DEW[1])/N+(NEAT.c2*DEW[0])/N+NEAT.c3*DEW[2];
    }
    
    public static double[] disjointExcessWeights(ArrayList<ConnectionGene> cg1, ArrayList<ConnectionGene> cg2){
        if(cg1.size() > cg2.size())
            return orderedDisjointExcessWeights(cg1, cg2);
        else
            return orderedDisjointExcessWeights(cg2, cg1);
    }
    
    //cg1 is larger array
    private static double[] orderedDisjointExcessWeights(ArrayList<ConnectionGene> cg1, ArrayList<ConnectionGene> cg2){
        double[] ret = new double[]{0,0,0};
        int smallInd = 0;
        int matchingNum = 0;
        for(int i = 0; i < cg1.size(); i++){
            //disjoint
            if(cg1.get(i).getInnovation() != cg2.get(smallInd).getInnovation()){
                ret[0]++;
                if(cg1.get(i).getInnovation() > cg2.get(smallInd).getInnovation()){
                    smallInd++;
                    i--;
                    if(smallInd >= cg2.size()){
                        ret[1] = cg1.size()-1-i;
                        break;
                    }
                }         
            }
            //matching
            else{
                ret[2]+=Math.abs(cg1.get(i).getWeight()-cg2.get(smallInd).getWeight());
                matchingNum++;
                smallInd++;
                //reached end of smaller genome, all other genes are excess
                if(smallInd >= cg2.size()){
                    ret[1] = cg1.size()-1-i;
                    break;
                }
            }
        }
        ret[2] = ret[2]/matchingNum;
        return ret;
    }
    
    public static ArrayList<ConnectionGene> synapsis(Genome g){
        ArrayList<ConnectionGene> ret = (ArrayList<ConnectionGene>) g.getConnections().clone();
        ret.sort(null);
        return ret;
    }

}
