package com.mycompany.maventest;

import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;

//MTJ extensions
public class MTJExt {

    public MTJExt(){}
    
    //returns m*n matrix of ones
    public static Matrix Ones(int m, int n){
        double[][] temp = new double[m][n];
        for(int r = 0; r < m; r++){
            for(int c = 0; c < n; c++){
                temp[r][c] = 1;
            }
        }
        return new DenseMatrix(temp);
    }
    
    //returns m*n matrix of con
    public static Matrix Const(int m, int n, double con){
        double[][] temp = new double[m][n];
        for(int r = 0; r < m; r++){
            for(int c = 0; c < n; c++){
                temp[r][c] = con;
            }
        }
        return new DenseMatrix(temp);
    }
    
    //returns m*n matrix of zeros
    public static Matrix Zeros(int m, int n){
        return new DenseMatrix(new double[m][n]);
    }
    
    //returns 1*x matrix of values from s-f at intervals i
    public static Matrix Range(int s, int i, int f){
        double[][] retArr = new double[1][(f-s+1)/i];
        int ind = 0;
        for(int v = s; v <= f; v+=i){
            retArr[0][ind] = v;
            ind++;
        }
        return new DenseMatrix(retArr);
    }
    
    //returns 1*1 matrix of d
    public static Matrix single(double d){
        return new DenseMatrix(new double[][]{{d}});
    }
    
    //returns x*1 matrix representation of a
    public static Matrix toVector(Matrix a){
        double[][] retArr = new double[a.numRows()*a.numColumns()][1];
        int retAt = 0;
        for(int r = 0; r < a.numRows(); r++){
            for(int c = 0; c < a.numColumns(); c++){
                retArr[retAt][0] = a.get(r, c);
                retAt++;
            }
        }
        return new DenseMatrix(retArr);
    }
    
    //compute mean along specified dimension (1-rows, get x*1 matrix; 2-cols, get 1*x matrix)
    public static Matrix mean(Matrix a, int dim){
        if(dim == 1){
            double[][] retArr = GenFunc.getMatrixArray(sum(a, 1));
            for(int r = 0; r < a.numRows(); r++){
                retArr[r][0] = retArr[r][0]/a.numColumns();
            }
            return new DenseMatrix(retArr);
        }
        else{
            double[][] retArr = GenFunc.getMatrixArray(sum(a, 2));
            for(int c = 0; c < a.numColumns(); c++){
                retArr[0][c] = retArr[0][c]/a.numRows();
            }
            return new DenseMatrix(retArr);
        }
    }
    
    //compute standard deviation along specified dimension (1-rows, get x*1 matrix; 2-cols, get 1*x matrix)
    public static Matrix std(Matrix a, int dim){
        Matrix temp = sum(powExtend(minusExtend(a, mean(a, dim)),new DenseMatrix(new double[][]{{2}})),dim);
        double divisor = 0;
        if(dim == 1)
            divisor = a.numColumns();
        else
            divisor = a.numRows();
        temp.scale(((double)1)/divisor);
        return powExtend(temp, new DenseMatrix(new double[][]{{0.5}}));
    }
    
    //sums all elements of matrix along dim (1: ->, 2: v)
    public static Matrix sum(Matrix a, int dim){
        double[][] retArr = null;
        if(dim == 1){
            retArr = new double[a.numRows()][1];
            for(int r = 0; r < a.numRows(); r++){
                double sum = 0;
                for(int c = 0; c < a.numColumns(); c++){
                    sum+=a.get(r, c);
                }
                retArr[r] = new double[]{sum};
            }
            return new DenseMatrix(retArr);
        }
        else{
            retArr = new double[1][a.numColumns()];
            for(int c = 0; c < a.numColumns(); c++){
                double sum = 0;
                for(int r = 0; r < a.numRows(); r++){
                    sum+=a.get(r, c);
                }
                retArr[0][c] = sum;
            }
            return new DenseMatrix(retArr);
        }
    }
    
    public static Matrix plusExtend(Matrix a, Matrix b){
        return opExtend(a, b, 0);
    }
    
