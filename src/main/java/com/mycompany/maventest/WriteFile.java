package com.mycompany.maventest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import no.uib.cipr.matrix.Matrix;

public class WriteFile {

    private static final String resourcePath = new File("").getAbsolutePath().concat("/src/main/java/resources/");
    
    public WriteFile(){}
    
    public static void writeToFile(String name, String data) throws IOException{
        FileWriter write =  new FileWriter(resourcePath.concat(name), false);
        PrintWriter print_line = new PrintWriter(write);

        print_line.printf("%s", data);

        print_line.close();
    }
    
    public static void writeMatrix(String name, Matrix mat) throws IOException{
        String data = "";
        String matrix = mat.toString();
        String[] temp = matrix.split("\n");
        for(String s : temp)
            data += (s.trim()).replace("  ", ", ")+"\n";
        writeToFile(name, data);
    }

}
