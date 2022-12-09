package org.nuuskapoeka.domain;

public class Talent {

    private String name;
    private int cost;
    private String color;

    private TalentPart oneSpirit;
    private TalentPart twoSpirit;
    private TalentPart fourSpirit;
    public Talent(String name, int cost, String color){
        this.name = name;
        this.cost = cost;
        this.color = color;
    }
}
