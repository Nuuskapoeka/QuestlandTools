package org.nuuskapoeka.domain;

public class CraftSlot {

    private String emblemRequirement;
    private String slotRequirement;

    private Item item;

    public CraftSlot(String embReq, String slotReq){
        this.emblemRequirement = embReq;
        this.slotRequirement = slotReq;
    }

    public boolean setItem(Item  i){
        if(i.getEmblem().equalsIgnoreCase(emblemRequirement)
                || emblemRequirement.equalsIgnoreCase("any")
                && i.getSlot().equalsIgnoreCase(slotRequirement)
                || slotRequirement.equalsIgnoreCase("any")){
            this.item = i;
            return true;
        }
        System.out.println("Emblem or Slot doesnt match");
        return false;
    }
}
