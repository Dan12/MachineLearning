package com.mycompany.maventest;

import com.mycompany.JAMA.*;

public class Main {

    public Main(){

    }
    
    public static void main( String[] args ){
        Matrix matX = new Matrix(new double[][]{
            {1,2,3},
            {4,5,6},
            {7,8,9}
        });
        Matrix matY = new Matrix(new double[][]{
            {10},
            {15},
            {24}
        });
        LinearRegression lg = new LinearRegression(matX, matY, JAMAExtensions.Zeros(3, 1));
        System.out.println(JAMAExtensions.matrixToString(lg.featureNoramlize()));
        //System.out.println(JAMAExtensions.matrixToString(JAMAExtensions.std(matA, 2)));
        //System.out.println(JAMAExtensions.matrixToString(JAMAExtensions.minusExtend(matA, JAMAExtensions.Ones(1, 1))));
        //System.out.println(JAMAExtensions.matrixToString(JAMAExtensions.mean(JAMAExtensions.concat(JAMAExtensions.Ones(1, 3),matA, 2),2)));
    }

}
