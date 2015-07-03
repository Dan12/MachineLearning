package com.mycompany.maventest;

import com.mycompany.JAMA.Matrix;

public class JAMAExtensions {

    public JAMAExtensions(){}
    
    public static Matrix Ones(int m, int n){
        return new Matrix(m, n, 1);
    }
    
    public static Matrix Zeros(int m, int n){
        return new Matrix(m, n);
    }
    
    //compute mean along specified dimension (1-rows, get row vector; 2-cols, get column vector)
    public static Matrix mean(Matrix a, int dim){
        if(dim == 1){
            Matrix ret = sum(a, 1);
            for(int r = 0; r < ret.getRowDimension(); r++){
                ret.getArray()[r][0] = ret.getArray()[r][0]/a.getColumnDimension();
            }
            return ret;
        }
        else{
            Matrix ret = sum(a, 2);
            for(int c = 0; c < ret.getColumnDimension(); c++){
                ret.getArray()[0][c] = ret.getArray()[0][c]/a.getRowDimension();
            }
            return ret;
        }
    }
    
    //compute standard deviation along specified dimension (1-rows, get row vector; 2-cols, get column vector)
    public static Matrix std(Matrix a, int dim){
        Matrix temp = sum(dotPow(minusExtend(a, mean(a, dim)),2),dim);
        if(dim == 1)
            return dotPow(temp.times(((double)1)/a.getColumnDimension()),0.5);
        else
            return dotPow(temp.times(((double)1)/a.getRowDimension()),0.5);
    }
    
    //raises every element to p
    public static Matrix dotPow(Matrix a, double p){
        Matrix ret = new Matrix(a.getArrayCopy());
        for(int r = 0; r < a.getRowDimension(); r++){
            for(int c = 0; c < a.getColumnDimension(); c++){
                ret.getArray()[r][c] = Math.pow(a.get(r, c), p);
            }
        }
        return ret;
    }
    
    //sums all elements of matrix along dim
    public static Matrix sum(Matrix a, int dim){
        if(dim == 1){
            Matrix ret = new Matrix(a.getRowDimension(),1);
            for(int r = 0; r < a.getRowDimension(); r++){
                int sum = 0;
                for(int c = 0; c < a.getColumnDimension(); c++){
                    sum+=a.get(r, c);
                }
                ret.getArray()[r] = new double[]{sum};
            }
            return ret;
        }
        else{
            Matrix ret = new Matrix(1,a.getColumnDimension());
            for(int c = 0; c < a.getColumnDimension(); c++){
                int sum = 0;
                for(int r = 0; r < a.getRowDimension(); r++){
                    sum+=a.get(r, c);
                }
                ret.getArray()[0][c] = sum;
            }
            return ret;
        }
    }
    
    //Extends subtraction even if matricies do not have same dimensions
    public static Matrix minusExtend(Matrix a, Matrix b){
        if(a.getColumnDimension() == b.getColumnDimension() && a.getRowDimension() == b.getRowDimension())
            return a.minus(b);
        else if(a.getColumnDimension() == b.getColumnDimension()){
            if(a.getRowDimension() == 1){
                Matrix ret = new Matrix(b.getRowDimension(),b.getColumnDimension());
                for(int r = 0; r < b.getRowDimension(); r++){
                    for(int c = 0; c < a.getColumnDimension(); c++){
                        ret.getArray()[r][c] = a.get(0, c)-b.get(r,c);
                    }
                }
                return ret;
            }
            else if(b.getRowDimension() == 1){
                Matrix ret = new Matrix(a.getRowDimension(),a.getColumnDimension());
                for(int r = 0; r < a.getRowDimension(); r++){
                    for(int c = 0; c < a.getColumnDimension(); c++){
                        ret.getArray()[r][c] = a.get(r, c)-b.get(0,c);
                    }
                }
                return ret;
            }
            else{
                throw new IllegalArgumentException("No Good Arguments.");
            }
        }
        else if(a.getRowDimension() == b.getRowDimension()){
            if(a.getColumnDimension() == 1){
                Matrix ret = new Matrix(b.getRowDimension(),b.getColumnDimension());
                for(int r = 0; r < a.getRowDimension(); r++){
                    for(int c = 0; c < b.getColumnDimension(); c++){
                        ret.getArray()[r][c] = a.get(r, 0)-b.get(r,c);
                    }
                }
                return ret;
            }
            else if(b.getColumnDimension() == 1){
                Matrix ret = new Matrix(a.getRowDimension(),a.getColumnDimension());
                for(int r = 0; r < a.getRowDimension(); r++){
                    for(int c = 0; c < a.getColumnDimension(); c++){
                        ret.getArray()[r][c] = a.get(r, c)-b.get(r,0);
                    }
                }
                return ret;
            }
            else{
                throw new IllegalArgumentException("No Good Arguments.");
            }
        }
        else if(a.getRowDimension() == 1 && a.getColumnDimension() == 1){
            Matrix ret = new Matrix(b.getRowDimension(),b.getColumnDimension());
            for(int r = 0; r < b.getRowDimension(); r++){
                for(int c = 0; c < b.getColumnDimension(); c++){
                    ret.getArray()[r][c] = a.get(0, 0)-b.get(r,c);
                }
            }
            return ret;
        }
        else if(b.getRowDimension() == 1 && b.getColumnDimension() == 1){
            Matrix ret = new Matrix(a.getRowDimension(),a.getColumnDimension());
            for(int r = 0; r < a.getRowDimension(); r++){
                for(int c = 0; c < a.getColumnDimension(); c++){
                    ret.getArray()[r][c] = a.get(r, c)-b.get(0,0);
                }
            }
            return ret;
        }
        else
            throw new IllegalArgumentException("No Good Arguments.");
    }
    
    //concat along dim (1-rows, 2-cols)
    public static Matrix concat(Matrix a, Matrix b, int dim){
        if(dim == 1){
            if(a.getRowDimension() != b.getRowDimension())
                throw new IllegalArgumentException("All rows must have the same length.");
            int newN = a.getColumnDimension()+b.getColumnDimension();
            double[][] temp = new double[a.getRowDimension()][newN];
            for(int r = 0; r < a.getRowDimension(); r++){
                int curC = 0;
                for(int c = 0; c < newN; c++){
                    if(curC < a.getColumnDimension())
                        temp[r][c] = a.getArray()[r][curC];
                    else
                        temp[r][c] = b.getArray()[r][curC-a.getColumnDimension()];
                    curC++;
                }
            }
            return new Matrix(temp);
        }
        else{
            if(a.getColumnDimension()!= b.getColumnDimension())
                throw new IllegalArgumentException("All columns must have the same length.");
            int newM = a.getRowDimension()+b.getColumnDimension();
            double[][] temp = new double[newM][a.getColumnDimension()];
            int curR = 0;
            for(int r = 0; r < newM; r++){
                for(int c = 0; c < a.getColumnDimension(); c++){
                    if(curR < a.getRowDimension())
                        temp[r][c] = a.getArray()[curR][c];
                    else
                        temp[r][c] = b.getArray()[curR-a.getRowDimension()][c];
                }
                curR++;
            }
            return new Matrix(temp);
        }
    }
    
    //return string representation of matrix
    public static String matrixToString(Matrix a){
        String ret = "";
        for(int r = 0; r < a.getRowDimension(); r++){
            for(int c = 0; c < a.getColumnDimension(); c++){
                ret+="  "+String.format("% 6f", a.getArray()[r][c]);
            }
            ret+="\n";
        }
        return ret;
    }

}
