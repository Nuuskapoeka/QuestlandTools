package org.nuuskapoeka.logic;

import org.nuuskapoeka.domain.Boss;
import org.nuuskapoeka.domain.Talent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class TalentManager {

    private String loadUrl;
    private List<Talent> talentList;

    public TalentManager(String url){
        this.loadUrl = url;
    }
    public void load(){
        try{
            URL url = new URL(loadUrl);
            Scanner r = new Scanner(url.openStream());
            r.nextLine();


            Talent t = null;
            while(r.hasNextLine()){
                String[] b = r.nextLine().split(",");
                if(t==null || b[0].equalsIgnoreCase(t.getName())){
                    t = new Talent(b[0],Integer.parseInt(b[2]),b[3]);
                }

                talentList.add(t);
            }
            System.out.println("successfully loaded " + talentList.size() + " bosses");
        }catch(IOException e){
            System.out.println("file not found");
        }
    }
}
