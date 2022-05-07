package org.nuuskapoeka.logic;

import java.util.Scanner;

public class CraftSolver {

    private Items itemList;

    private int maxPotential;
    private int minPotential;

    private int maxEmblems;



    public CraftSolver(Items itemList){
        this.itemList = itemList;
    }

    public void start(){
        readRequirements();
    }
    public void readRequirements(){
        Scanner r = new Scanner(System.in);

        System.out.println("Min average potential");
        minPotential = Integer.parseInt(r.nextLine());
        System.out.println("Max average potential");
        minPotential = Integer.parseInt(r.nextLine());
        System.out.println("Max amount of emblems");
        maxEmblems = Integer.parseInt(r.nextLine());

        System.out.println("potential range: " + minPotential + " - " + maxPotential);
    }
}
