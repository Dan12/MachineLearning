package com.mycompany.maventest;

import com.jmatio.io.MatFileReader;
import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLDouble;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;

public class MatFileInt {
    
    private static final String resourcePath = new File("").getAbsolutePath().concat("/src/main/java/resources/");

    public MatFileInt(){}
    
    public static Matrix readFile(String fname, String mname){
        MatFileReader temp = null;
        try {
            temp = new MatFileReader(new File(resourcePath.concat(fname)));
        } catch (IOException ex) {}
        double[][] tempArr = ((MLDouble) temp.getMLArray(mname)).getArray(); 
        return new DenseMatrix(tempArr);
    }
    
    public static void getFileContent(String fname){
        MatFileReader temp = null;
        try {
            temp = new MatFileReader(new File(resourcePath.concat(fname)));
        } catch (IOException ex) {}
        System.out.println(temp.getContent());
    }
    
    public static void writeMatrixArr(Matrix[] a, String[] n, String fname){
        MatFileWriter fw = new MatFileWriter();
        ArrayList<MLArray> ctn = new ArrayList<MLArray>();
        for(int i = 0; i < a.length; i++)
            ctn.add(new MLDouble(n[i], GenFunc.getMatrixArray(a[i])));
        try {
            fw.write(new File(resourcePath.concat(fname)), ctn);
        } catch (IOException ex) {}
    }

}
