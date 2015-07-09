package com.mycompany.maventest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;

//General Functions
public class GenFunc {

    public GenFunc(){}
    
    private static final int precision = 100;
    
    //Calculate sigmoid of all values in z
    public static Matrix sigmoid(Matrix z){
        Matrix tempZ = new DenseMatrix(z,true);
        return MTJExt.divideExtend(MTJExt.single(1), MTJExt.plusExtend(MTJExt.single(1), MTJExt.powExtend(MTJExt.single(Math.E), tempZ.scale(-1))));
    }
    
    public static Matrix sigmoidGradient(Matrix z){
        return MTJExt.timesExtend(sigmoidEx(z), invSigmoidEx(z));
    }
    
    public static Matrix sigmoidEx(Matrix z){
        double[][] retArr = new double[z.numRows()][z.numColumns()];
        for(int r = 0; r < z.numRows(); r++){
            for(int c = 0; c < z.numColumns(); c++){
                BigDecimal exp = new BigDecimal(Math.exp(-z.get(r, c)));
                retArr[r][c] = (new BigDecimal(1).divide(new BigDecimal(1).add(exp), precision, RoundingMode.HALF_UP)).doubleValue();
            }
        }
        return new DenseMatrix(retArr);
    }
    
    public static Matrix invSigmoidEx(Matrix z){
        double[][] retArr = new double[z.numRows()][z.numColumns()];
        for(int r = 0; r < z.numRows(); r++){
            for(int c = 0; c < z.numColumns(); c++){
                BigDecimal exp = new BigDecimal(Math.exp(-z.get(r, c)));
                retArr[r][c] = (new BigDecimal(1).subtract(new BigDecimal(1).divide(new BigDecimal(1).add(exp), precision, RoundingMode.HALF_UP))).doubleValue();
            }
        }
        return new DenseMatrix(retArr);
    }
    
    public static Matrix reshape(Matrix a, int rs, int rf, int nr, int nc){
        double[][] retArr = new double[nr][nc];
        int rAt = 0;
        int cAt = 0;
        for(int r = rs; r <= rf; r++){
            retArr[rAt][cAt] = a.get(r, 0);
            cAt++;
            if(cAt >= nc){
                cAt = 0;
                rAt++;
            }
        }
        return new DenseMatrix(retArr);
    }
    
    public static Matrix unroll(Matrix[] a){
        Matrix ret = MTJExt.toVector(a[0]);
        for(int i = 1; i < a.length; i++){
            ret = MTJExt.concat(ret, MTJExt.toVector(a[i]), 2);
        }
        return ret;
    }
    
    /*maps the two input features (n*1 Matricies) to quadratic features.
    * Returns a new feature array with more features, comprising of X1, X2, X1.^2, X2.^2, X1.*X2, X1.*X2.^2, etc..*/
    public static Matrix mapFeature(Matrix X1, Matrix X2, int deg){
        if(X1.numColumns() != 1 || X2.numColumns() != 1 || X1.numRows() != X2.numRows())
            throw new IllegalArgumentException("X1 and X2 must be the same size");
        int colNums = 0;
        for(int i = 1; i <= deg+1; i++)
            colNums+=i;
        double[][] retArr = new double[X1.numRows()][colNums-1];
        int colAt = 0;
        for(int i = 1; i <= deg; i++){
            for(int j = 0; j <= i; j++){
                Matrix X1Pow = MTJExt.powExtend(X1, MTJExt.single(i-j));
                Matrix X2Pow = MTJExt.powExtend(X2, MTJExt.single(j));
                double[][] vec = GenFunc.getMatrixArray(MTJExt.timesExtend(X1Pow, X2Pow));
                for(int r = 0; r < X1.numRows(); r++){
                    retArr[r][colAt] = vec[r][0];
                }
                colAt++;
            }
        }
        
        return MTJExt.concat(MTJExt.Ones(X1.numRows(), 1), new DenseMatrix(retArr), 1);
    }
    
    public static Matrix featureNormalize(Matrix a, Matrix mu, Matrix sig){
        return MTJExt.divideExtend(MTJExt.minusExtend(a,mu), sig);
    }
    
    public static double[][] splitDouble(double[][] d, int rs, int rf, int cs, int cf){
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
    
    public static Matrix splitMatrix(Matrix a, int rs, int rf, int cs, int cf){
        if(rf == -1)
            rf = a.numRows()-1;
        if(cf == -1)
            cf = a.numColumns()-1;
        double[][] ret = new double[rf-rs+1][cf-cs+1];
        int curR = 0;
        for(int r = rs; r <= rf; r++){
            int curC = 0;
            for(int c = cs; c <= cf; c++){
                ret[curR][curC] = a.get(r,c);
                curC++;
            }
            curR++;
        }
        return new DenseMatrix(ret);
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

    public static double map(double x, double in_min, double in_max, double out_min, double out_max){
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
