package org.nuuskapoeka.logic;

import org.nuuskapoeka.domain.Boss;
import org.nuuskapoeka.domain.Talent;
import org.nuuskapoeka.domain.TalentPart;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TalentManager {

    private String loadUrl;
    private List<Talent> talentList;

    public TalentManager(String url){
        this.loadUrl = url;
        talentList = new ArrayList<>();
    }
    public void load(){
        try{
            URL url = new URL(loadUrl);
            Scanner r = new Scanner(url.openStream());
            r.nextLine();


            Talent t = null;
            while(r.hasNextLine()){
                String s = r.nextLine();
                String[] b = s.split(",");
                if(t==null || !b[0].equalsIgnoreCase(t.getName())){
                    t = new Talent(b[0],Integer.parseInt(b[2]),b[3]);
                }
                //System.out.println(s);
                TalentPart tp = new TalentPart(b[0],
                                Integer.parseInt(b[1]),
                                Integer.parseInt(b[2]),
                                b[3],
                                Double.parseDouble(b[4]),
                                Double.parseDouble(b[5]),
                                Double.parseDouble(b[6]),
                                Integer.parseInt(b[7]),
                                Double.parseDouble(b[8]),
                                Double.parseDouble(b[9]),
                                Double.parseDouble(b[10]),
                                Double.parseDouble(b[11]),
                                b[12]);
                if(Integer.parseInt(b[1]) == 1){
                    t.setOneSpirit(tp);
                }else if(Integer.parseInt(b[1]) == 2){
                    t.setTwoSpirit(tp);
                }
                else if(Integer.parseInt(b[1]) == 4){
                    t.setFourSpirit(tp);
                }

                if(!talentList.contains(t)){
                    talentList.add(t);
                }
                //System.out.println(t);
            }
            System.out.println("successfully loaded " + talentList.size() + " talents");
        }catch(IOException e){
            System.out.println("file not found");
        }
    }
    public Talent getTalent(String name){
        for(Talent t : talentList){
            if(t.getName().equalsIgnoreCase(name)){
                return t;
            }
        }
        return null;
    }
    public void getBloodlustComboDamage(){

    }
}
