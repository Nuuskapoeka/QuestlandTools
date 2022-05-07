package org.nuuskapoeka.logic;

import org.nuuskapoeka.domain.CraftSlot;

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

}
