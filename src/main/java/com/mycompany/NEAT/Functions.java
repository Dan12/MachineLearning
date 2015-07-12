package com.mycompany.NEAT;

public class Functions {

    public Functions(){}
    
    public static double Sigmoid(double z){
        return (double)1/(1+Math.exp(-z));
    }

}
