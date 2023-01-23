package org.nuuskapoeka.logic;

import org.nuuskapoeka.domain.Hero;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Analyze {

    private File guildStatistics;
    private List<Hero> members;

    public Analyze(File file){
        guildStatistics = file;
        members = new ArrayList<>();

        try {
            read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void read() throws FileNotFoundException {
        Scanner r = new Scanner(guildStatistics);
        int i = 1;
        while(r.hasNextLine()){
            String[] parts = r.nextLine().split(",");
            if(i>1){
                members.add(new Hero(parts[0], Integer.parseInt(parts[5]), Integer.parseInt(parts[26]),Integer.parseInt(parts[12]), Integer.parseInt(parts[24])));
            }
            i++;
        }
    }
    public int getAverageGuildTrophies(){
        int guildTrophiesTotal = 0;
        for(Hero h: this.members){
            guildTrophiesTotal += h.getGuildTrophies();
        }
        return guildTrophiesTotal/this.members.size();
    }
    public int getAverageTicketsUsed(){
        int ticketsUsedTotal = 0;
        for(Hero h: this.members){
            ticketsUsedTotal += h.getTicketsUsed();
        }
        return ticketsUsedTotal/this.members.size();
    }
    public int getAmountWithZeroUsed(){
        int withZeroTickets = 0;
        for(Hero h: this.members){
            if(h.getTicketsUsed()==0){
                withZeroTickets++;
            }
        }
        return withZeroTickets;
    }
    public int getAmountWithAllUsed(){
        int withAllTickets = 0;
        for(Hero h: this.members){
            if(h.getTicketsUsed()==100){
                withAllTickets++;
            }
        }
        return withAllTickets;
    }
    public void analyze(){
        System.out.println("AVERAGE");
        System.out.println("  Guild Trophies:    " + this.getAverageGuildTrophies());
        System.out.println("  Tickets Used:      " + this.getAverageTicketsUsed());
        System.out.println("  Zero tickets used: " + this.getAmountWithZeroUsed());
        System.out.println("  All tickets used:  " + this.getAmountWithAllUsed());
    }
}
