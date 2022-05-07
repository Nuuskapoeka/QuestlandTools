package org.nuuskapoeka.domain;

import org.nuuskapoeka.tools.Writer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Hero {

    private String heroName;    //index = 0
    private int heroPower;      //index = 5
    private int bossesHit;    //index = 26
    private int guildTrophies;  //index = 12
    private int ticketsUsed;    //index = 24

    private int health;
    private int attack;
    private int shield;
    private int magic;
    private double multi;

    private int amountOfEvents;

    private List<String> battleEvents;

    public Hero(String heroName,
                int heroPower,
                int bossesHit,
                int guildTrophies,
                int ticketsUsed){
        this.heroName = heroName;
        this.heroPower = heroPower;
        this.bossesHit = bossesHit;
        this.guildTrophies = guildTrophies;
        this.ticketsUsed = ticketsUsed;

        this.battleEvents = new ArrayList<>();
        this.battleEvents.add(this.heroName);
        this.battleEvents.add("WEEK,HERO LEVEL,PROGRESS,FAME LEVEL,PROGRESS,HERO POWER,PROGRESS,BOSS CHALLENGE SCORE,PROGRESS,DAYS IN GUILD,PROGRESS,GUILD TROPHIES COLLECTED PREVIOUS,GUILD TROPHIES COLLECTED CURRENT,GUILD EXPERIENCE PREVIOUS,GUILD EXPERIENCE CURRENT,DONATES COUNT PREVIOUS,DONATES COUNT CURRENT,TRIBUTES COUNT PREVIOUS,TRIBUTES COUNT CURRENT,NUMBER OF GIFTS SENT PREVIOUS,NUMBER OF GIFTS SENT CURRENT,FINISHED VOYAGES PREVIOUS,FINISHED VOYAGES CURRENT,GUILD BOSS TICKETS USED PREVIOUS,GUILD BOSS TICKETS USED CURRENT,SKULL TOKENS COLLECTED PREVIOUS,SKULL TOKENS COLLECTED CURRENT");
    }
    public Hero(String heroName, int health, int attack, int shield, int magic, double multi){
        this.heroName = heroName;
        this.health = health;
        this.attack = attack;
        this.shield = shield;
        this.magic = magic;
        this.multi = multi;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public int getBossesHit() {
        return bossesHit;
    }

    public void setBossesHit(int bossesHit) {
        this.bossesHit = bossesHit;
    }

    public int getGuildTrophies() {
        return guildTrophies;
    }

    public void setGuildTrophies(int guildTrophies) {
        this.guildTrophies = guildTrophies;
    }

    public int getTicketsUsed() {
        return ticketsUsed;
    }

    public void setTicketsUsed(int ticketsUsed) {
        this.ticketsUsed = ticketsUsed;
    }

    public int getAmountOfEvents(){

        int events = 0;

        int i = 1;

        for(String s : this.battleEvents){
            if(i<3){
                i++;
                continue;
            }
            events++;
        }
        return events;
    }

    public String getSave(){
        return this.heroName + "," + this.heroPower;
    }

    public void addEvent(String s){
        this.battleEvents.add(s);
    }

    public void save(){
        File f = new File("horsemen\\players2022\\" + this.heroName.toLowerCase() + ".csv");
        try {
            Writer w  = new Writer(f);
            w.write(this.battleEvents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getGuildTrophyHistory(){

        String history = this.heroName + ":" +
                        "\n  " + "Week: Trophies";

        String save = this.heroName;

        int i = 1;

        int eventsAmount = 0;
        int totalTrophies = 0;

        for(String s : this.battleEvents){
            if(i<3){
                i++;
                continue;
            }
            String[] parts = s.split(",");
            history += "\n  " + parts[0] + ":   " + parts[12];
            totalTrophies += Integer.parseInt(parts[12]);
            eventsAmount++;
            save += "," + parts[12];

        }

        //System.out.println(save);
        history += "\n  Avg:  " + totalTrophies/eventsAmount;

        List<String> list = new ArrayList<>();
        list.add(history);
        list.add(save);

        return list;
    }

    public String getTicketUseHistory(){

        String history = this.heroName + ":" +
                "\n  " + "Week: Tickets Used";

        int i = 1;

        int eventsAmount = 0;
        int totalTixUsed = 0;

        for(String s : this.battleEvents){
            if(i<3){
                i++;
                continue;
            }
            String[] parts = s.split(",");
            history += "\n  " + parts[0] + ":   " + parts[25];
            totalTixUsed += Integer.parseInt(parts[25]);
            eventsAmount++;
        }
        history += "\n  Avg:  " + totalTixUsed/eventsAmount;
        return history;
    }

    public String getHeroPowerHistory(){

        String history = this.heroName + ":" +
                "\n  " + "Week: Hero Power";

        int i = 1;

        for(String s : this.battleEvents){
            if(i<3){
                i++;
                continue;
            }
            String[] parts = s.split(",");
            history += "\n  " + parts[0] + ":   " + parts[5];
        }
        return history;
    }

    public String getGuildExpHistory(){

        String history = this.heroName + ":" +
                "\n  " + "Week: Guild Experience";

        int i = 1;

        for(String s : this.battleEvents){
            if(i<3){
                i++;
                continue;
            }
            String[] parts = s.split(",");
            history += "\n  " + parts[0] + ":   " + parts[14];
        }
        return history;
    }

    public String getGuildDonatesHistory(){

        String history = this.heroName + ":" +
                "\n  " + "Week: Donates";

        int i = 1;

        for(String s : this.battleEvents){
            if(i<3){
                i++;
                continue;
            }
            String[] parts = s.split(",");
            history += "\n  " + parts[0] + ":   " + parts[16];
        }
        return history;
    }

    public String getGuildTributesHistory(){

        String history = this.heroName + ":" +
                "\n  " + "Week: Tributes";

        int i = 1;

        for(String s : this.battleEvents){
            if(i<3){
                i++;
                continue;
            }
            String[] parts = s.split(",");
            history += "\n  " + parts[0] + ":   " + parts[18];
        }
        return history;
    }

    public String getRelationOfHpAndTrophies(){
        String relation = this.heroName + ":" +
                "\n  " + "Week: Trophies and Hero Power Relation";

        int i = 1;

        for(String s : this.battleEvents){
            if(i<3){
                i++;
                continue;
            }
            String[] parts = s.split(",");
            relation += "\n  " + parts[0] + ":   " + ((Integer.parseInt(parts[12])/getTickets(Integer.parseInt(parts[0])))/Double.parseDouble(parts[5])*10000);
        }
        return relation;
    }

    public int getAverageTicketsUsed(){

        if(this.heroName.equals("DUMMY")){
            return 1;
        }

        int ticketsUsed = 0;
        int eventsParticipated = 0;

        int i = 1;

        for(String s : this.battleEvents){
            if(i<3){
                i++;
                continue;
            }
            String[] parts = s.split(",");
            ticketsUsed += Integer.valueOf(parts[26]);
            eventsParticipated++;
        }
        //System.out.println(heroName + " avg tix: " + ticketsUsed/eventsParticipated);
        return ticketsUsed/eventsParticipated;
    }

    public int getAverageTrophies(){

        if(this.heroName.equals("DUMMY")){
            return Integer.MAX_VALUE;
        }

        int guildTrophies = 0;
        int eventsParticipated = 0;

        int i = 1;

        for(String s : this.battleEvents) {
            if (i < 3) {
                i++;
                continue;
            }
            String[] parts = s.split(",");
            guildTrophies += Integer.valueOf(parts[12]);
            eventsParticipated++;
        }
        this.amountOfEvents = eventsParticipated;
        return guildTrophies/eventsParticipated;
    }

    public int getHitterValue(){

        if(getAverageTicketsUsed()<=-1||getAverageTrophies()<=-1){
            return -1;
        }

        return (getAverageTrophies()*getAverageTicketsUsed())/120;
    }

    public int getHitterValue(int weekNmbr){

        if(getTrophies(weekNmbr)==-1||getTickets(weekNmbr)==-1){
            return -1;
        }

        return (getTrophies(weekNmbr)*getTickets(weekNmbr))/120;
    }


    //values by week


    public int getHeroPower(int weekNmbr){

        String event = "";

        int i = 1;

        for(String s : this.battleEvents){
            if (i < 3) {
                i++;
                continue;
            }
            String[] parts = s.split(",");
            if(Integer.parseInt(parts[0])==weekNmbr){
                event = s;
                //System.out.println(event);
                break;
            }
        }
        String[] parts = event.split(",");
        if(parts.length < 2){
            return -1;
        }
        return Integer.valueOf(parts[5]);
    }

    public int getTrophies(int weekNmbr){

        if(this.guildTrophies==Integer.MAX_VALUE){
            return Integer.MAX_VALUE;
        }else if(guildTrophies==Integer.MIN_VALUE){
            return Integer.MIN_VALUE;
        }


        String event = "";

        int i = 1;

        for(String s : this.battleEvents){
            if (i < 3) {
                i++;
                continue;
            }
            String[] parts = s.split(",");
            if(Integer.parseInt(parts[0])==weekNmbr){
                event = s;
                break;
            }
        }
        String[] parts = event.split(",");
        if(parts.length < 2){
            return -1;
        }
        return Integer.valueOf(parts[12]);
    }

    public int guildExp(int weekNmbr){
        String event = "";

        int i = 1;

        for(String s : this.battleEvents){
            if (i < 3) {
                i++;
                continue;
            }
            String[] parts = s.split(",");
            if(Integer.parseInt(parts[0])==weekNmbr){
                event = s;
                break;
            }
        }
        String[] parts = event.split(",");
        if(parts.length < 2){
            return -1;
        }
        return Integer.valueOf(parts[14]);
    }

    public int getTickets(int weekNmbr){

        String event = "";

        int i = 1;

        for(String s : this.battleEvents){
            if (i < 3) {
                i++;
                continue;
            }
            String[] parts = s.split(",");
            if(Integer.parseInt(parts[0])==weekNmbr){
                event = s;
                //System.out.println(event);
                break;
            }
        }
        String[] parts = event.split(",");
        if(parts.length < 2){
            return -1;
        }
        return Integer.valueOf(parts[24]);
    }

    public int getTokens(int weekNmbr){

        String event = "";

        int i = 1;

        for(String s : this.battleEvents){
            if (i < 3) {
                i++;
                continue;
            }
            String[] parts = s.split(",");
            if(Integer.parseInt(parts[0])==weekNmbr){
                event = s;
                //System.out.println(event);
                break;
            }
        }
        String[] parts = event.split(",");
        if(parts.length < 2){
            return -1;
        }
        return Integer.valueOf(parts[26]);
    }


    //calculations


    public int getTrophiesPerHit(int weekNmbr){

        String event = "";

        int i = 1;

        for(String s : this.battleEvents){
            if (i < 3) {
                i++;
                continue;
            }
            String[] parts = s.split(",");
            if(Integer.parseInt(parts[0])==weekNmbr){
                event = s;
                //System.out.println(event);
                break;
            }
        }
        String[] parts = event.split(",");
        if(parts.length < 2){
            return -1;
        }
        int hits = Integer.parseInt(parts[24])/2;
        int trophies = Integer.parseInt(parts[12]);

        if(hits <= 0){
            return 0;
        }

        return trophies/hits;
    }

    public int getTrophyDiff(int weekNmbr){

        String previousEvent = "";
        String event = "";

        int i = 1;

        for(String s : this.battleEvents){
            if (i < 3) {
                i++;
                continue;
            }
            String[] parts = s.split(",");
            if(Integer.parseInt(parts[0])==weekNmbr){
                event = s;
                //System.out.println(event);
                break;
            }
            if(Integer.parseInt(parts[0])==weekNmbr-2){
                previousEvent = s;

            }
        }
        String[] parts = event.split(",");
        String[] parts2 = previousEvent.split(",");
        if(parts.length < 2){
            return 0;
        }
        if(parts2.length < 2){
            return Integer.parseInt(parts[12]);
        }
        return Integer.parseInt(parts[12])-Integer.parseInt(parts2[12]);
    }

    public int chillingDamage(){

        double damage = this.magic*1.4*3*1.15;

        //System.out.println(h.getHeroName() + " : chilling : " + (int)damage);

        return (int)damage;
    }
    public int fistDamage(){

        double damage = 0.8*((this.magic*3+this.attack*2)/2);

        //System.out.println(h.getHeroName() + " : fist : " + (int)damage);

        return (int)damage;
    }
    //damage divided by 1k
    public int blueDamage(){

        long damage = ((long)(chillingDamage() + (long)fistDamage())*(int)this.multi)*19;
        //System.out.println(h.getMulti());
        //System.out.println(h.getHeroName() + " : " + damage);
        return (int)(damage/1000);
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

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public double getMulti() {
        return multi;
    }

    public void setMulti(int multi) {
        this.multi = multi;
    }


    @Override
    public String toString(){
        return this.heroName + ", " + this.heroPower
                + "\n  Bosses Hit:   " + this.bossesHit
                + "\n  Guild Trophies: " + this.guildTrophies
                + "\n  Tickets Used:   " + this.ticketsUsed;
    }
}
