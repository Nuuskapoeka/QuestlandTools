package org.nuuskapoeka.domain;

public class CraftSlot {

    private String emblemRequirement;
    private String slotRequirement;

    private Item item;

    public CraftSlot(String embReq, String slotReq){
        this.emblemRequirement = embReq;
        this.slotRequirement = slotReq;
    }

    public String getEmblemRequirement() {
        return emblemRequirement;
    }

    public void setEmblemRequirement(String emblemRequirement) {
        this.emblemRequirement = emblemRequirement;
    }

    public String getSlotRequirement() {
        return slotRequirement;
    }

    public void setSlotRequirement(String slotRequirement) {
        this.slotRequirement = slotRequirement;
    }

    public Item getItem() {
        return item;
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

    @Override
    public String toString() {
        return "CraftSlot{" +
                "emblemRequirement='" + emblemRequirement + '\'' +
                ", slotRequirement='" + slotRequirement + '\'' +
                ", item=" + item +
                '}';
    }
}
