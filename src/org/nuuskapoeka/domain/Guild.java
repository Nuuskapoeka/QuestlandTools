package org.nuuskapoeka.domain;

import org.nuuskapoeka.tools.Writer;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Guild {

    private String name;

    private List<Hero> heroes;

    private List<Integer> events;

    public Guild(String name){
        this.name = name;

        this.heroes = new ArrayList<>();

        this.events = new ArrayList<>();
    }
    public void addHero(Hero h){
        this.heroes.add(h);
    }

    public Hero getHero(String s){
        for(Hero h : this.heroes){
            if(h.getHeroName().equalsIgnoreCase(s)) {
                return h;
            }
        }
        return null;
    }

    public List<Hero> getHeroes(){
        return this.heroes;
    }

    public List<Integer> getEvents(){
        return this.events;
    }

    public int getMaxScores(int week){

        int maxScores = 0;

        for (Hero h : this.heroes){
            if(h.getTickets(week)<=-1){
                continue;
            }
            if(h.getTrophies(week) == 9.06*1000000){
                maxScores++;
            }
        }
        return maxScores;
    }

    public int getGuildScore(int week){

        List<Hero> topThirtyHitters = new ArrayList<>();

        int players;

        for(Hero h : this.heroes){
            if(h.getTickets(week)<=-1){
                continue;
            }
            topThirtyHitters.add(h);
            if(topThirtyHitters.size()>30){
                topThirtyHitters.remove(findLowestTrophies(topThirtyHitters, week));
            }
        }

        int totalTrophies = 0;

        int i = 1;

        Collections.sort(topThirtyHitters, new Comparator<Hero>() {
            @Override
            public int compare(Hero h1, Hero h2) {
                return Double.compare(h2.getTrophies(week), h1.getTrophies(week));
            }
        });

        for(Hero h : topThirtyHitters){
            //System.out.println("    " + i + ". " + h.getHeroName() + " : " + h.getTrophies(week));
            totalTrophies += h.getTrophies(week);
            //System.out.println(h.getHeroName() + ": " + h.getTrophies(week));
        }
        System.out.println("  " + week + ". " + totalTrophies);
        for(Hero h : topThirtyHitters){
            System.out.println("    " + i + ". " + h.getHeroName() + " : " + h.getTrophies(week));
            i++;
        }
        System.out.println();

        if(totalTrophies==0){
            return -1;
        }

        return totalTrophies;
    }

    public List<Integer> getGuildScores(){
        List<Integer> guildScores = new ArrayList<>();

        for(int i = 5; i<Integer.MAX_VALUE;i++){
            if(getGuildScore(i)<0){
                break;
            }
            this.events.add(i);
            //System.out.println(i);
            System.out.println(getGuildScore(i));
            guildScores.add(getGuildScore(i));
        }
        System.out.println(this.events);

        return guildScores;
    }

    public List<Integer> getAverageTickets(){
        List<Integer> tickets = new ArrayList<>();

        for(int i = 5; i<Integer.MAX_VALUE;i++){
            if(getAverageTickets(i)<0){
                break;
            }
            this.events.add(i);
            //System.out.println(i);
            System.out.println(getAverageTickets(i));
            tickets.add(getAverageTickets(i));
        }
        System.out.println(this.events);

        return tickets;
    }

    public List<Integer> getAverageHeroPower(){
        List<Integer> avgHeroPower = new ArrayList<>();

        System.out.println("power");

        for(int i = 5; i<events.size();i++){
            System.out.println("power");
            int avgPowerPerWeek = 0;
            int memberCount = 0;
            for(Hero h : heroes){
                if(h.getHeroPower(i)<0){
                    System.out.println(h.getHeroName()+ " returning");
                }
                System.out.println(i);
                avgPowerPerWeek+=h.getHeroPower(i);
                memberCount++;
            }
            System.out.println(avgPowerPerWeek);
            avgHeroPower.add(avgPowerPerWeek);
            avgPowerPerWeek = 0;
            memberCount = 0;
        }
        System.out.println(this.events);

        return avgHeroPower;
    }

    public List<Hero> getTopHitters(){

        List<Hero> hitters = new ArrayList<>();

        for(Hero h : this.heroes){
            if(hitters.size()>10){
                Hero lowest = findLowestHitter(hitters);
                hitters.remove(lowest);
            }
            if(h.getAmountOfEvents() >= 3){
                hitters.add(h);
            }
        }

        return hitters;
    }

    public List<Hero> getTopHitters(int week){

        List<Hero> hitters = new ArrayList<>();

        for(Hero h : this.heroes){
            if(hitters.size()>10){
                Hero lowest = findLowestHitter(hitters, week);
                hitters.remove(lowest);
            }
            hitters.add(h);
        }

        return hitters;
    }

    public List<Hero> getLowHitters(){

        List<Hero> hitters = new ArrayList<>();

        for(Hero h : this.heroes){
            if(h.getHitterValue()>=0){
                hitters.add(h);
            }
            if(hitters.size() > 10){
                Hero highest = findHighestHitter(hitters);
                hitters.remove(highest);
            }
        }

        return hitters;
    }

    public List<Hero> getLowHitters(int week){

        List<Hero> hitters = new ArrayList<>();

        for(Hero h : this.heroes){
            if(hitters.size() > 10){
                Hero highest = findHighestHitter(hitters, week);
                hitters.remove(highest);
            }
            if(h.getHitterValue(week)>=0){
                hitters.add(h);
            }
        }

        return hitters;
    }

    public List<Hero> getTopRisers(int week){

        List<Hero> topRisers = new ArrayList<>();

        for(Hero h : this.heroes){
            if(topRisers.size()>10){
                Hero lowest = findLowestRiser(topRisers, week);
                topRisers.remove(lowest);
            }
            if(h.getAmountOfEvents() >= 3){
                topRisers.add(h);
            }
        }

        return topRisers;

    }

    public Hero findLowestHitter(List<Hero> highest){
        Hero lowest = new Hero("DUMMY",Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);

        for(Hero h : highest){
            if(h.getHitterValue() < lowest.getHitterValue()){
                lowest = h;
            }
        }

        return lowest;
    }

    public Hero findLowestHitter(List<Hero> highest, int week){
        Hero lowest = new Hero("DUMMY",Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);

        for(Hero h : highest){
            if(h.getHitterValue(week) < lowest.getHitterValue()){
                lowest = h;
            }
        }

        return lowest;
    }

    public Hero findHighestHitter(List<Hero> lowest){
        Hero highest = new Hero("DUMMY",Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);

        for(Hero h : lowest){
            if(h.getHitterValue() > highest.getHitterValue()){
                highest = h;
            }
        }

        return highest;
    }

    public Hero findHighestHitter(List<Hero> lowest, int week){
        Hero highest = new Hero("DUMMY",Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);

        for(Hero h : lowest){
            if(h.getHitterValue(week) > highest.getHitterValue()){
                highest = h;
            }
        }

        return highest;
    }

    public List<Hero> getLowTicketUsers(){

        List<Hero> heroes = new ArrayList<>();

        for(Hero h : this.heroes){
            if(heroes.size()>10){
                Hero highest = findHighestTicketUser(heroes);
                heroes.remove(highest);
            }
            heroes.add(h);
        }
        return heroes;
    }

    public List<Hero> getLowTicketUsers(int week){

        List<Hero> heroes = new ArrayList<>();

        for(Hero h : this.heroes){
            if(heroes.size()>10){
                Hero highest = findHighestTicketUser(heroes, week);
                heroes.remove(highest);
            }
            if(h.getTickets(week)>=0&&h.getTickets(week)<=90){
                heroes.add(h);
            }
        }
        return heroes;
    }

    public List<Hero> getTopTicketUsers(){

        List<Hero> heroes = new ArrayList<>();

        for(Hero h : this.heroes){
            if(h.getAverageTicketsUsed() > 90){
                if(h.getAmountOfEvents() >= 3){
                    heroes.add(h);
                }
            }
            if(heroes.size()<10&&!heroes.contains(h)){
                if(h.getAmountOfEvents() >= 3){
                    heroes.add(h);
                }
            }
        }
        return heroes;
    }

    public List<Hero> getTopTicketUsers(int week){

        List<Hero> heroes = new ArrayList<>();

        for(Hero h : this.heroes){
            heroes.add(h);
        }
        return heroes;
    }

    public Hero findLowestTicketUser(List<Hero> highest){
        Hero lowest = new Hero("DUMMY",Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);

        for(Hero h : highest){
            if(h.getAverageTicketsUsed() < lowest.getAverageTicketsUsed()){
                lowest = h;
            }
        }

        return lowest;
    }

    public Hero findLowestTicketUser(List<Hero> highest, int week){
        Hero lowest = new Hero("DUMMY",Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);

        for(Hero h : highest){
            if(h.getTickets(week) < lowest.getTickets(week)){
                lowest = h;
            }
        }

        return lowest;
    }

    public Hero findHighestTicketUser(List<Hero> lowest){
        Hero highest = new Hero("DUMMY",Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);

        for(Hero h : lowest){
            if(h.getAverageTicketsUsed() > highest.getAverageTicketsUsed()){
                highest = h;
            }
        }

        return highest;
    }

    public Hero findHighestTicketUser(List<Hero> lowest, int week){
        Hero highest = new Hero("DUMMY",Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);

        for(Hero h : lowest){
            if(h.getTickets(week) > highest.getTickets(week)){
                highest = h;
            }
        }

        return highest;
    }

    public Hero findLowestTrophies(List<Hero> highest, int week){
        Hero lowest = new Hero("DUMMY",Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
        lowest.setGuildTrophies(Integer.MAX_VALUE);
        for(Hero h : highest){
            if (h.getTrophies(week)<lowest.getTrophies(week)){
                lowest = h;
            }
        }
        return lowest;
    }

    public Hero findLowestRiser(List<Hero> highest, int week){
        Hero lowest = new Hero("DUMMY",Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);

        for(Hero h : highest){
            if(h.getTrophyDiff(week)<lowest.getTrophyDiff(week)){
                lowest = h;
            }
        }

        return lowest;
    }

    //averages


    public int getAverageTicketUse(int week){

        int ticketsUsed = 0;
        int heroesAmnt = 0;

        for(Hero h : this.heroes){
            if(h.getTickets(week)>-1){
                ticketsUsed+=h.getTickets(week);
                heroesAmnt++;
            }
        }

        return ticketsUsed/heroesAmnt;
    }

    public int getAverageTrophies(int week){

        int trophies = 0;
        int heroesAmnt = 0;

        for(Hero h : this.heroes){
            if(h.getTrophies(week)>-1){
                trophies+=h.getTrophies(week);
                heroesAmnt++;
            }
        }

        if(heroesAmnt <=0){
            return 0;
        }

        return trophies/heroesAmnt;
    }

    public int getAverageTickets(int week){

        int tickets = 0;
        int heroesAmnt = 0;

        for(Hero h : this.heroes){
            if(h.getTrophies(week)>-1){
                tickets+=h.getTickets(week);
                heroesAmnt++;
            }
        }

        //System.out.println(heroesAmnt);

        if(heroesAmnt <= 0){
            return 0;
        }

        //System.out.println(heroesAmnt);

        return tickets/heroesAmnt;
    }

    //saves


    public String save(int week){
        return "";
    }

    public void customSave(String filePath, String statsToSave, int week, String command){

        String[] parts = statsToSave.split(",");
        List<String> saves = new ArrayList<>();

        if(week%2==1){
            saves.add("BLUE," + week + "," + getGuildScore(week) + "\n");
        }else{
            saves.add("RED," + week + "," + getGuildScore(week) + "\n");
        }

        saves.add(statsToSave.toUpperCase());

        try {
            Writer writer = new Writer(new File(filePath));
            for(Hero h : sortList(this.heroes, week, command)){

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

    public void customSaveEvent(String filePath, String statsToSave, int week, String command){
        String[] parts = statsToSave.split(",");
        List<String> saves = new ArrayList<>();

        if(week%2==1){
            saves.add("BLUE," + week + "," + getGuildScore(week) + "\n");
        }else{
            saves.add("RED," + week + "," + getGuildScore(week) + "\n");
        }

        saves.add(statsToSave.toUpperCase());

        try {
            Writer writer = new Writer(new File(filePath));
            for(Hero h : sortList(this.heroes, week, command)){

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

    public List<Hero> sortList(List<Hero> list, int week, String command){

        String[] parts = command.split(" ");

        if(parts.length<=7){
            return list;
        }

        if(parts[6].equals("sort")) {
            if(parts[8].equals("tickets")){
                System.out.println("!");
                Collections.sort(list, new Comparator<Hero>() {
                    @Override
                    public int compare(Hero h1, Hero h2) {
                        return Double.compare(h2.getTickets(week), h1.getTickets(week));
                    }
                });
            }else if(parts[8].equals("trophies")){
                Collections.sort(list, new Comparator<Hero>() {
                    @Override
                    public int compare(Hero h1, Hero h2) {
                        return Double.compare(h2.getTrophies(week), h1.getTrophies(week));
                    }
                });
            }else if(parts[8].equals("trophies_per_hit")){
                Collections.sort(list, new Comparator<Hero>() {
                    @Override
                    public int compare(Hero h1, Hero h2) {
                        return Double.compare(h2.getTrophiesPerHit(week), h1.getTrophiesPerHit(week));
                    }
                });
            }else if(parts[8].equals("hero_power")){
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