package org.nuuskapoeka.logic;

import org.nuuskapoeka.domain.CraftSlot;
import org.nuuskapoeka.domain.Item;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CraftSolver {

    private Items itemList;

    private int maxPotential;
    private int minPotential;

    private int maxEmblems;

    private List<CraftSlot> craftSlots;

    public CraftSolver(Items itemList){
        this.craftSlots = new ArrayList<>();
        this.itemList = itemList;

    }

    public void start(){
        Scanner r = new Scanner(System.in);

        readRequirements(r);
    }
    public void readRequirements(Scanner r){

        System.out.println("Min average potential");
        minPotential = Integer.parseInt(r.nextLine());
        System.out.println("Max average potential");
        maxPotential = Integer.parseInt(r.nextLine());
        System.out.println("Max amount of emblems");
        maxEmblems = Integer.parseInt(r.nextLine());

        System.out.println("potential range: " + minPotential + " - " + maxPotential);

        setSlots();

        solve();
        printSlots();
        System.out.println(totalPotential());
        //calculateNeededPotential(totalPotential(), 238,craftSlots.size());
    }
    private void setSlots(){
        craftSlots.add(new CraftSlot("abyss","any"));
        craftSlots.add(new CraftSlot("any","helm"));
        craftSlots.add(new CraftSlot("thunder","any"));
        craftSlots.add(new CraftSlot("any","gloves"));
        craftSlots.add(new CraftSlot("any","boots"));
        craftSlots.add(new CraftSlot("hex","any"));
    }
    public void printSlots(){
        for (CraftSlot cs : craftSlots){
            System.out.println(cs);
        }
    }
    public void addCraftSlots(Scanner r){
        while (checkContinuance(r)){
            System.out.println("Emblem: ");
            String emblem = r.nextLine();
            System.out.println("Slot: ");
            String slot = r.nextLine();

            craftSlots.add(new CraftSlot(emblem,slot));
        }
    }
    public boolean checkContinuance(Scanner r){
        System.out.println("More slots?");
        String answer = r.nextLine();
        if(answer.equalsIgnoreCase("y")){
            return true;
        }
        return false;
    }
    private double checkAverage(){

        int totalPotential = 0;
        int count = 0;

        for (CraftSlot cs : craftSlots){
            count++;
            totalPotential += cs.getItem().getPotential();
        }
        return totalPotential/count;
    }
    public Item findItem(int targetPotential, CraftSlot req) {
        //System.out.println(req);
        for(Item i : itemList.getItemList()){
            //System.out.println(i);
            boolean emblem =  (i.getEmblem().equalsIgnoreCase(req.getEmblemRequirement())
                    || req.getEmblemRequirement().equalsIgnoreCase("any"));
            boolean slot = (i.getSlot().equalsIgnoreCase(req.getSlotRequirement())
                    || req.getSlotRequirement().equalsIgnoreCase("any"));

            //System.out.println(emblem + ", " + slot);
            if(emblem && slot){
                //System.out.println(i);
                return i;
            }
        }
        return null;
    }
    public int calculateNeededPotential(double currentTotal, double neededAverage, int amount){

        int potential = 1;

        while(true){
            if(((currentTotal+potential)/amount) == neededAverage){
                System.out.println(potential);
                break;
            }
            potential++;
        }
        return -1;
    }
    private int totalPotential(){
        int totalPotential = 0;
        for (CraftSlot cs : craftSlots){
            if(cs!=null){
                totalPotential+=cs.getItem().getPotential();
            }
        }
        return totalPotential;
    }
    public int findHighestPotential(){

        int highestPotential = 0;
        Item item = null;

        int index = 0;

        for (Item i : itemList.getItemList()){
            if(i.getPotential() > highestPotential){
                highestPotential = i.getPotential();
                item = i;
            }
            index++;
        }
        return index;
    }
    public Item findLowestPotential(){

        int lowestPotential = Short.MAX_VALUE;
        Item item = null;

        int index = 0;

        for (CraftSlot i : craftSlots){
            if(i.getItem().getPotential() < lowestPotential){
                lowestPotential = i.getItem().getPotential();
                item = i.getItem();
            }
            index ++;
        }
        return item;
    }
    public int findIndex(Item i){

        int index = 0;

        for(CraftSlot cs : craftSlots){
            if(cs.getItem().getName().equalsIgnoreCase(i.getName())){
                return index;
            }
            index++;
        }
        return index;
    }
    public void solve(){

        int i = 0;

        for(CraftSlot cs : craftSlots){
            //System.out.println(findItem(1,cs));
            craftSlots.get(i).setItem(findItem(1,cs));
            i++;
        }

        while(checkAverage()<minPotential || checkAverage()>maxPotential){
            if(checkAverage()<minPotential){
                int lowestPot = findIndex(findLowestPotential());
                craftSlots.get(lowestPot).setItem(null);
                craftSlots.get(lowestPot).setItem(findItem(calculateNeededPotential(checkAverage(),minPotential,craftSlots.size()),craftSlots.get(lowestPot)));
                System.out.println(craftSlots.get(lowestPot).getItem());
            }
        }
    }
}
