package org.nuuskapoeka.tools;

import org.nuuskapoeka.domain.Guild;
import org.nuuskapoeka.domain.Hero;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StartUp {

    private Guild guild;

    public StartUp(Guild g){
        this.guild = g;
    }
    public String[] splitRow(String s){
        String[] parts = s.split(",",2);

        return parts;
    }
    public void run(){

        List<String> files = new ArrayList<>();
        try {
            Scanner fileScanner = new Scanner(new File("horsemen\\horsemen_battle_events.txt"));
            while(fileScanner.hasNextLine()){
                files.add(fileScanner.nextLine());
            }

            for(String f: files){
                File f2 = new File("horsemen\\2022\\blue_horsemen_5.csv");
                Scanner r = new Scanner(new File(f));
                //System.out.println(f);
                try {
                    r = new Scanner(new File(f));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                String weekNumber;
                int i = 1;
                while(r.hasNextLine()){
                    String event = r.nextLine();
                    String[] parts = event.split(",");
                    String[] heroEvent = event.split(",",2);
                    if(i>1){
                        if(guild.getHero(parts[0]) == null){
                            guild.addHero(new Hero(parts[0], Integer.parseInt(parts[5]), Integer.parseInt(parts[26]),Integer.parseInt(parts[12]), Integer.parseInt(parts[24])));
                        }
                        guild.getHero(parts[0]).addEvent(findWeek(f.toString()) +"," + heroEvent[1]);
                    }
                    i++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    public String findWeek(String fileName){
        String[] parts1 = fileName.split("_");
        String[] parts2 = parts1[parts1.length-1].split("\\.");

        return parts2[0];
    }
}
