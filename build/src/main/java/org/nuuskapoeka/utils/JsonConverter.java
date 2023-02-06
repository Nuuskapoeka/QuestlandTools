package org.nuuskapoeka.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.nuuskapoeka.tools.*;

public class JsonConverter {

    public JsonConverter(){

    }
    public void ToCSV(){

    }
    public String FromCSV(File csv) throws IOException {
        Reader r = new Reader(csv);
        Writer w = new Writer(new File("items.json"));
        List<String> read = r.read();
        String[] fields = read.get(0).split(",");
        String json = "[";
        int id = 0;
        for(String s : read){
            if(id == 0){
                id++;
                continue;
            }
            String[] parts = s.split(",");
            String jsonObj = "{";
            int fieldID = 0;
            for(String str : parts){
                jsonObj+="\""+fields[fieldID]+"\"" + ":";
                if(isNumeric(parts[fieldID])){
                    jsonObj += parts[fieldID];
                }else {
                    jsonObj += "\"" + parts[fieldID] + "\"";
                }
                if(fieldID<fields.length-1){
                    jsonObj+=",";
                }
                fieldID++;

            }
            jsonObj += "}";
            json+=jsonObj;
            if(id<read.size()-1){
                json+=",";
            }
            id++;
        }
        json += "]";
        w.write(json);
        System.out.println(json);
        return json;
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