    public static Matrix minusExtend(Matrix a, Matrix b){
        return opExtend(a, b, 1);
    }
    
    public static Matrix timesExtend(Matrix a, Matrix b){
        return opExtend(a, b, 2);
    }
    
    public static Matrix divideExtend(Matrix a, Matrix b){
        return opExtend(a, b, 3);
    }
    
    public static Matrix powExtend(Matrix a, Matrix b){
        return opExtend(a,b,4);
    }
    
    public static Matrix logExtend(Matrix a){
        return opExtend(a, a, 5);
    }
    
    public static Matrix equalsExtend(Matrix a, Matrix b){
        return opExtend(a, b, 6);
    }
    
    public static Matrix roundExtend(Matrix a){
        return opExtend(a, a, 7);
    }
    
    public static Matrix moduloExtend(Matrix a, Matrix b){
        return opExtend(a, b, 8);
    }
    
    //max along dim (1-rows (->), 2-cols (v))
    public static Matrix max(Matrix a, int dim){
        if(dim == 1){
            double[][] temp = new double[a.numRows()][2];
            for(int r = 0; r < a.numRows(); r++){
                double max = a.get(r,0);
                int index = 0;
                for(int c = 1; c < a.numColumns(); c++){
                    if(a.get(r, c) > max){
                        max = a.get(r,c);
                        index = c;
                    }
                }
                temp[r][0] = max;
                temp[r][1] = index;
            }
            return new DenseMatrix(temp);
        }
        else{
            double[][] temp = new double[2][a.numColumns()];
            for(int c = 0; c < a.numColumns(); c++){
                double max = a.get(0,c);
                int index = 0;
                for(int r = 1; r < a.numRows(); r++){
                    if(a.get(r, c) > max){
                        max = a.get(r,c);
                        index = r;
                    }
                }
                temp[0][c] = max;
                temp[1][c] = index;
            }
            return new DenseMatrix(temp);
        }
    }
    
    //concat along dim (1-rows, 2-cols)
    public static Matrix concat(Matrix a, Matrix b, int dim){
        if(dim == 1){
            if(a.numRows()!= b.numRows())
                throw new IllegalArgumentException("All rows must have the same length.");
            int newN = a.numColumns()+b.numColumns();
            double[][] temp = new double[a.numRows()][newN];
            for(int r = 0; r < a.numRows(); r++){
                int curC = 0;
                for(int c = 0; c < newN; c++){
                    if(curC < a.numColumns())
                        temp[r][c] = a.get(r,curC);
                    else
                        temp[r][c] = b.get(r,curC-a.numColumns());
                    curC++;
                }
            }
            return new DenseMatrix(temp);
        }
        else{
            if(a.numColumns()!= b.numColumns())
                throw new IllegalArgumentException("All columns must have the same length.");
            int newM = a.numRows()+b.numRows();
            double[][] temp = new double[newM][a.numColumns()];
            int curR = 0;
            for(int r = 0; r < newM; r++){
                for(int c = 0; c < a.numColumns(); c++){
                    if(curR < a.numRows())
                        temp[r][c] = a.get(curR,c);
                    else
                        temp[r][c] = b.get(curR-a.numRows(),c);
                }
                curR++;
            }
            return new DenseMatrix(temp);
        }
    }
    
