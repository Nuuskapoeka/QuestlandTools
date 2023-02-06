package org.nuuskapoeka.logic;

import org.nuuskapoeka.tools.Reader;
import org.nuuskapoeka.tools.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Copy {

    private Reader reader;
    private Writer writer;
    private File newFile;

    public Copy(File file) throws IOException {
        reader = new Reader(file);
        newFile = new File("new.txt");
        writer = new Writer(newFile);
    }
    public Copy(File file, String newName) throws IOException {
        reader = new Reader(file);
        newFile = new File(newName);
        writer = new Writer(newFile);
    }
    public void copy() throws IOException {
        writer.write(reader.read());
    }
}
