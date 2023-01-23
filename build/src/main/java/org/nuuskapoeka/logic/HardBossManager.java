package org.nuuskapoeka.logic;

import org.nuuskapoeka.domain.Boss;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HardBossManager {

    private String loadUrl;
    private List<Boss> bosses;
    private TalentManager talentManager;

    public HardBossManager(String url) {
        loadUrl = url;
        bosses = new ArrayList<>();
        talentManager = new TalentManager("https://docs.google.com/spreadsheets/d/e/2PACX-1vQACdbvpCIg7Uri2UZ_ZpoPLqEQzB0tWtnf8J8awM7s7DwvZQkoet1V-8TYyEKYPPo_CtU4QdtQDHxo/pub?gid=851318924&single=true&output=csv");
        talentManager.load();
    }

    public void load() {
        try {
            URL url = new URL(loadUrl);
            Scanner r = new Scanner(url.openStream());
            r.nextLine();
            while (r.hasNextLine()) {
                String[] b = r.nextLine().split(",");
                bosses.add(new Boss(Integer.parseInt(b[0]),
                        Integer.parseInt(b[1]),
                        Long.parseLong(b[2]),
                        Long.parseLong(b[3]),
                        Long.parseLong(b[4]),
                        Long.parseLong(b[5])));
            }
            System.out.println("successfully loaded " + bosses.size() + " bosses");
        } catch (IOException e) {
            System.out.println("file not found");
        }
    }
    public int getHighestHardBossBlue2(int health,int attack,int defence,int magic,int multi, boolean lotw){
        int boss = 0;
        for(Boss b : bosses){
            long magicToTank = (long) (b.getMagic()*0.9*0.8*0.85)/5;
            if(lotw){
                magicToTank*=0.8;
            }
            if(magicToTank<health*multi){

                boss++;
            }else{
                System.out.println((long)(defence*multi*5)-b.getAttack());
                System.out.println((long)(defence*multi*5));
                System.out.println(b.getAttack());
                return boss;
            }
        }
        return -1;
    }
    public int getHighestHardBossBlue6(int health,int attack,int defence,int magic,int multi,boolean lotw){
        int bossMagic = 0;
        int bossMelee = 0;
        for(Boss b : bosses){
            long magicToTank = (long) (b.getMagic()*0.9*0.8*0.85)/25;
            if(lotw){
                magicToTank*=0.8;
            }
            if(magicToTank<health*multi){
                bossMagic++;
            }else{
                System.out.println((((long)(health*multi)+((long)(defence*multi)-b.getAttack()/25))/(long)(health*multi))*100);
                System.out.println((long)(health*multi)+(long)(defence*multi));
                System.out.println(b.getAttack()/5);
                return bossMagic;
            }
            if(bossMagic == 150){
                return 150;
            }
        }
        return -1;
    }
    public int getHighestHardBossRed2(int health,int attack,int defence,int magic,int multi, boolean res, boolean runic){
        long playerAttack = (long) (talentManager.getTalent("Bloodlust").getFourSpirit().getDamage(10)*attack*1.6*2*14)*multi;
        if(!runic){
            playerAttack*=1.2;
        }
        if(res){
            playerAttack*=2;
        }
        System.out.println(playerAttack);
        int playerHealth = health*multi;
        if(runic){
            playerHealth+=defence*0.25*multi;
        }
        int bossHealth = 0;
        for(Boss b : bosses){
            long magicToTank = (long) (b.getMagic()*0.85)/5;
            if(magicToTank<playerHealth){
                bossHealth++;
            }else{
                break;
            }
        }
        int bossAttack = 0;
        for(Boss b : bosses){
            long damageToDeal = (long) (b.getHealth()+b.getDefence());
            if(damageToDeal<playerAttack){
                bossAttack++;
            }else{
                break;
            }
        }

        System.out.println(bossAttack + ", " + bossHealth);

        return Math.min(bossAttack,bossHealth);
    }
    public int getHighestHardBossRed6(int health,int attack,int defence,int magic,int multi, boolean res, boolean runic){
        long playerAttack = (long) (talentManager.getTalent("Bloodlust").getFourSpirit().getDamage(10)*attack*1.6*2*14)*5*multi;
        if(!runic){
            playerAttack*=1.2;
        }
        if(res){
            playerAttack*=2;
        }
        System.out.println(playerAttack);
        int playerHealth = health*multi;
        if(runic){
            playerHealth+=defence*0.25*multi;
        }
        int bossHealth = 0;
        for(Boss b : bosses){
            long magicToTank = (long) (b.getMagic()*0.85)/25;
            if(magicToTank<playerHealth){
                bossHealth++;
            }else{
                break;
            }
        }
        int bossAttack = 0;
        for(Boss b : bosses){
            long damageToDeal = b.getHealth()+b.getDefence();
            if(damageToDeal<playerAttack){
                bossAttack++;
            }else{
                break;
            }
        }
        System.out.println(bossAttack + ", " + bossHealth);
        return Math.min(bossAttack,bossHealth);
    }
}
