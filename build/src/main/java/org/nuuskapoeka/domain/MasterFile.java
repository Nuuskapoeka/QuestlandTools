package org.nuuskapoeka.domain;

import org.nuuskapoeka.tools.Reader;
import org.nuuskapoeka.tools.Writer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MasterFile {

    private File masterFile;
    private Reader reader;
    private Writer writer;

    private List<String> masterFileContent;

    public MasterFile(String fileName) throws IOException {
        this.masterFile = new File(fileName);
        this.reader = new Reader(masterFile);
        this.writer = new Writer(masterFile);

        masterFileContent = reader.read();

    }
    public void addLine(String line){
        if(masterFileContent.isEmpty()){
            masterFileContent.add("SKULL TOKENS,GUILD TROPHIES,TICKETS USED");
        }
        masterFileContent.add(line);
    }
    public void printContent(){
        for(String s: this.masterFileContent){
            System.out.println(s);
        }
    }
    public void save(){
        try {
            writer.write(masterFileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
