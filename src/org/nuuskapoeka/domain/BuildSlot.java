package org.nuuskapoeka.domain;

import java.util.ArrayList;
import java.util.List;

public class BuildSlot {

    private Item item;
    private boolean isLinked;
    private boolean isOrbLinked;

    private List<String> activeLinks;
    private boolean oneStatReforge;

    public BuildSlot(){
        this.isLinked = false;
        this.isOrbLinked = false;
    }
    public BuildSlot(Item item){
        this.item = item;
        this.isLinked = false;
        isOrbLinked = false;
        this.activeLinks = new ArrayList<>();
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
    private String activeLinksToStringStars(){
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
}
