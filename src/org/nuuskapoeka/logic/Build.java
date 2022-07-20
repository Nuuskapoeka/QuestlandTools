package org.nuuskapoeka.logic;

import org.nuuskapoeka.domain.BuildSlot;
import org.nuuskapoeka.domain.Item;

import java.util.ArrayList;
import java.util.List;

public class Build {

    private List<BuildSlot> fullBuild;

    private BuildSlot[] equipped;
    private BuildSlot[] healthSlots;
    private BuildSlot[] attackSlots;
    private BuildSlot[] defenceSlots;
    private BuildSlot[] magicSlots;

    private int potential;
    private int health;
    private int attack;
    private int defence;
    private int magic;

    private Items itemList;

    public Build(Items itemList){
        equipped = new BuildSlot[7];
        healthSlots = new BuildSlot[5];
        attackSlots = new BuildSlot[5];
        defenceSlots = new BuildSlot[5];
        magicSlots = new BuildSlot[5];

        fullBuild = new ArrayList<>();

        this.potential = 0;
        this.health = 0;
        this.attack = 0;
        this.defence = 0;
        this.magic = 0;

        this.itemList = itemList;
    }

    public void addItem(Item i){
        //System.out.println(i);
        if(fullBuild.size()<=27){
            BuildSlot bs = new BuildSlot(i);
            //System.out.println(bs);
            fullBuild.add(bs);
            //System.out.println(bs.getItem().getName());
        }
    }

    public void checkOverview(){
        for(BuildSlot slot : fullBuild){
            //System.out.println(slot.getItem());
        }
    }

    public void checkLinks(){
        List<BuildSlot> oneLinking = new ArrayList<>();
        List<BuildSlot> noLinking = new ArrayList<>();

        int index = 0;

        for(BuildSlot slot : fullBuild){
            //System.out.println(slot);
            if(slot.getItem()!=null) {
            	int linking = 0;
                if(slot.getItem()!=null) {
                	for(String link : slot.getItem().getLinks()){
                        //System.out.println("\nLink "+link +"\n");

                        //System.out.println("Link " + linkNum + "\n" + itemList.getItem(link));
                        for(BuildSlot item : fullBuild){
                            //System.out.println("\nItem " + item.getItem().getName() + "\n");
                            if(item.getItem()!=null) {
                            	if(link.equalsIgnoreCase(item.getItem().getName())){
                                    linking++;
                                    slot.addActiveLink(link);
                                    //System.out.println(linking);
                                }
                            }
                        }
                    }
                }
                addStats(slot,linking,index);
                linking = 0;
                index++;
            }
        }

        //System.out.println("Full overview");

        //System.out.println("health = " + health + "\nattack = " + attack + "\ndefence = " + defence + "\nmagic = " + magic);
        //System.out.println("total = " + (health+attack+defence+magic));

        for(BuildSlot bs : fullBuild ){
            //System.out.println(bs + "\n");
            if(bs.getActiveLinks().size()==1){
                oneLinking.add(bs);
            }else if(bs.getActiveLinks().size()==0){
                noLinking.add(bs);
            }
        }

        //System.out.println("Has one linking item");

        for(BuildSlot bs : oneLinking){
            //System.out.println(bs + "\n");
        }

        //System.out.println("Has no linking items");

        for(BuildSlot bs : noLinking){
            //System.out.println(bs + "\n");
        }
    }

    public String getStats(){
        return "health = " + health + "\nattack = " + attack + "\ndefence = " + defence + "\nmagic = " + magic;
    }

    public String getHealth() {
        return "health = " + health;
    }

    public String getAttack() {
        return "attack = " + attack;
    }

    public String getDefence() {
        return "defence = " + defence;
    }

    public String getMagic() {
        return "magic = " + magic;
    }

    public String getTotal(){
        return "total = " + (health+attack+defence+magic);
    }

    public void addStats(BuildSlot slot, int linking, int index){
        if(slot.getItem() != null) {
        	if(linking >= 2){
                slot.setLinked(true);
                if(index < 7){
                    addFullStats(slot);
                }else if(index < 12){
                    this.health += slot.getItem().getHealth()*1.3;
                }else if(index < 17){
                    this.attack += slot.getItem().getAttack()*1.3;
                }else if(index < 22){
                    this.defence += slot.getItem().getDefence()*1.3;
                }else if(index < 27){
                    this.magic += slot.getItem().getMagic()*1.3;
                }
                //System.out.println(slot);
            }else{
                if(index < 7){
                    addFullStats(slot);
                }else if(index < 12){
                    this.health += slot.getItem().getHealth();
                }else if(index < 17){
                    this.attack += slot.getItem().getAttack();
                }else if(index < 22){
                    this.defence += slot.getItem().getDefence();
                }else if(index < 27){
                    this.magic += slot.getItem().getMagic();
                }
            }
        }
    }

    private void addFullStats(BuildSlot slot){
        if(slot.getItem()!=null) {
        	if(slot.getItem().getType().equalsIgnoreCase("health")){
                this.health += slot.getItem().getHealth()*1.3;
                this.attack += slot.getItem().getAttack();
                this.defence += slot.getItem().getDefence();
                this.magic += slot.getItem().getMagic();
            }else if(slot.getItem().getType().equalsIgnoreCase("attack")){
                this.health += slot.getItem().getHealth();
                this.attack += slot.getItem().getAttack()*1.3;
                this.defence += slot.getItem().getDefence();
                this.magic += slot.getItem().getMagic();
            }else if(slot.getItem().getType().equalsIgnoreCase("defence")){
                this.health += slot.getItem().getHealth();
                this.attack += slot.getItem().getAttack();
                this.defence += slot.getItem().getDefence()*1.3;
                this.magic += slot.getItem().getMagic();
            }else if(slot.getItem().getType().equalsIgnoreCase("magic")){
                this.health += slot.getItem().getHealth();
                this.attack += slot.getItem().getAttack();
                this.defence += slot.getItem().getDefence();
                this.magic += slot.getItem().getMagic()*1.3;
            }

        }

    }

    public BuildSlot getLink(String s){
        for(BuildSlot bs : fullBuild){
            if(bs.getItem().getIdentifier().equals(s)){
                return bs;
            }
        }
        return null;
    }

    private void addPartialStats(){

    }

    public BuildSlot[] getEquipped() {
        return equipped;
    }

    public void setEquipped(BuildSlot[] equipped) {
        this.equipped = equipped;
    }

    public BuildSlot[] getHealthSlots() {
        return healthSlots;
    }

    public void setHealthSlots(BuildSlot[] healthSlots) {
        this.healthSlots = healthSlots;
    }

    public BuildSlot[] getAttackSlots() {
        return attackSlots;
    }

    public void setAttackSlots(BuildSlot[] attackSlots) {
        this.attackSlots = attackSlots;
    }

    public BuildSlot[] getDefenceSlots() {
        return defenceSlots;
    }

    public void setDefenceSlots(BuildSlot[] defenceSlots) {
        this.defenceSlots = defenceSlots;
    }

    public BuildSlot[] getMagicSlots() {
        return magicSlots;
    }

    public void setMagicSlots(BuildSlot[] magicSlots) {
        this.magicSlots = magicSlots;
    }

    public List<BuildSlot> getFullBuild(){
        return fullBuild;
    }
    public void setFullBuild(List<BuildSlot> build){
        this.fullBuild = build;
    }
}
