package org.nuuskapoeka.logic;

import org.nuuskapoeka.domain.Guild;
import org.nuuskapoeka.domain.Hero;
import org.nuuskapoeka.tools.Writer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Saver {

    private Guild g;

    public Saver(Guild g){
        this.g = g;
    }
    public void customSaveEvent(String filePath, String statsToSave, int week, String command){
        String[] parts = statsToSave.split(",");
        List<String> saves = new ArrayList<>();

        if(week%2==1){
            saves.add("BLUE," + week + "," + g.getGuildScore(week) + "\n");
        }else{
            saves.add("RED," + week + "," + g.getGuildScore(week) + "\n");
        }

        saves.add(statsToSave.toUpperCase());

        try {
            Writer writer = new Writer(new File(filePath));
            for(Hero h : sortList(g.getHeroes(), week, command)){

                if(h.getTickets(week)==-1){
                    continue;
                }

                String save = "";

                int i = 0;

                for(String s : parts) {
                    if (s.equalsIgnoreCase("name")) {
                        save += h.getHeroName();
                    } else if (s.equalsIgnoreCase("tickets")) {
                        save += h.getTickets(week);
                    } else if (s.equalsIgnoreCase("trophies")) {
                        save += h.getTrophies(week);
                    }else if(s.equalsIgnoreCase("tokens")){
                        save += h.getTokens(week);
                    }else if(s.equalsIgnoreCase("hero_power")){
                        save += h.getHeroPower(week);
                    }else if(s.equalsIgnoreCase("hitter_score")){
                        save += h.getHitterValue(week);
                    }else if(s.equalsIgnoreCase("trophies_per_hit")){
                        save += h.getTrophiesPerHit(week);
                    }else if(s.equalsIgnoreCase("trophy_diff")){
                        save += h.getTrophyDiff(week);
                    }
                    i++;
                    if(i < parts.length){
                        save += ",";
                    }
                }
                saves.add(save);
            }
            writer.write(saves);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void customSaveGuildTotals(String filePath, String statsToSave, String command){
        String[] parts = statsToSave.split(",");
        List<String> saves = new ArrayList<>();
        g.getGuildScores();
        try {
            Writer writer = new Writer(new File(filePath));
            for(int e : g.getEvents()){
                System.out.println(e);
                String save = e + ",";

                int i = 0;



                for(String s : parts) {

                    if(s.contains("guild_score")){
                        save += g.getGuildScore(e);
                    }else if(s.contains("ticket_use")){
                        save += g.getAverageTicketUse(e);
                    }else if(s.equals("trophies")){
                        save += g.getAverageTrophies(e);
                    }
                    i++;
                    if(i < parts.length){
                        save += ",";
                    }
                }
                System.out.println(save);
                saves.add(save);
                System.out.println("added: " + saves.size());
            }

            writer.write(saves);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<Hero> sortList(List<Hero> list, int week, String command){

        String[] parts = command.split(" ");

        if(parts.length<=8){
            return list;
        }

        if(parts[7].equals("sort")) {
            if(parts[9].equals("tickets")){
                System.out.println("!");
                Collections.sort(list, new Comparator<Hero>() {
                    @Override
                    public int compare(Hero h1, Hero h2) {
                        return Double.compare(h2.getTickets(week), h1.getTickets(week));
                    }
                });
            }else if(parts[9].equals("trophies")){
                Collections.sort(list, new Comparator<Hero>() {
                    @Override
                    public int compare(Hero h1, Hero h2) {
                        return Double.compare(h2.getTrophies(week), h1.getTrophies(week));
                    }
                });
            }else if(parts[9].equals("trophies_per_hit")){
                Collections.sort(list, new Comparator<Hero>() {
                    @Override
                    public int compare(Hero h1, Hero h2) {
                        return Double.compare(h2.getTrophiesPerHit(week), h1.getTrophiesPerHit(week));
                    }
                });
            }else if(parts[9].equals("hero_power")){
                Collections.sort(list, new Comparator<Hero>() {
                    @Override
                    public int compare(Hero h1, Hero h2) {
                        return Double.compare(h2.getHeroPower(week), h1.getHeroPower(week));
                    }
                });
            }
        }
        return list;
    }
}
