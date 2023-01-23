package org.nuuskapoeka.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer {

    private FileWriter write;
    private File f;

    public Writer(File f) throws IOException {
        write = new FileWriter(f);
        this.f = f;

    }
    public void write(List<String> lines) throws IOException {
        for(String s: lines){
            write.write(s + "\n");
            //System.out.println(s + "\n");
        }
        write.flush();
        write.close();
        //System.out.println(f.getAbsolutePath());
    }
    public void write(String line) throws IOException {
        write.write(line);
        write.flush();
        write.close();

    }
}
