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
    private BuildSlot[] weapons;

    private int potential;
    private int health;
    private int attack;
    private int defence;
    private int magic;

    private int maxHealth;
    private int maxAttack;
    private int maxDefence;
    private int maxMagic;

    private int avgOrbPot;
    private int avgOrbBase;
    private int avgEnhance;
    private double guildBonus;


    private Items itemList;

    public Build(Items itemList){
        equipped = new BuildSlot[7];
        healthSlots = new BuildSlot[5];
        attackSlots = new BuildSlot[5];
        defenceSlots = new BuildSlot[5];
        magicSlots = new BuildSlot[5];
        weapons = new BuildSlot[2];

        fullBuild = new ArrayList<>();

        this.potential = 0;
        this.health = 0;
        this.attack = 0;
        this.defence = 0;
        this.magic = 0;

        this.maxHealth = 0;
        this.maxAttack = 0;
        this.maxDefence = 0;
        this.maxMagic = 0;

        this.itemList = itemList;
    }

    public int getOrbPowerEst(){
        double avgPower = (avgOrbBase+(avgOrbBase*0.05*avgEnhance))+(avgOrbPot+(avgOrbPot*0.05*avgEnhance))*179;
        return (int)avgPower*4*9;
    }
    public int getOrbPowerEst(int avgOrbBase, int avgOrbPot, int avgEnhance){
        double avgPower = (avgOrbBase+(avgOrbBase*0.05*avgEnhance))+(avgOrbPot+(avgOrbPot*0.05*avgEnhance))*179;
        return (int)avgPower*4*9;
    }

    public void addItem(Item i){
        //System.out.println(i);
        if(fullBuild.size()<=29){
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
                if(slot.getItem()!=null&&slot.getItem().getType()!=null) {
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
        addStats(fullBuild.get(27),0,27);
        addStats(fullBuild.get(28),0,28);
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
    public String getMaxStats(){
        int orbPower = getOrbPowerEst();
        int total = (int) ((maxAttack+maxMagic+maxDefence+maxHealth+orbPower)*guildBonus);
        return "health = " + (maxHealth+orbPower/4)*guildBonus+ "\nattack = " + (maxAttack+orbPower/4)*guildBonus+ "\ndefence = " + (maxDefence+orbPower/4)*guildBonus + "\nmagic = " + (maxMagic+orbPower/4)*guildBonus + "\ntotal = " + total;
    }
    public String getMaxHealth() {
        int orbPower = getOrbPowerEst();
        return "health = " +Math.round((maxHealth+orbPower/4)*guildBonus);
    }
    public String getMaxAttack(){
        int orbPower = getOrbPowerEst();
        return "attack = " + Math.round((maxAttack+orbPower/4)*guildBonus);
    }

    public String getMaxDefence() {
        int orbPower = getOrbPowerEst();
        return "defence = " +Math.round((maxDefence+orbPower/4)*guildBonus);
    }
    public String getMaxMagic(){
        int orbPower = getOrbPowerEst();
        return "magic = " +Math.round((maxMagic+orbPower/4)*guildBonus);
    }

    public String getMaxTotal(){
        int orbPower = getOrbPowerEst();
        int total = (int) ((maxAttack+maxMagic+maxDefence+maxHealth+orbPower)*guildBonus);
        return "total = " + total;
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
                //System.out.println(slot);
                if(index < 7){
                    addFullStats(slot);
                }else if(index < 12){
                    this.health += slot.getItem().getHealth()*1.3;
                    this.maxHealth += Math.round(slot.getItem().getHealth()*1.3*2.75*Math.pow(1.2,5));
                }else if(index < 17){
                    this.attack += slot.getItem().getAttack()*1.3;
                    this.maxAttack += Math.round(slot.getItem().getAttack()*1.3*2.75*Math.pow(1.2,5));
                }else if(index < 22){
                    this.defence += slot.getItem().getDefence()*1.3;
                    this.maxDefence += Math.round(slot.getItem().getDefence()*1.3*2.75*Math.pow(1.2,5));
                }else if(index < 27){
                    this.magic += slot.getItem().getMagic()*1.3;
                    this.maxMagic += Math.round(slot.getItem().getMagic()*1.3*2.75*Math.pow(1.2,5));
                }else if(index < 29){
                    addFullStats(slot);
                }
                //System.out.println(slot);
            }else{
                if(index < 7){
                    addFullStats(slot);
                }else if(index < 12){
                    this.health += slot.getItem().getHealth();
                    this.maxHealth += Math.round(slot.getItem().getHealth()*2.75*Math.pow(1.2,5));
                }else if(index < 17){
                    this.attack += slot.getItem().getAttack();
                    this.maxAttack += Math.round(slot.getItem().getAttack()*2.75*Math.pow(1.2,5));
                }else if(index < 22){
                    this.defence += slot.getItem().getDefence();
                    this.maxDefence += Math.round(slot.getItem().getDefence()*2.75*Math.pow(1.2,5));
                }else if(index < 27){
                    this.magic += slot.getItem().getMagic();
                    this.maxMagic += Math.round(slot.getItem().getMagic()*2.75*Math.pow(1.2,5));
                }else if(index < 29){
                    addFullStats(slot);
                }
            }
        }
    }

    private void addFullStats(BuildSlot slot){
        if(slot.getItem()!=null) {
            int reforge = (int) Math.round(slot.getItem().getPotential()*Math.pow(1.2,5)*100);
            //System.out.println(reforge);
            if(slot.getItem().getType()==null){
                this.maxHealth += Math.round((slot.getItem().getHealth() + slot.getItem().getHealthInc())*Math.pow(1.2,5)+reforge/2);
                this.maxAttack += Math.round((slot.getItem().getAttack() + slot.getItem().getAttackInc())*Math.pow(1.2,5)+reforge/2);
                this.maxDefence += Math.round((slot.getItem().getDefence() + slot.getItem().getDefenceInc())*Math.pow(1.2,5));
                this.maxMagic += Math.round((slot.getItem().getMagic() + slot.getItem().getMagicInc())*Math.pow(1.2,5));
                return;
            } else if(slot.getItem().getType().equalsIgnoreCase("health") || slot.getItem().getTypeID().contains("Col")){
                addFullStatsColItem(slot);
            }else if(slot.getItem().getType().equalsIgnoreCase("attack")){
                this.health += slot.getItem().getHealth()*1.15;
                this.attack += slot.getItem().getAttack()*1.3;
                this.defence += slot.getItem().getDefence();
                this.magic += slot.getItem().getMagic();
                this.maxHealth += Math.round((slot.getItem().getHealth() + slot.getItem().getHealthInc()*199)*Math.pow(1.2,5)+reforge/2)*1.15;
                this.maxAttack += Math.round((slot.getItem().getAttack() + slot.getItem().getAttackInc()*199)*Math.pow(1.2,5)+reforge/2)*1.3;
                this.maxDefence += Math.round((slot.getItem().getDefence() + slot.getItem().getDefenceInc()*199)*Math.pow(1.2,5));
                this.maxMagic += Math.round((slot.getItem().getMagic() + slot.getItem().getMagicInc()*199)*Math.pow(1.2,5));
            }else if(slot.getItem().getType().equalsIgnoreCase("defence")){
                this.health += slot.getItem().getHealth();
                this.attack += slot.getItem().getAttack()*1.15;
                this.defence += slot.getItem().getDefence()*1.3;
                this.magic += slot.getItem().getMagic();
                this.maxHealth += Math.round((slot.getItem().getHealth() + slot.getItem().getHealthInc()*199)*Math.pow(1.2,5));
                this.maxAttack += Math.round((slot.getItem().getAttack() + slot.getItem().getAttackInc()*199)*Math.pow(1.2,5)+reforge/2)*1.15;
                this.maxDefence += Math.round((slot.getItem().getDefence() + slot.getItem().getDefenceInc()*199)*Math.pow(1.2,5)+reforge/2)*1.3;
                this.maxMagic += Math.round((slot.getItem().getMagic() + slot.getItem().getMagicInc()*199)*Math.pow(1.2,5));
            }else if(slot.getItem().getType().equalsIgnoreCase("magic")){
                this.health += slot.getItem().getHealth();
                this.attack += slot.getItem().getAttack();
                this.defence += slot.getItem().getDefence()*1.15;
                this.magic += slot.getItem().getMagic()*1.3;
                this.maxHealth += Math.round((slot.getItem().getHealth() + slot.getItem().getHealthInc()*199)*Math.pow(1.2,5));
                this.maxAttack += Math.round((slot.getItem().getAttack() + slot.getItem().getAttackInc()*199)*Math.pow(1.2,5));
                this.maxDefence += Math.round((slot.getItem().getDefence() + slot.getItem().getDefenceInc()*199)*Math.pow(1.2,5)+reforge/2)*1.15;
                this.maxMagic += Math.round((slot.getItem().getMagic() + slot.getItem().getMagicInc()*199)*Math.pow(1.2,5)+reforge/2)*1.3;
            }
        }
    }

    private void addFullStatsColItem(BuildSlot slot){
        int reforge = (int) Math.round(slot.getItem().getPotential()*Math.pow(1.2,5)*100);
        if(slot.getItem().getType().equalsIgnoreCase("health")){
            this.health += slot.getItem().getHealth()*1.3;
            this.attack += slot.getItem().getAttack();
            this.defence += slot.getItem().getDefence();
            this.magic += slot.getItem().getMagic();
            this.maxHealth += Math.round((slot.getItem().getHealth() + slot.getItem().getHealthInc()*199)*Math.pow(1.2,5)+reforge)*1.3;
            this.maxAttack += Math.round((slot.getItem().getAttack() + slot.getItem().getAttackInc()*199)*Math.pow(1.2,5));
            this.maxDefence += Math.round((slot.getItem().getDefence() + slot.getItem().getDefenceInc()*199)*Math.pow(1.2,5));
            this.maxMagic += Math.round((slot.getItem().getMagic() + slot.getItem().getMagicInc()*199)*Math.pow(1.2,5));
        }else if(slot.getItem().getTypeID().equalsIgnoreCase("acol")){
            this.health += slot.getItem().getHealth()*1.3;
            this.attack += slot.getItem().getAttack();
            this.defence += slot.getItem().getDefence();
            this.magic += slot.getItem().getMagic();
            this.maxHealth += Math.round((slot.getItem().getHealth() + slot.getItem().getHealthInc()*199)*Math.pow(1.2,5));
            this.maxAttack += Math.round((slot.getItem().getAttack() + slot.getItem().getAttackInc()*199)*Math.pow(1.2,5)+reforge)*1.3;
            this.maxDefence += Math.round((slot.getItem().getDefence() + slot.getItem().getDefenceInc()*199)*Math.pow(1.2,5));
            this.maxMagic += Math.round((slot.getItem().getMagic() + slot.getItem().getMagicInc()*199)*Math.pow(1.2,5));
        }else if(slot.getItem().getTypeID().equalsIgnoreCase("dcol")){
            this.health += slot.getItem().getHealth()*1.3;
            this.attack += slot.getItem().getAttack();
            this.defence += slot.getItem().getDefence();
            this.magic += slot.getItem().getMagic();
            this.maxHealth += Math.round((slot.getItem().getHealth() + slot.getItem().getHealthInc()*199)*Math.pow(1.2,5));
            this.maxAttack += Math.round((slot.getItem().getAttack() + slot.getItem().getAttackInc()*199)*Math.pow(1.2,5));
            this.maxDefence += Math.round((slot.getItem().getDefence() + slot.getItem().getDefenceInc()*199)*Math.pow(1.2,5)+reforge)*1.3;
            this.maxMagic += Math.round((slot.getItem().getMagic() + slot.getItem().getMagicInc()*199)*Math.pow(1.2,5));
        }else if(slot.getItem().getTypeID().equalsIgnoreCase("mcol")){
            this.health += slot.getItem().getHealth()*1.3;
            this.attack += slot.getItem().getAttack();
            this.defence += slot.getItem().getDefence();
            this.magic += slot.getItem().getMagic();
            this.maxHealth += Math.round((slot.getItem().getHealth() + slot.getItem().getHealthInc()*199)*Math.pow(1.2,5));
            this.maxAttack += Math.round((slot.getItem().getAttack() + slot.getItem().getAttackInc()*199)*Math.pow(1.2,5));
            this.maxDefence += Math.round((slot.getItem().getDefence() + slot.getItem().getDefenceInc()*199)*Math.pow(1.2,5));
            this.maxMagic += Math.round((slot.getItem().getMagic() + slot.getItem().getMagicInc()*199)*Math.pow(1.2,5)+reforge)*1.3;
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
        int i = 0;
        for(BuildSlot bs : fullBuild){
            if(i>=7){
                break;
            }
            equipped[i] = bs;
            i++;
        }
        return equipped;
    }

    public void setEquipped(BuildSlot[] equipped) {
        this.equipped = equipped;
    }

    public BuildSlot[] getHealthSlots() {
        int i = 7;
        int slotI = 0;
        for(BuildSlot bs : fullBuild){
            if(i>=13){
                break;
            }else if(i<7){
                continue;
            }else if(slotI==5){
                break;
            }
            healthSlots[slotI] = fullBuild.get(i);
            slotI ++;
            i++;
        }
        return healthSlots;
    }

    public void setHealthSlots(BuildSlot[] healthSlots) {
        this.healthSlots = healthSlots;
    }

    public BuildSlot[] getAttackSlots() {
        int i = 12;
        int slotI = 0;
        for(BuildSlot bs : fullBuild){
            if(i>=18){
                break;
            }else if(i<12){
                continue;
            }else if(slotI==5){
                break;
            }
            attackSlots[slotI] = fullBuild.get(i);
            slotI++;
            i++;
        }
        return attackSlots;
    }

    public void setAttackSlots(BuildSlot[] attackSlots) {
        this.attackSlots = attackSlots;
    }

    public BuildSlot[] getDefenceSlots() {
        int i = 17;
        int slotI = 0;
        for(BuildSlot bs : fullBuild){
            if(i>=23){
                break;
            }else if(i<17){
                continue;
            }else if(slotI==5){
                break;
            }
            defenceSlots[slotI] = fullBuild.get(i);
            slotI++;
            i++;
        }
        return defenceSlots;
    }

    public void setDefenceSlots(BuildSlot[] defenceSlots) {
        this.defenceSlots = defenceSlots;
    }

    public BuildSlot[] getMagicSlots() {
        int i = 22;
        int slotI = 0;
        for(BuildSlot bs : fullBuild){
            if(i>=28){
                break;
            }else if(i<22){
                continue;
            }else if(slotI==5){
                break;
            }
            magicSlots[slotI] = fullBuild.get(i);
            slotI++;
            i++;
        }
        return magicSlots;
    }

    public void setMagicSlots(BuildSlot[] magicSlots) {
        this.magicSlots = magicSlots;
    }


    public int getAvgOrbPot() {
        return avgOrbPot;
    }

    public void setAvgOrbPot(int avgOrbPot) {
        this.avgOrbPot = avgOrbPot;
    }

    public int getAvgOrbBase() {
        return avgOrbBase;
    }

    public void setAvgOrbBase(int avgOrbBase) {
        this.avgOrbBase = avgOrbBase;
    }

    public int getAvgEnhance() {
        return avgEnhance;
    }

    public void setAvgEnhance(int avgEnhance) {
        this.avgEnhance = avgEnhance;
    }

    public double getGuildBonus() {
        return guildBonus;
    }

    public void setGuildBonus(double guildBonus) {
        this.guildBonus = guildBonus;
    }

    public List<BuildSlot> getFullBuild(){
        return fullBuild;
    }
    public void setFullBuild(List<BuildSlot> build){
        this.fullBuild = build;
    }
}
