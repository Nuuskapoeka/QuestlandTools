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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTrophies() {
        return trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public long getHealth() {
        return health;
    }

    public void setHealth(long health) {
        this.health = health;
    }

    public long getAttack() {
        return attack;
    }

    public void setAttack(long attack) {
        this.attack = attack;
    }

    public long getDefence() {
        return defence;
    }

    public void setDefence(long defence) {
        this.defence = defence;
    }

    public long getMagic() {
        return magic;
    }

    public void setMagic(long magic) {
        this.magic = magic;
    }
}
