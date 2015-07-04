package com.mycompany.maventest;

import no.uib.cipr.matrix.Matrix;

//General Functions
public class GenFunc {

    public GenFunc(){}
    
    public static Matrix featureNormalize(Matrix a, Matrix mu, Matrix sig){
        return MTJExt.divideExtend(MTJExt.minusExtend(a,mu), sig);
    }
    
    public static double[][] splitMatrix(double[][] d, int rs, int rf, int cs, int cf){
        if(rf == -1)
            rf = d.length-1;
        if(cf == -1)
            cf = d[0].length-1;
        double[][] ret = new double[rf-rs+1][cf-cs+1];
        int curR = 0;
        for(int r = rs; r <= rf; r++){
            int curC = 0;
            for(int c = cs; c <= cf; c++){
                ret[curR][curC] = d[r][c];
                curC++;
            }
            curR++;
        }
        return ret;
    }
    
    public static double[][] getMatrixArray(Matrix a){
        double[][] ret = new double[a.numRows()][a.numColumns()];
        for(int r = 0; r < ret.length; r++){
            for(int c = 0; c < ret[0].length; c++){
                ret[r][c] = a.get(r, c);
            }
        }
        return ret;
    }
    
    public static String doubleToString(double[][] d){
        String ret = "";
        for(int r = 0; r < d.length; r++){
            for(int c = 0; c < d[0].length; c++){
                ret+="  "+d[r][c];
            }
            ret+="\n";
        }
        return ret;
    }
    
    public static String matrixToString(Matrix a){
        return doubleToString(getMatrixArray(a));
    }

}
