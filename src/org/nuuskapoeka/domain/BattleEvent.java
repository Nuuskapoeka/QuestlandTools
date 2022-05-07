package org.nuuskapoeka.domain;

public class BattleEvent {

    private int week;
    private String weekColor;
    private int averageTokens;
    private int averageTrophies;
    private int averageTicketsUsed;

    public BattleEvent(int week, String weekColor, int averageTrophies, int averageTokens, int averageTicketsUsed){
        this.week = week;
        this.weekColor = weekColor;
        this.averageTrophies = averageTrophies;
        this.averageTokens = averageTokens;
        this.averageTicketsUsed = averageTicketsUsed;
    }
    //returns difference between this and other
    private int differenceTrophies(BattleEvent be){
        return this.averageTrophies-be.averageTrophies;
    }
    @Override
    public String toString(){
        return this.week + "," + this.weekColor;
    }
}
