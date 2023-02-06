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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public TalentPart getOneSpirit() {
        return oneSpirit;
    }

    public void setOneSpirit(TalentPart oneSpirit) {
        this.oneSpirit = oneSpirit;
    }

    public TalentPart getTwoSpirit() {
        return twoSpirit;
    }

    public void setTwoSpirit(TalentPart twoSpirit) {
        this.twoSpirit = twoSpirit;
    }

    public TalentPart getFourSpirit() {
        return fourSpirit;
    }

    public void setFourSpirit(TalentPart fourSpirit) {
        this.fourSpirit = fourSpirit;
    }

    @Override
    public String toString() {
        return "Talent{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", color='" + color + '\'' +
                ", \noneSpirit=" + oneSpirit +
                ", \ntwoSpirit=" + twoSpirit +
                ", \nfourSpirit=" + fourSpirit +
                '}';
    }
}
