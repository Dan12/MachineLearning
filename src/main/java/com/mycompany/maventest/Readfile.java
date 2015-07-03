package com.mycompany.maventest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Readfile {
    
    private static final String resourcePath = new File("").getAbsolutePath().concat("/src/main/java/resources/");

    public Readfile(){}
    
    public static String[] fileLines(String fileName) throws FileNotFoundException, IOException{;
        BufferedReader lineReader = new BufferedReader(new FileReader(new File(resourcePath.concat(fileName))));
        BufferedReader lineNums = new BufferedReader(new FileReader(new File(resourcePath.concat(fileName))));
        String temp = "";
        int numLines = 0;
        while((temp =lineNums.readLine()) != null){
            numLines++;
        }
        String[] ret = new String[numLines];
        for(int i = 0; i < numLines; i++){
            ret[i] = lineReader.readLine();
        }
        return ret;
    }

}
