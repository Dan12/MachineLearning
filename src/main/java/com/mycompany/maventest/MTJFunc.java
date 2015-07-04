package com.mycompany.maventest;

import no.uib.cipr.matrix.*;

public class MTJFunc {

    public MTJFunc(){}
    
    public static double[][] getMatrixArray(Matrix a){
        double[][] ret = new double[a.numRows()][a.numColumns()];
        for(int r = 0; r < ret.length; r++){
            for(int c = 0; c < ret[0].length; c++){
                ret[r][c] = a.get(r, c);
            }
        }
        return ret;
    }

}
