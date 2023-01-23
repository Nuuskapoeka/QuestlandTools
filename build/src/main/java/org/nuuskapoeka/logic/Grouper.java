package org.nuuskapoeka.logic;

import org.nuuskapoeka.domain.Group;
import org.nuuskapoeka.domain.Hero;
import org.nuuskapoeka.tools.Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Grouper {

    private String statsFile;

    private List<Hero> heroes;

    private static int damageToDeal = 102500801; //divided by 1000

    public Grouper(String stats){
        statsFile = stats;
    }
    public List<Hero> readStats() throws FileNotFoundException {

        List<Hero> heroStats = new ArrayList<>();

        Reader f = new Reader(new File(this.statsFile));

        for(String s : f.read()){
            String[] stats = s.split(",");
            Hero h = new Hero(stats[0],
                    Integer.parseInt(stats[1]),
                    Integer.parseInt(stats[2]),
                    Integer.parseInt(stats[3]),
                    Integer.parseInt(stats[4]),
                    Double.parseDouble(stats[5]));
            heroStats.add(h);
        }
        heroes = heroStats;
        return heroStats;
    }
    public List<Hero> findSoloers() throws FileNotFoundException {

        int damageToDeal = 102500801; //divided by 1000
        List<Hero> soloers = new ArrayList<>();



        for(Hero h : readStats()){

            if((blueDamage(h))>damageToDeal){
                soloers.add(h);
            }
        }

        return soloers;
    }
    public int chillingDamage(Hero h){

        double damage = h.getMagic()*1.4*3*1.15;

        return (int)damage;
    }
    public int fistDamage(Hero h){

        double damage = 0.8*((h.getMagic()*3+h.getAttack()*2)/2);

        return (int)damage;
    }
    //damage divided by 1k
    public int blueDamage(Hero h){

        long damage = ((long)(chillingDamage(h) + (long)fistDamage(h))*(int)h.getMulti())*19;
        //System.out.println(h.getMulti());
        //System.out.println(h.getHeroName() + " : " + damage);
        return (int)(damage/1000);
    }
    public void makeGroups(){
        List<Group> groups = new ArrayList<>();

        while(heroes.size()>0){
            Group group = new Group();
            group.add(heroes.get(0));
            Hero firstHero = heroes.get(0);
            heroes.remove(0);
            Hero h = findNear(heroes, damageToDeal-group.getGroupDamage());
            heroes.remove(h);
            System.out.println("Left Over : " + (damageToDeal-blueDamage(firstHero)));
            if(h != null){
                group.add(h);
                groups.add(group);
                continue;
            }else{
                Hero h1 = findLower(heroes, damageToDeal-blueDamage(firstHero));
                if(h1 != null){
                    heroes.remove(h1);
                    group.add(h1);
                    Hero h2 = findNear(heroes, damageToDeal-group.getGroupDamage());
                    if(h2!= null){
                        heroes.remove(h2);
                        group.add(h2);
                        groups.add(group);
                        continue;
                    }
                }
            }
        }

        int i = 1;

        for(Group group : groups){
            System.out.println("Group " + i);
            for(Hero hero : group.getGroup()){
                System.out.println(hero.getHeroName() + " : " + blueDamage(hero));
            }
            i++;
        }

        System.out.println("Taggers");
        for(Hero h : heroes){
            System.out.println(h.getHeroName() + " : " + blueDamage(h));
        }
    }
    private Hero findNear(List<Hero> heroes, int damage){

        boolean found = false;

        for(Hero h : heroes){
            if(blueDamage(h) < damage*1.05 && blueDamage(h) > damage/1.05){
                found = true;
                return h;
            }
        }

        for(Hero h : heroes){
            if(blueDamage(h) < damage*1.1 && blueDamage(h) > damage/1.1){
                found = true;
                return h;
            }
        }


        return null;
    }
    private Hero findLower(List<Hero> heroes, int damage){
        for(Hero h : heroes){
            if(blueDamage(h) < damage){
                return h;
            }
        }
        return null;
    }
}
