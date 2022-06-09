package org.nuuskapoeka.tools;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reader {

    private Scanner read;

    public Reader(File f) throws FileNotFoundException {
        read = new Scanner(f);
    }
    public List<String> read(){
        List<String> lines = new ArrayList<>();
        while(read.hasNextLine()){
            lines.add(read.nextLine());
        }
        return lines;
    }
    public void ReadJson(){

    }
}
