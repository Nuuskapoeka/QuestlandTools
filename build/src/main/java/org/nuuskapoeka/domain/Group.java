package org.nuuskapoeka.domain;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private List<Hero> group;

    public Group(){
        this.group = new ArrayList<>();
    }
    public void add(Hero h){
        group.add(h);
    }
    public int getGroupDamage(){
        int damage = 0;
        for(Hero h : group){
            damage += h.blueDamage();
        }
        return damage;
    }

    public List<Hero> getGroup() {
        return group;
    }

    public void setGroup(List<Hero> group) {
        this.group = group;
    }
}
