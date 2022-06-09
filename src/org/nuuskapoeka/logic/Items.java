package org.nuuskapoeka.logic;

import org.nuuskapoeka.domain.Item;
import org.nuuskapoeka.tools.Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Items {

    private List<Item> itemList;
    private boolean isLoaded;

    public Items(){
        itemList = new ArrayList<>();
        isLoaded = false;
    }
    public void load(String file) throws FileNotFoundException {
        Reader r = new Reader(new File(file));

        for(String s : r.read()){
            String[] parts = s.split(",");
            //System.out.println(parts[1]);

            if(parts[2].equalsIgnoreCase("DEFENSE")){
                parts[2] = "DEFENCE";
                //System.out.println(parts[2]);
            }
            Item i = new Item(parts[0].toUpperCase(),
                    parts[1].toUpperCase(),
                    parts[2].toUpperCase(),
                    parts[3].toUpperCase(),
                    Integer.parseInt(parts[4]),
                    Integer.parseInt(parts[5]),
                    Integer.parseInt(parts[6]),
                    Integer.parseInt(parts[7]),
                    Integer.parseInt(parts[8]),
                    parts[9].toUpperCase(),
                    parts[10].toUpperCase(),
                    parts[11].toUpperCase(),
                    parts[12]);
            itemList.add(i);
        }
        isLoaded = true;
        for (Item i : itemList){
            //System.out.println(i);
        }

    }
    public void findLength(int length, String comparableEntry, String includedLetters){
        List<Item> list = new ArrayList<>();
        for(Item i : itemList){
            String s = i.getName().replace(" ", "");
            if(s.length()==length && compareEntries(comparableEntry,s,includedLetters)){
                list.add(i);
                System.out.println(s);
            }
        }
        for(Item i : list){
            //System.out.println(i.getName());
        }
    }
    public int lengthNoSpaces(String name){
        String str = name;
        String[] myString = str.split(" ");
        int length = myString.length;
        return length;
    }
    public boolean compareEntries(String s, String e, String letters){
        String[] charSplit = s.split("");
        //System.out.println(s);
        String[] charSplit2 = e.split("");
        //System.out.println(e);
        String[] letterSplit = letters.split("");
        int index = 0;
        for(String c : charSplit){
            //System.out.println(c);
            if(c.equals("_")){
                //System.out.println(c);
                index++;
                continue;
            }
            if(!charSplit[index].equalsIgnoreCase(charSplit2[index])){
                //System.out.println("returning false in matching check");
                return false;
            }else{
                //System.out.println("letters match");
            }
            index++;
        }
        for(String c3 : letterSplit){
            if(!e.contains(c3)){
                //System.out.println("returning false in letter check");
                return false;
            }
        }
        return true;
    }
    /*
    public void load() throws FileNotFoundException {
        Reader r = new Reader(new File("C:\\Users\\s2000997\\Documents\\itemsList.csv"));

        for(String s : r.read()){

            String[] parts = s.split(",");
            System.out.println(parts[1]);

            if(parts[2].equalsIgnoreCase("DEFENSE")){
                parts[2] = "DEFENCE";
                //System.out.println(parts[2]);
            }
            Item i = new Item(parts[0].toUpperCase(),
                    parts[1].toUpperCase(),
                    parts[2].toUpperCase(),
                    parts[3].toUpperCase(),
                    Integer.parseInt(parts[4]),
                    Integer.parseInt(parts[5]),
                    Integer.parseInt(parts[6]),
                    Integer.parseInt(parts[7]),
                    Integer.parseInt(parts[8]),
                    parts[9].toUpperCase(),
                    parts[10].toUpperCase(),
                    parts[11].toUpperCase(),
                    parts[12]);
            itemList.add(i);
        }
        isLoaded = true;
        for (Item i : itemList){
            System.out.println(i);
        }

    }
    */
    public Item getItem(String name){
        for(Item i : itemList){
            if(i.getName().equalsIgnoreCase(name)){
                return i;
            }
        }
        for(Item i : itemList){
            if(i.getIdentifier().equalsIgnoreCase(name)){
                return i;
            }
        }
        return null;
    }
    public Build loadBuild() throws FileNotFoundException {
        Reader r = new Reader(new File("build.csv"));
        if(!isLoaded){
            //load();
        }
        Build build = new Build(this);

        int i = 0;

        for (String s : r.read()){
            if(!s.isEmpty()){
                build.addItem(getItem(s));
                //System.out.println(getItem(s));
            }
        }
        return build;
    }
    public Build loadBuild(String fileName) throws FileNotFoundException {
        Reader r = new Reader(new File(fileName));
        if(!isLoaded){
            //load();
        }
        Build build = new Build(this);

        int i = 0;

        for (String s : r.read()){
            if(!s.isEmpty()){
                build.addItem(getItem(s));
                //System.out.println(getItem(s));
            }
        }
        System.out.println("Loaded");
        return build;
    }
    public Build loadBuild(List<String> buildList) throws FileNotFoundException {
        if(!isLoaded){
            //load();
        }
        Build build = new Build(this);

        int i = 0;

        for (String s : buildList){
            if(!s.isEmpty()){
                build.addItem(getItem(s));
                //System.out.println(getItem(s));
            }
        }
        System.out.println("Loaded");

        return build;
    }
    public void loadAndHandle() throws FileNotFoundException {
        Reader r = new Reader(new File("build\\fullItems.csv"));

        for(String s : r.read()){
            String[] parts = s.split(",");
            //System.out.println(parts[1]);
            if(!parts[3].equals("MAIN_HAND") && !parts[3].equals("OFF_HAND")){
                if(parts[2].equalsIgnoreCase("DEFENSE")){
                    parts[2] = "DEFENCE";
                    //System.out.println(parts[2]);
                }

                //System.out.println(parts[1]);
                Item i = new Item(parts[0],
                        parts[1].toUpperCase(),
                        parts[2].toUpperCase(),
                        parts[3].toUpperCase(),
                        Integer.parseInt(parts[4]),
                        Integer.parseInt(parts[5]),
                        Integer.parseInt(parts[6]),
                        Integer.parseInt(parts[7]),
                        Integer.parseInt(parts[8]),
                        splitString(parts[9]),
                        splitString(parts[10]),
                        splitString(parts[11]),
                        parts[12]);
                itemList.add(i);
            }
        }
        isLoaded = true;
        for (Item i : itemList){
            //System.out.println(i.toCSV());
        }
    }
    public void clearEmpty() throws FileNotFoundException {
        Reader r = new Reader(new File("build\\ql_database.csv"));
        for(String s : r.read()){
            String[] parts = s.split(",");
            //System.out.println(parts[1]);
            if(parts.length >= 19 && !parts[3].equals("MAIN_HAND") && !parts[3].equals("OFF_HAND")) {
                //System.out.println(parts[18]);
            }
        }
    }
    private String splitString(String s){
        String[] parts = s.split("-");

        if(parts.length < 2){
            return parts[0].toUpperCase();
        }

        return parts[1].toUpperCase();
    }

    public List<Item> getItemList() {
        return itemList;
    }
    public List<String> getItemNames(){
        List<String> itemNames = new ArrayList<>();
        for (Item i : itemList){
            itemNames.add(i.getName());
        }
        return itemNames;
    }
}
