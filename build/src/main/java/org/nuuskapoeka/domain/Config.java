package org.nuuskapoeka.domain;

import org.nuuskapoeka.tools.Reader;
import org.nuuskapoeka.tools.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Config {

    private String configPath;
    private File configFile;

    private String pathToGuildFiles;
    private int startWeek;
    private boolean builderStatus;
    private boolean trackerStatus;
    private String builderSource;
    private String buildSource;

    public Config(String configPath) throws IOException {
        this.configPath = configPath;
        this.configFile = new File(configPath);
        read();
    }

    public void read() throws IOException {
        try {
            Scanner r = new Scanner(new File(configPath));

            int i = 0;

            while(r.hasNextLine()) {
                //System.out.println(r.nextLine() +", " + i);
                if(i == 0) {
                    pathToGuildFiles = split(r.nextLine(), 1);
                }else if(i==1) {
                    startWeek = Integer.valueOf(split(r.nextLine(),1));
                }else if(i==2){
                    builderStatus = Boolean.valueOf(split(r.nextLine(),1));
                }else if(i==3){
                    trackerStatus = Boolean.valueOf(split(r.nextLine(),1));
                }else if(i==4){
                    builderSource = split(r.nextLine(),1);
                }else if(i==5){
                    buildSource = split(r.nextLine(),1);
                }

                i++;
            }
        } catch (FileNotFoundException e) {
            Writer w = new Writer(configFile);
            w.write(defaultConfig());
        }
    }
    public void write() throws IOException {
        configFile.setWritable(true);
        Writer w = new Writer(configFile);
        List<String> list = new ArrayList<>();
        list.add("guildFiles: " + pathToGuildFiles);
        list.add("startWeek: " + startWeek);
        list.add("builderStatus: " + builderStatus);
        list.add("trackerStatus: " + trackerStatus);
        list.add("builderSource: " + builderSource);
        list.add("buildSource: " + buildSource);
        w.write(list);
        configFile.setReadOnly();
    }
    private List<String> defaultConfig(){

        List<String> list = new ArrayList<>();
        list.add("guildFiles: null");
        pathToGuildFiles = null;
        list.add("startWeek: 1");
        startWeek = 1;
        list.add("builderStatus: true");
        builderStatus = true;
        list.add("trackerStatus: false");
        trackerStatus = false;
        list.add("builderSource: null");
        builderSource = null;
        list.add("buildSource: null");
        buildSource = null;

        return list;
    }

    private String split(String s, int r){
        String[] parts = s.split(": ");
        //System.out.println(parts[r]);
        return parts[r];
    }

    public String getPathToGuildFiles() {
        return pathToGuildFiles;
    }

    public void setPathToGuildFiles(String pathToGuildFiles) {
        this.pathToGuildFiles = pathToGuildFiles;
    }

    public boolean isBuilderStatus() {
        return builderStatus;
    }

    public void setBuilderStatus(boolean builderStatus) {
        this.builderStatus = builderStatus;
    }

    public boolean isTrackerStatus() {
        return trackerStatus;
    }

    public void setTrackerStatus(boolean trackerStatus) {
        this.trackerStatus = trackerStatus;
    }

    public void enable(String s){
        if(s.equalsIgnoreCase("builder")){
            builderStatus = true;
        }else if(s.equalsIgnoreCase("tracker")){
            trackerStatus = true;
        }
    }
    public void disable(String s){
        if(s.equalsIgnoreCase("builder")){
            builderStatus = false;
        }else if(s.equalsIgnoreCase("tracker")){
            trackerStatus = false;
        }
    }

    public String getBuilderSource() {
        return builderSource;
    }

    public void setBuilderSource(String builderSource) {
        this.builderSource = builderSource;
    }

    public String getBuildSource() {
        return buildSource;
    }

    public void setBuildSource(String buildSource) {
        this.buildSource = buildSource;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }
    @Override
    public String toString() {
        return "Config{" +
                "configPath='" + configPath + '\'' +
                ", pathToGuildFiles='" + pathToGuildFiles + '\'' +
                ", builderStatus=" + builderStatus +
                ", trackerStatus=" + trackerStatus +
                '}';
    }

}