    //Extends element-wise operations even if matricies do not have same dimensions
    //0-add, 1-right sub, 2-mult, 3-right div, 4-pow, 5-log, 6-equals(==), 7-round
    private static Matrix opExtend(Matrix a, Matrix b, int op){
        if(a.numColumns()== b.numColumns() && a.numRows()== b.numRows()){
            DenseMatrix result = new DenseMatrix(a, true);
            if(op == 0)
                result.add(b);
            else if(op == 1){
                Matrix invB = new DenseMatrix(b, true);
                invB.scale(-1);
                result.add(invB);
            }
            else{
                result = null;
                double[][] retArr = new double[a.numRows()][a.numColumns()];
                for(int r = 0; r < a.numRows(); r++){
                    for(int c = 0; c < a.numColumns(); c++){
                        if(op == 2)
                            retArr[r][c] = a.get(r, c)*b.get(r, c);
                        else if(op == 3)
                            retArr[r][c] = a.get(r, c)/b.get(r, c);
                        else if(op == 4)
                            retArr[r][c] = Math.pow(a.get(r, c),b.get(r, c));
                        else if(op == 5)
                            retArr[r][c] = Math.log(a.get(r, c));
                        else if(op == 6){
                            if(a.get(r,c) == b.get(r,c))
                                retArr[r][c] = 1;
                            else
                                retArr[r][c] = 0;
                        }
                        else if(op == 7)
                            retArr[r][c] = Math.round(a.get(r,c));
                        else if(op == 8)
                            retArr[r][c] = ((int) a.get(r, c))%((int)b.get(r, c));
                    }
                }
                result = new DenseMatrix(retArr);
            }
            return result;
        }
       else if(a.numColumns() == b.numColumns()){
            if(a.numRows() == 1){
                double[][] retArr = new double[b.numRows()][b.numColumns()];
                for(int r = 0; r < b.numRows(); r++){
                    for(int c = 0; c < a.numColumns(); c++){
                        switch(op){
                            case 0:
                                retArr[r][c] = a.get(0, c)+b.get(r,c);
                                break;
                            case 1:
                                retArr[r][c] = a.get(0, c)-b.get(r,c);
                                break;
                            case 2:
                                retArr[r][c] = a.get(0, c)*b.get(r,c);
                                break;
                            case 3:
                                retArr[r][c] = a.get(0, c)/b.get(r,c);
                                break;
                            case 4:
                                retArr[r][c] = Math.pow(a.get(0, c),b.get(r,c));
                                break;
                            case 6:
                                if(a.get(0,c) == b.get(r,c))
                                    retArr[r][c] = 1;
                                else
                                    retArr[r][c] = 0;
                                break;
                            case 8:
                                retArr[r][c] = ((int) a.get(0, c))%((int)b.get(r, c));
                                break;
                        }
                    }
                }
                return new DenseMatrix(retArr);
            }
            else if(b.numRows() == 1){
                double[][] retArr = new double[a.numRows()][a.numColumns()];
                for(int r = 0; r < a.numRows(); r++){
                    for(int c = 0; c < a.numColumns(); c++){
                        switch(op){
                            case 0:
                                retArr[r][c] = a.get(r, c)+b.get(0,c);
                                break;
                            case 1:
                                retArr[r][c] = a.get(r, c)-b.get(0,c);
                                break;
                            case 2:
                                retArr[r][c] = a.get(r, c)*b.get(0,c);
                                break;
                            case 3:
                                retArr[r][c] = a.get(r, c)/b.get(0,c);
                                break;
                            case 4:
                                retArr[r][c] = Math.pow(a.get(r, c),b.get(0,c));
                                break;
                            case 6:
                                if(a.get(r,c) == b.get(0,c))
                                    retArr[r][c] = 1;
                                else
                                    retArr[r][c] = 0;
                                break;
                            case 8:
                                retArr[r][c] = ((int) a.get(r, c))%((int)b.get(0, c));
                                break;
                        }
                    }
                }
                return new DenseMatrix(retArr);
            }
            else{
                throw new IllegalArgumentException("No Good Arguments.");
            }
        }
        else if(a.numRows() == b.numRows()){
            if(a.numColumns() == 1){
                double[][] retArr = new double[b.numRows()][b.numColumns()];
                for(int r = 0; r < a.numRows(); r++){
                    for(int c = 0; c < b.numColumns(); c++){
                        switch(op){
                            case 0:
                                retArr[r][c] = a.get(r, 0)+b.get(r,c);
                                break;
                            case 1:
                                retArr[r][c] = a.get(r, 0)-b.get(r,c);
                                break;
                            case 2:
                                retArr[r][c] = a.get(r, 0)*b.get(r,c);
                                break;
                            case 3:
                                retArr[r][c] = a.get(r, 0)/b.get(r,c);
                                break;
                            case 4:
                                retArr[r][c] = Math.pow(a.get(r, 0),b.get(r,c));
                                break;
                            case 6:
                                if(a.get(r,0) == b.get(r,c))
                                    retArr[r][c] = 1;
                                else
                                    retArr[r][c] = 0;
                                break;
                            case 8:
                                retArr[r][c] = ((int) a.get(r, 0))%((int)b.get(r, c));
                                break;
                        }
                    }
                }
                return new DenseMatrix(retArr);
            }
            else if(b.numColumns() == 1){
                double[][] retArr = new double[a.numRows()][a.numColumns()];
                for(int r = 0; r < a.numRows(); r++){
                    for(int c = 0; c < a.numColumns(); c++){
                        switch(op){
                            case 0:
                                retArr[r][c] = a.get(r, c)+b.get(r,0);
                                break;
                            case 1:
                                retArr[r][c] = a.get(r, c)-b.get(r,0);
                                break;
                            case 2:
                                retArr[r][c] = a.get(r, c)*b.get(r,0);
                                break;
                            case 3:
                                retArr[r][c] = a.get(r, c)/b.get(r,0);
                                break;
                            case 4:
                                retArr[r][c] = Math.pow(a.get(r, c),b.get(r,0));
                                break;
                            case 6:
                                if(a.get(r,c) == b.get(r,0))
                                    retArr[r][c] = 1;
                                else
                                    retArr[r][c] = 0;
                                break;
                            case 8:
                                retArr[r][c] = ((int) a.get(r, c))%((int)b.get(r, 0));
                                break;
                        }
                    }
                }
                return new DenseMatrix(retArr);
            }
            else{
                throw new IllegalArgumentException("No Good Arguments.");
            }
        }
        else if(a.numRows() == 1 && a.numColumns() == 1){
            double[][] retArr = new double[b.numRows()][b.numColumns()];
            for(int r = 0; r < b.numRows(); r++){
                for(int c = 0; c < b.numColumns(); c++){
                    switch(op){
                        case 0:
                            retArr[r][c] = a.get(0, 0)+b.get(r,c);
                            break;
                        case 1:
                            retArr[r][c] = a.get(0, 0)-b.get(r,c);
                            break;
                        case 2:
                            retArr[r][c] = a.get(0, 0)*b.get(r,c);
                            break;
                        case 3:
                            retArr[r][c] = a.get(0, 0)/b.get(r,c);
                            break;
                        case 4:
                            retArr[r][c] = Math.pow(a.get(0, 0),b.get(r,c));
                            break;
                        case 6:
                            if(a.get(0,0) == b.get(r,c))
                                retArr[r][c] = 1;
                            else
                                retArr[r][c] = 0;
                            break;
                        case 8:
                            retArr[r][c] = ((int) a.get(0, 0))%((int)b.get(r, c));
                            break;
                    }
                }
            }
            return new DenseMatrix(retArr);
        }
        else if(b.numRows() == 1 && b.numColumns() == 1){
            double[][] retArr = new double[a.numRows()][a.numColumns()];
            for(int r = 0; r < a.numRows(); r++){
                for(int c = 0; c < a.numColumns(); c++){
                    switch(op){
                        case 0:
                            retArr[r][c] = a.get(r, c)+b.get(0,0);
                            break;
                        case 1:
                            retArr[r][c] = a.get(r, c)-b.get(0,0);
                            break;
                        case 2:
                            retArr[r][c] = a.get(r, c)*b.get(0,0);
                            break;
                        case 3:
                            retArr[r][c] = a.get(r, c)/b.get(0,0);
                            break;
                        case 4:
                            retArr[r][c] = Math.pow(a.get(r, c),b.get(0,0));
                            break;
                        case 6:
                            if(a.get(r,c) == b.get(0,0))
                                retArr[r][c] = 1;
                            else
                                retArr[r][c] = 0;
                            break;
                        case 8:
                            retArr[r][c] = ((int) a.get(r, c))%((int)b.get(0, 0));
                            break;
                    }
                }
            }
            return new DenseMatrix(retArr);
        }
        else
            throw new IllegalArgumentException("No Good Arguments.");
    }

}
