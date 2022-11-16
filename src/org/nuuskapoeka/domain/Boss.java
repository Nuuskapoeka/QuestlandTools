package org.nuuskapoeka.domain;

public class Boss {

    private int level;
    private int trophies;
    private long health;
    private long attack;
    private long defence;
    private long magic;

    public Boss(int level, int trophies,long health,long attack,long defence,long magic){
        this.level = level;
        this.trophies = trophies;
        this.health = health;
        this.attack = attack;
        this.defence = defence;
        this.magic = magic;
    }
}
