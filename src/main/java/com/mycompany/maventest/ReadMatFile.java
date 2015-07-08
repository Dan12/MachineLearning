package com.mycompany.maventest;

import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLDouble;
import java.io.File;
import java.io.IOException;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;

public class ReadMatFile {
    
    private static final String resourcePath = new File("").getAbsolutePath().concat("/src/main/java/resources/");

    public ReadMatFile(){}
    
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

}
