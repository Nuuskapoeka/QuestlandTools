package org.nuuskapoeka.domain;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private String emblem;
    private String name;
    private String type;
    private String slot;
    private int potential;
    private int health;
    private int attack;
    private int defence;
    private int magic;

    private int healthInc;
    private int attackInc;
    private int defenceInc;
    private int magicInc;

    private List<String> links;
    private String typeID;

    public Item(String emblem,
                String name,
                String type,
                String slot,
                int potential,
                int health,
                int attack,
                int defence,
                int magic,
                String link1,
                String link2,
                String link3,
                String typeID) {
        this.emblem = emblem;
        this.name = name;
        this.type = type;
        this.slot = slot;
        this.potential = potential;
        this.health = health;
        this.attack = attack;
        this.defence = defence;
        this.magic = magic;
        this.links = new ArrayList<>();
        this.links.add(link1);
        this.links.add(link2);
        if(!(link3 == null)){
            this.links.add(link3);
        }else{
            this.links.add(null);
        }
        this.typeID = typeID;
    }

    public int getStats(){
        if(this.type.equalsIgnoreCase("health")){
            return health;
        }else if(this.type.equalsIgnoreCase("attack")){
            return attack;
        }else if(this.type.equalsIgnoreCase("defence")){
            return defence;
        }else if(this.type.equalsIgnoreCase("magic")){
            return magic;
        }
        return 0;
    }

    public String getEmblem() {
        return emblem;
    }

    public void setEmblem(String emblem) {
        this.emblem = emblem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public int getPotential() {
        return potential;
    }

    public void setPotential(int potential) {
        this.potential = potential;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public int getHealthInc() {

        int sum = health+attack+defence+magic;
        double pot = (double)potential/sum;
        return (int) Math.round(health*pot);
    }

    public void setHealthInc(int healthInc) {
        this.healthInc = healthInc;
    }

    public int getAttackInc() {
        int sum = health+attack+defence+magic;
        double pot = (double)potential/sum;
        return (int) Math.round(attack*pot);
    }

    public void setAttackInc(int attackInc) {
        this.attackInc = attackInc;
    }

    public int getDefenceInc() {
        int sum = health+attack+defence+magic;
        double pot = (double)potential/sum;
        return (int) Math.round(defence*pot);
    }

    public void setDefenceInc(int defenceInc) {
        this.defenceInc = defenceInc;
    }

    public int getMagicInc() {
        int sum = health+attack+defence+magic;
        double pot = (double)potential/sum;
        return (int) Math.round(magic*pot);
    }

    public void setMagicInc(int magicInc) {
        this.magicInc = magicInc;
    }


    public List<String> getLinks(){
        return this.links;
    }

    public String toCSV(){
        return emblem + "," + name + "," + type + "," + slot + "," + potential + "," +
                health + "," + attack + "," + defence + "," + magic + "," +
                links.get(0) + "," + links.get(1) + "," + links.get(2);

    }

    private int getHighestStat(){
        if(type.equalsIgnoreCase("health")){
            return this.health;
        }else if(type.equalsIgnoreCase("attack")){
            return this.attack;
        }else if(type.equalsIgnoreCase("defence")){
            return this.defence;
        }if(type.equalsIgnoreCase("magic")){
            return this.magic;
        }
        return 0;
    }

    public String getIdentifier(){
        String identifier = "";

        identifier+=Character.toUpperCase(emblem.charAt(0));
        identifier+=Character.toLowerCase(emblem.charAt(1));
        identifier+=Character.toLowerCase(emblem.charAt(2));
        identifier+=typeID;
        identifier+=Character.toUpperCase(slot.charAt(0));
        identifier+=Character.toLowerCase(slot.charAt(1));
        identifier+=Character.toLowerCase(slot.charAt(2));
        identifier+=Character.toLowerCase(slot.charAt(3));

        if(potential >= 270){
            identifier += 5;
        }else if(potential >= 235){
            identifier += 4;
        }

        return identifier;
    }

    @Override
    public String toString() {
        return name + "{" +
                "\n    identifier = " + getIdentifier() +
                "\n    emblem = " + emblem +
                "\n    type = " + type +
                "\n    slot = " + slot +
                "\n    potential = " + potential +
                "\n    stats" +
                "\n       health = " + health +
                "\n       attack = " + attack +
                "\n       defence = " + defence +
                "\n       magic = " + magic +
                "\n    links" +
                "\n       " + links.get(0) + 
                "\n       " + links.get(1) +
                "\n       " + links.get(2) +
                '}';
    }
}
