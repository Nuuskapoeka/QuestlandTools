package org.nuuskapoeka.domain;

import java.util.ArrayList;
import java.util.List;

public class BuildSlot {

    private Item item;
    private boolean isLinked;
    private boolean isOrbLinked;

    private List<String> activeLinks;
    private boolean oneStatReforge;

    private double healthReforge;
    private double attackReforge;
    private double defencReforge;
    private double magicReforge;

    public BuildSlot(){
        this.isLinked = false;
        this.isOrbLinked = false;
    }

    public BuildSlot(Item item){
        this.item = item;
        this.isLinked = false;
        this.isOrbLinked = false;
        this.activeLinks = new ArrayList<>();
        setDefaultReforge();
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isLinked() {
        return isLinked;
    }

    public void setLinked(boolean linked) {
        isLinked = linked;
    }
    public void setOneStatReforge(boolean oneStatReforge){
        this.oneStatReforge = oneStatReforge;
    }

    public boolean getOneStatReforge() {
        return oneStatReforge;
    }

    public List<String> getActiveLinks(){
        return this.activeLinks;
    }
    public void addActiveLink(String s){
        if(activeLinks.size()<=3){
            activeLinks.add(s);
        }
    }

    private String activeLinksToString(){
        String links = "";
        for (String s : activeLinks){
            links+="\n  " + s;
        }
        return links;
    }
    public String activeLinksToStringStars(){
        String links = "[";
        for (String s : activeLinks){
            links+=" * ";
        }
        return links + "]";
    }

    /*
    @Override
    public String toString() {
        return "BuildSlot{" +
                "item=" + item.getName() +
                ", isLinked=" + isLinked +
                '}';
    }
    */
    @Override
    public String toString() {
        if(item == null){
            return null;
        }
        return item.getIdentifier() +"{" +
                "links:" + activeLinksToStringStars() +
                '}';
    }

    public double getHealthReforge() {
        return healthReforge;
    }

    public void setHealthReforge(double healthReforge) {
        this.healthReforge = healthReforge;
    }

    public double getAttackReforge() {
        return attackReforge;
    }

    public void setAttackReforge(double attackReforge) {
        this.attackReforge = attackReforge;
    }

    public double getDefencReforge() {
        return defencReforge;
    }

    public void setDefencReforge(double defencReforge) {
        this.defencReforge = defencReforge;
    }

    public double getMagicReforge() {
        return magicReforge;
    }

    public void setMagicReforge(double magicReforge) {
        this.magicReforge = magicReforge;
    }
    private void setDefaultReforge(){
        if(item.getType() == null){
            healthReforge = 0.5;
            attackReforge = 0.5;
            defencReforge = 0;
            magicReforge = 0;
            return;
        }
        if(item.getType().equalsIgnoreCase("attack")){
            healthReforge = 0.5;
            attackReforge = 0.5;
            defencReforge = 0;
            magicReforge = 0;
        }else if(item.getType().equalsIgnoreCase("defence")){
            healthReforge = 0;
            attackReforge = 0.5;
            defencReforge = 0.5;
            magicReforge = 0;
        }else if(item.getType().equalsIgnoreCase("magic")){
            healthReforge = 0;
            attackReforge = 0;
            defencReforge = 0.5;
            magicReforge = 0.5;
        }else if(item.getType().equalsIgnoreCase("health")){
            healthReforge = 1;
            attackReforge = 0;
            defencReforge = 0;
            magicReforge = 0;
        }
    }
}
