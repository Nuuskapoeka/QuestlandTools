package org.nuuskapoeka.ui;

import org.nuuskapoeka.domain.Guild;
import org.nuuskapoeka.domain.Hero;
import org.nuuskapoeka.logic.*;
import org.nuuskapoeka.tools.StartUp;
import org.nuuskapoeka.tools.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class MainUI {

    private Scanner r;
    private Guild guild;
    private StartUp start;
    private Saver saver;

    private Grouper grouper;
    private Items itemList;
    private GUI gui;
    private CraftSolver craftSolver;

    public MainUI() throws FileNotFoundException {
        r = new Scanner(System.in);
        this.guild = new Guild("Fooking Horsemen");
        this.start = new StartUp(guild);

        this.saver = new Saver(guild);

        this.grouper = new Grouper("horsemen\\player_stats.csv");
        this.itemList = new Items();

        this.craftSolver = new CraftSolver(itemList);
    }
    public void startBuilder() throws FileNotFoundException {
    	Scanner r = new Scanner(System.in);
    	System.out.println("Item file location:");
    	String s = r.nextLine();
    	System.out.println("Build file location:");
    	String b = r.nextLine();
    	itemList.load(s);
        this.gui = new GUI(itemList.getItemNames(), itemList);
        //this.itemList.clearEmpty();
        gui.startGUI();
        gui.loadBuildIn(itemList.loadBuild(b).getFullBuild());
    }

    public void startAnalyzer() throws FileNotFoundException {
        while(true) {
            System.out.println("Command: ");
            System.out.print("> ");
            String command = r.nextLine();
            String[] parts = command.split(" ");
            String preFix = parts[0];
            if (command.equals("close")) {
                break;
            } else if (preFix.contains("copy")) {
                copy(command);
            } else if (preFix.contains("analyze")) {
                analyze(command);
            } else if (preFix.contains("compare")) {
                compare(command);
            } else if (preFix.contains("create")) {
                create(command);
            } else if (preFix.contains("delete")) {
                delete(command);
            } else if(preFix.contains("average")){
                average(command);
            } else if (preFix.contains("history")) {
                history(command);
            } else if (preFix.contains("top_hitters")) {
                getTopHitters(command);
            } else if(preFix.contains("low_hitters")){
                getLowHitters(command);
            } else if(preFix.contains("top_tickets")){
                getTopTicketsUsers(command);
            } else if(preFix.contains("low_tickets")){
                getLowTicketUsers(command);
            } else if(preFix.contains("save")){
                save(command);
            } else if(preFix.contains("top_risers")){
                topRisers(command);
            } else if(preFix.contains("blue_damage")){
                calculateBlueDamage();
            }else if(preFix.contains("make_groups")){
                grouper.makeGroups();
            }else if(preFix.contains("build")){
                startBuild();
            }
        }
    }

    public void start() throws IOException {
        //this.itemList.loadBuild().checkLinks();
    	Scanner r = new Scanner(System.in);
    	//System.out.println("Item file location:");
    	//String s = r.nextLine();
    	//itemList.load(s);
        //craftSolver.start();
        //this.itemList.clearEmpty();
        //gui.startGUI();
        startBuilder();
        //this.start.run();
        //grouper.findSoloers();
        for(Hero h : this.guild.getHeroes()){
            h.save();
        }
    }


    //save


    public void save(String command){
        String[] parts = command.split(" ");
        if(parts[1].equals("event")){
            saver.customSaveEvent(parts[4], parts[6], Integer.parseInt(parts[2]), command);

        }else if(parts[1].equals("guild")){
            System.out.println("!");
            saver.customSaveGuildTotals(parts[3],parts[5], command);
        }
    }

    //averages

    public void average(String command){
        String[] parts = command.split(" ");
        if(parts[1].equals("tickets")){
            System.out.println(guild.getAverageTicketUse(Integer.parseInt(parts[2])));
        } else if(parts[1].equals("trophies")){
            System.out.println(guild.getAverageTrophies(Integer.parseInt(parts[2])));
        }else{
            System.out.println("please enter valid parameter");
        }
    }


    //analyzing methods


    public void analyze(String command) {
        String[] parts = command.split(" ");
        Analyze analyze = new Analyze(new File(parts[1]));
        analyze.analyze();


    }

    public void compare(String command){
        String[] parts = command.split(" ");
        if(parts[1].equals("events")){
            try {
                Compare compare = new Compare(new File(parts[2]), new File(parts[4]));
                compare.compare();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else if(parts[1].equals("tickets")){
            compareTicketUse(command);
        }else if(parts[1].equals("trophies")){
            compareTrophies(command);
        }
    }

    public void compareTicketUse(String command){

        DecimalFormat df = new DecimalFormat("#.##");

        String[] parts = command.split(" ");

        double ticketsFirstWeek = guild.getAverageTicketUse(Integer.parseInt(parts[2]));
        double ticketsSecondWeek = guild.getAverageTicketUse(Integer.parseInt(parts[4]));
        double ticketsRelation = ticketsSecondWeek/ticketsFirstWeek;

        System.out.println("TICKET USE:");
        System.out.println("  " + parts[2] + ":  " + ticketsFirstWeek);
        System.out.println("  " + parts[4] + ":  " + ticketsSecondWeek);
        System.out.println("  REL: " + df.format(ticketsRelation));
    }

    public void compareTrophies(String command){

        DecimalFormat df = new DecimalFormat("#.##");

        String[] parts = command.split(" ");

        double trophiesFirstWeek = guild.getAverageTrophies(Integer.parseInt(parts[2]));
        double trophiesSecondWeek = guild.getAverageTrophies(Integer.parseInt(parts[4]));
        double trophiesRelation = trophiesSecondWeek/trophiesFirstWeek;

        System.out.println("TROPHIES:");
        System.out.println("  " + parts[2] + ":  " + trophiesFirstWeek);
        System.out.println("  " + parts[4] + ":  " + trophiesSecondWeek);
        System.out.println("  REL: " + df.format(trophiesRelation));
    }

    public void topRisers(String command){
        String[] parts = command.split(" ");

        int week = Integer.parseInt(parts[2]);
        System.out.println(week);

        List<Hero> topHitters = guild.getTopRisers(week);

        Collections.sort(topHitters, new Comparator<Hero>() {
            @Override
            public int compare(Hero h1, Hero h2) {
                return Double.compare(h2.getTrophyDiff(week), h1.getTrophyDiff(week));
            }
        });


        int i = 1;

        for (Hero h : topHitters){
            System.out.println(i + ". " + h.getHeroName() + ": " + h.getTrophyDiff(week));
            i++;
        }
    }

    //hitter methods

    public void getTopHitters(String command){
        String[] parts = command.split(" ");
        System.out.println(parts.length);
        if(parts.length >= 2){
            getTopHittersByWeek(command);
        }else{
            getOverallTopHitters();
        }
    }

    public void getOverallTopHitters(){
        List<Hero> topHitters = guild.getTopHitters();

        System.out.println("top hitters");

        Collections.sort(topHitters, new Comparator<Hero>() {
            @Override
            public int compare(Hero h1, Hero h2) {
                return Double.compare(h2.getHitterValue(), h1.getHitterValue());
            }
        });


        int i = 1;

        for (Hero h : topHitters){
            System.out.println(i + ". " + h.getHeroName() + ": " + h.getHitterValue());
            i++;
        }
    }

    public void getTopHittersByWeek(String command){

        String[] parts = command.split(" ");

        int week = Integer.parseInt(parts[1]);
        System.out.println(week);

        List<Hero> topHitters = guild.getTopHitters(week);

        Collections.sort(topHitters, new Comparator<Hero>() {
            @Override
            public int compare(Hero h1, Hero h2) {
                return Double.compare(h2.getHitterValue(week), h1.getHitterValue(week));
            }
        });

        int i = 1;

        for (Hero h : topHitters){
            System.out.println(i + ". " + h.getHeroName() + ": " + h.getHitterValue(week));
            i++;
        }
    }

    public void getLowHitters(String command){
        String[] parts = command.split(" ");
        System.out.println(parts.length);
        if(parts.length >= 2){
            getLowHittersByWeek(command);
        }else{
            getOverallLowHitters();
        }
    }

    public void getOverallLowHitters(){
        List<Hero> lowHitters = guild.getLowHitters();

        Collections.sort(lowHitters, new Comparator<Hero>() {
            @Override
            public int compare(Hero h1, Hero h2) {
                return Double.compare(h1.getHitterValue(), h2.getHitterValue());
            }
        });


        int i = 1;

        for (Hero h : lowHitters){
            System.out.println(i + ". " + h.getHeroName() + ": " + h.getHitterValue());
            i++;
        }
    }

    public void getLowHittersByWeek(String command){

        String[] parts = command.split(" ");

        int week = Integer.parseInt(parts[1]);
        System.out.println(week);

        List<Hero> lowHitters = guild.getLowHitters(week);

        Collections.sort(lowHitters, new Comparator<Hero>() {
            @Override
            public int compare(Hero h1, Hero h2) {
                return Double.compare(h1.getHitterValue(week), h2.getHitterValue(week));
            }
        });


        int i = 1;

        for (Hero h : lowHitters){
            System.out.println(i + ". " + h.getHeroName() + ": " + h.getHitterValue(week));
            i++;
        }
    }


    //ticket user methods

    public void getTopTicketsUsers(String command){
        String[] parts = command.split(" ");
        System.out.println(parts.length);
        if(parts.length >= 3){
            getTopTicketUsersByWeek(command);
        }else{
            getOverallTopTicketUsers();
        }
    }

    public void getOverallTopTicketUsers(){
        List<Hero> topTicketUsers = guild.getTopTicketUsers();

        Collections.sort(topTicketUsers, new Comparator<Hero>() {
            @Override
            public int compare(Hero h1, Hero h2) {
                return Double.compare(h2.getAverageTicketsUsed(), h1.getAverageTicketsUsed());
            }
        });


        int i = 1;

        for (Hero h : topTicketUsers){
            System.out.println(i + ". " + h.getHeroName() + ": " + h.getAverageTicketsUsed());
            i++;
        }
    }

    public void getTopTicketUsersByWeek(String command){

        String[] parts = command.split(" ");

        int week = Integer.parseInt(parts[2]);
        System.out.println(week);

        List<Hero> topTicketUsers = guild.getTopTicketUsers(week);

        Collections.sort(topTicketUsers, new Comparator<Hero>() {
            @Override
            public int compare(Hero h1, Hero h2) {
                return Double.compare(h2.getTickets(week), h1.getTickets(week));
            }
        });


        int i = 1;

        for (Hero h : topTicketUsers){
            System.out.println(i + ". " + h.getHeroName() + ": " + h.getTickets(week));
            i++;
        }
    }

    public void getLowTicketUsers(String command){
        String[] parts = command.split(" ");
        System.out.println(parts.length);
        if(parts.length >= 3){
            getLowTicketUsersByWeek(command);
        }else{
            getOverallLowTicketUsers();
        }
    }

    public void getOverallLowTicketUsers(){
        List<Hero> lowTicketUsers = guild.getLowTicketUsers();

        Collections.sort(lowTicketUsers, new Comparator<Hero>() {
            @Override
            public int compare(Hero h1, Hero h2) {
                return Double.compare(h1.getAverageTicketsUsed(), h2.getAverageTicketsUsed());
            }
        });


        int i = 1;

        for (Hero h : lowTicketUsers){
            System.out.println(i + ". " + h.getHeroName() + ": " + h.getAverageTicketsUsed());
            i++;
        }
    }

    public void getLowTicketUsersByWeek(String command){

        String[] parts = command.split(" ");

        int week = Integer.parseInt(parts[2]);
        System.out.println(week);

        List<Hero> lowTicketUsers = guild.getLowTicketUsers(week);

        Collections.sort(lowTicketUsers, new Comparator<Hero>() {
            @Override
            public int compare(Hero h1, Hero h2) {
                return Double.compare(h1.getTickets(week), h2.getTickets(week));
            }
        });


        int i = 1;

        for (Hero h : lowTicketUsers){
            System.out.println("    " + i + ". " + h.getHeroName() + ": " + h.getTickets(week));
            i++;
        }
    }


    //file management methods


    public void copy(String command)  {
        String[] parts = command.split(" ");
        Copy copy = null;
        try {
            copy = new Copy(new File(parts[1]),parts[3]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            copy.copy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create(String command){
        String[] parts = command.split(" ");
        File f = new File(parts[1]);
        try {
            Writer w = new Writer(f);
            w.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(String command){
        String[] parts = command.split(" ");
        File f = new File(parts[1]);
        f.delete();
    }


    //history methods


    public void history(String command){
        String[] parts = command.split(" ");
        if(parts[1].equals("trophies")){
            trophiesHistory(command);
        }else if(parts[1].equals("tickets")){
            ticketsHistory(command);
        }else if(parts[1].equals("hero_power")){
            heroPowerHistory(command);
        }else if(parts[1].equals("relation")){
            hpAndTrophiesRelationHistory(command);
        }else if(parts[1].equals("guild_exp")){
            guildExpHistory(command);
        }else if(parts[1].equals("donates")){
            donatesHistory(command);
        }else if(parts[1].equals("tributes")){
            tributesHistory(command);
        }else if(parts[1].equals("guild")){
            guildHistory(command);
        }else if(parts[1].equals("heroes")){
            printHeroesByWeek();
        }
    }

    public void trophiesHistory(String command){

        File f = new File("test.csv");
        List<String> trophyHistories = new ArrayList<>();

        String[] parts = command.split(" ");
        if(parts[2].equals("all")){
            for(Hero h : this.guild.getHeroes()){
                System.out.println(h.getGuildTrophyHistory().get(0));
                trophyHistories.add(h.getGuildTrophyHistory().get(1));
            }
        }
        else{
            System.out.println(this.guild.getHero(parts[2].toUpperCase()).getGuildTrophyHistory().get(0));

        }
        try {
            Writer w = new Writer(f);
            w.write(trophyHistories);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void ticketsHistory(String command){

        File f = new File("test.txt");
        List<String> ticketHistories = new ArrayList<>();

        String[] parts = command.split(" ");
        if(parts[2].equals("all")){
            for(Hero h : this.guild.getHeroes()){
                System.out.println(h.getTicketUseHistory());
                ticketHistories.add(h.getTicketUseHistory());
            }
        }
        else{
            System.out.println(this.guild.getHero(parts[2].toUpperCase()).getTicketUseHistory());
        }
        try {
            Writer w = new Writer(f);
            //w.write(trophyHistories);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void heroPowerHistory(String command){

        File f = new File("test.txt");
        List<String> ticketHistories = new ArrayList<>();

        String[] parts = command.split(" ");
        if(parts[2].equals("all")){
            for(Hero h : this.guild.getHeroes()){
                System.out.println(h.getHeroPowerHistory());
                ticketHistories.add(h.getHeroPowerHistory());
            }
        }
        else{
            System.out.println(this.guild.getHero(parts[2].toUpperCase()).getHeroPowerHistory());
        }
        try {
            Writer w = new Writer(f);
            //w.write(trophyHistories);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void guildExpHistory(String command){
        File f = new File("test.txt");
        List<String> ticketHistories = new ArrayList<>();

        String[] parts = command.split(" ");
        if(parts[2].equals("all")){
            for(Hero h : this.guild.getHeroes()){
                System.out.println(h.getGuildExpHistory());
                ticketHistories.add(h.getHeroPowerHistory());
            }
        }
        else{
            System.out.println(this.guild.getHero(parts[2].toUpperCase()).getGuildExpHistory());
        }
        try {
            Writer w = new Writer(f);
            //w.write(trophyHistories);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tributesHistory(String command){
        File f = new File("test.txt");
        List<String> ticketHistories = new ArrayList<>();

        String[] parts = command.split(" ");
        if(parts[2].equals("all")){
            for(Hero h : this.guild.getHeroes()){
                System.out.println(h.getGuildTributesHistory());
                ticketHistories.add(h.getHeroPowerHistory());
            }
        }
        else{
            System.out.println(this.guild.getHero(parts[2].toUpperCase()).getGuildTributesHistory());
        }
        try {
            Writer w = new Writer(f);
            //w.write(trophyHistories);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void donatesHistory(String command){
        File f = new File("test.txt");
        List<String> ticketHistories = new ArrayList<>();

        String[] parts = command.split(" ");
        if(parts[2].equals("all")){
            for(Hero h : this.guild.getHeroes()){
                System.out.println(h.getGuildDonatesHistory());
                ticketHistories.add(h.getHeroPowerHistory());
            }
        }
        else{
            System.out.println(this.guild.getHero(parts[2].toUpperCase()).getGuildDonatesHistory());
        }
        try {
            Writer w = new Writer(f);
            //w.write(trophyHistories);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hpAndTrophiesRelationHistory(String command){

        File f = new File("test.txt");
        List<String> relationHistories = new ArrayList<>();

        String[] parts = command.split(" ");
        if(parts[2].equals("all")){
            for(Hero h : this.guild.getHeroes()){
                System.out.println(h.getRelationOfHpAndTrophies());
                relationHistories.add(h.getRelationOfHpAndTrophies());
            }
        }
        else{
            System.out.println(this.guild.getHero(parts[2].toUpperCase()).getRelationOfHpAndTrophies());
        }
        try {
            Writer w = new Writer(f);
            //w.write(trophyHistories);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void guildHistory(String command){
        String[] parts = command.split(" ");
        System.out.println(parts[2]);
        if(parts[2].equals("guild_score")){
            guildScoreHistory();
        }else if(parts[2].equals("trophies")){
            guildAverageScorePerPlayer();
        }else if(parts[2].equals("tickets")){
            guildAverageTicketsPerPlayer();
        }
    }

    public void guildScoreHistory(){

        List<Integer> scores = new ArrayList<>();
        List<Integer> maxScores = new ArrayList<>();

        for(int i = 5; i<Integer.MAX_VALUE; i++){
            int guildScore = guild.getGuildScore(i);
            maxScores.add(guild.getMaxScores(i));
            scores.add(guildScore);
            if(guildScore<=0){
                for (int s : scores){
                    System.out.println(s);
                }
                for (int s : maxScores){
                    System.out.println(s);
                }
                break;
            }
        }

    }

    public void guildAverageScorePerPlayer(){
        for(int i = 5; i<Integer.MAX_VALUE; i++){
            int guildAverageTrophies = guild.getAverageTrophies(i);
            if(guildAverageTrophies==0){
                break;
            }
            System.out.println("  " + i + ". " + guildAverageTrophies);
        }
    }

    public void guildAverageTicketsPerPlayer(){
        for(int i = 5; i<Integer.MAX_VALUE; i++){
            int guildAverageTickets = guild.getAverageTickets(i);
            if(guildAverageTickets==0){
                break;
            }
            System.out.println("  " + i + ". " + guildAverageTickets);
        }
    }

    public void printHeroesByWeek(){

        for(Hero h : guild.getHeroes()){
            System.out.println(h);
        }
    }

    //damage calculations

    private void calculateBlueDamage(){

        String name;
        int attack, magic, multi;

        Scanner r = new Scanner(System.in);

        System.out.print("Hero name: ");
        name = r.nextLine();
        System.out.print("Attack: ");
        attack = Integer.parseInt(r.nextLine());
        System.out.print("Magic: ");
        magic = Integer.parseInt(r.nextLine());
        System.out.print("Multi: ");
        multi = Integer.parseInt(r.nextLine());

        Hero h = new Hero(name,0,attack,0,magic,multi);

        System.out.println(name + " : " + grouper.blueDamage(h));
        System.out.println("Shield : " + (grouper.blueDamage(h)-15000574));
    }

    public void startBuild() throws FileNotFoundException {
        Scanner r = new Scanner(System.in);
        Build build = new Build(itemList);
        while(true){
            String command = r.nextLine();
            String[] parts = command.split(" ",2);
            if(command.contains("item")){
                System.out.println(itemList.getItem(parts[1]));
            }else if(command.contains("links")){
                build.checkLinks();
            }else if(command.contains("load")){
                build = itemList.loadBuild(parts[1]);
            }else if(command.contains("overview")){
                build.checkOverview();
            }
        }
    }

    public void help(){
        System.out.println("copy [path to file].txt to [new file]");
        System.out.println("analyze [path to file]");
        System.out.println("create [path to new file]");
        System.out.println("delete [path to file]");
    }
}
