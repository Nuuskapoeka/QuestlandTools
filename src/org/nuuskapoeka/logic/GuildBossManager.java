package org.nuuskapoeka.logic;

import org.nuuskapoeka.domain.Boss;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GuildBossManager {

    private String loadUrl;
    private List<Boss> bosses;

    public GuildBossManager(String url){
        loadUrl = url;
        bosses = new ArrayList<>();
    }
    public void load(){
        try{
            URL url = new URL(loadUrl);
            Scanner r = new Scanner(url.openStream());
            r.nextLine();
            while(r.hasNextLine()){
                String[] b = r.nextLine().split(",");
                bosses.add(new Boss(Integer.parseInt(b[0]),
                        Integer.parseInt(b[1]),
                        Long.parseLong(b[2]),
                        Long.parseLong(b[3]),
                        Long.parseLong(b[4]),
                        Long.parseLong(b[5])));
            }
            System.out.println("successfully loaded " + bosses.size() + " bosses");
        }catch(IOException e){
            System.out.println("file not found");
        }
    }
    public int highestSolo(int health,int attack,int defence,int magic){
        return -1;
    }
    public long estimatedDamage(int health,int attack,int defence,int magic, double weaponPassive, double linkPassive){

        return -1;
    }
}
