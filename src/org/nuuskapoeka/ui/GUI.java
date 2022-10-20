package org.nuuskapoeka.ui;

import org.nuuskapoeka.domain.Config;
import org.nuuskapoeka.domain.Guild;
import org.nuuskapoeka.domain.Hero;
import org.nuuskapoeka.logic.BarChart;
import org.nuuskapoeka.logic.BuilderGUI;
import org.nuuskapoeka.logic.GraphPanel;
import org.nuuskapoeka.logic.Items;
import org.nuuskapoeka.tools.Writer;

import javax.print.attribute.standard.JobName;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GUI {

    private static Guild guild;
    private static List<String> items;
    private static Items itemList;
    private static String buildPath;

    private static Config config;

    public GUI(Guild guild, List<String> items, Items itemList, Config c){
        this.guild = guild;
        this.items = items;
        this.itemList = itemList;
        this.config = c;
    }
    public static void createAndShowGUI() {
        //Create and set up the window.

        BuilderGUI builder = new BuilderGUI(items,itemList,buildPath);

        JFrame frame = new JFrame("Build Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<JPanel> list = new ArrayList<>();
        list.add(builder.createMainPanel());
        list.add(graphPanel());

        //Display the window.
        frame.add(graphPanel());
        //frame.add(new BarChart(convertList(guild.getGuildScores()),guild));
        frame.pack();
        frame.setSize(1200,800);
        frame.setVisible(true);

        //addFileInterface();
    }
    private static JPanel graphPanel(){
        JPanel panel = new JPanel();
        GraphPanel graphPanel = new GraphPanel(new ArrayList<>(),guild, new JComboBox(filterType().toArray()));

        //graphPanel.setComparison(guild.getHero("Nuuska").getHeroPowerHistoryAsList());

        //graphPanel.revalidate();
        //graphPanel.repaint();

        JComboBox memberList = new JComboBox(filterType().toArray());

        JPanel jPanel = new JPanel();

        //JComboBox options;

        JButton addNew = new JButton("Add");
        addNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFileInterface();
            }
        });

        ButtonGroup bg = new ButtonGroup();
        JRadioButton powerRadio = new JRadioButton("Hero Power");
        powerRadio.setSelected(true);
        JRadioButton trophiesRadio = new JRadioButton("Trophies");
        JRadioButton bossesRadio = new JRadioButton("Bosses Tagged");
        JRadioButton ticketsRadio = new JRadioButton("Tickets Used");
        JRadioButton expRadio = new JRadioButton("Guild Exp");
        bg.add(powerRadio);
        bg.add(trophiesRadio);
        bg.add(bossesRadio);
        bg.add(ticketsRadio);
        bg.add(expRadio);

        GridLayout layout = new GridLayout(2,1);
        panel.setLayout(layout);

        JButton button = new JButton("print");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!memberList.getSelectedItem().toString().equalsIgnoreCase("guild")){
                    if(panel.getComponent(1) != null){
                        panel.remove(1);
                    }
                    if(powerRadio.isSelected()){
                        panel.add(new GraphPanel(guild.getHero(memberList.getSelectedItem().toString()).getHeroPowerHistoryAsList(),guild,memberList));
                    }else if(trophiesRadio.isSelected()){
                        panel.add(new GraphPanel(guild.getHero(memberList.getSelectedItem().toString()).getTrophyHistoryAsList(),guild,memberList));
                    }else if(expRadio.isSelected()){
                        panel.add(new GraphPanel(guild.getHero(memberList.getSelectedItem().toString()).getGuildExpHistoryAsList(),guild,memberList));
                    }else if(bossesRadio.isSelected()){
                        panel.add(new GraphPanel(guild.getHero(memberList.getSelectedItem().toString()).getBossesTaggedHistoryAsList(),guild,memberList));
                    }else if(ticketsRadio.isSelected()){
                        panel.add(new GraphPanel(guild.getHero(memberList.getSelectedItem().toString()).getTicketsHistoryAsList(),guild,memberList));
                    }
                }else{
                    if(panel.getComponentCount() > 1){
                        panel.remove(1);

                    }
                    if(powerRadio.isSelected()){
                        panel.add(new GraphPanel(convertList(guild.getAverageHeroPower()),guild,memberList));
                    }else if(trophiesRadio.isSelected()){
                        panel.add(new GraphPanel(convertList(guild.getGuildScores()),guild,memberList));
                    }
                }
                panel.revalidate();
                panel.repaint();
            }
        });

        JPanel radioPanel = new JPanel();
        BoxLayout radioLayout = new BoxLayout(radioPanel,BoxLayout.Y_AXIS);
        radioPanel.setLayout(radioLayout);
        jPanel.add(memberList);
        jPanel.add(button);
        radioPanel.add(powerRadio);
        radioPanel.add(trophiesRadio);
        radioPanel.add(bossesRadio);
        radioPanel.add(ticketsRadio);
        radioPanel.add(expRadio);
        jPanel.add(radioPanel);
        jPanel.add(addNew);
        panel.add(jPanel);

        panel.add(graphPanel);

        return panel;
    }

    public static void addFileInterface(){
        JFrame frame = new JFrame("Build Planner");

        JPanel panel = new JPanel();
        JPanel control = new JPanel();
        JPanel pasteField = new JPanel();

        control.setLayout(new FlowLayout());
        panel.setLayout(new GridLayout(2,1));

        //panel.setSize(1200,800);

        JTextField weekField = new JTextField();
        weekField.setPreferredSize(new Dimension(80,40));

        JTextArea statPasteField = new JTextArea();
        statPasteField.setPreferredSize(new Dimension(1000,600));

        JButton returnButton = new JButton("Back");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndShowGUI();
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int w = Integer.parseInt(weekField.getText());
                File f;
                if(w%2==0){
                    f = new File(config.getPathToGuildFiles() + "red_horsemen_" + w+ ".csv");
                }else{
                    f = new File(config.getPathToGuildFiles() +  "blue_horsemen_" + w + ".csv");
                }
                try {
                    Writer writer = new Writer(f);
                    writer.write(statPasteField.getText());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                Scanner r = null;
                try {
                    r = new Scanner(f);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                //System.out.println(f);
                int i = 1;
                try {
                    r = new Scanner(f);
                    while(r.hasNextLine()){
                        String event = r.nextLine();
                        System.out.println(event);
                        String[] parts = event.split(",");
                        String[] heroEvent = event.split(",",2);
                        if(i>1){
                            if(guild.getHero(parts[0]) == null){
                                guild.addHero(new Hero(parts[0], Integer.parseInt(parts[5]), Integer.parseInt(parts[26]),Integer.parseInt(parts[12]), Integer.parseInt(parts[24])));
                            }
                            guild.getHero(parts[0]).addEvent(w +"," + heroEvent[1]);
                            guild.addEvent(w);
                        }
                    }
                    System.out.println(guild.getHero("nuuska").getHeroPower(30));
                } catch (FileNotFoundException excp) {
                    excp.printStackTrace();
                }
                String weekNumber;

                while(r.hasNextLine()){
                    String event = r.nextLine();
                    System.out.println(event);
                    String[] parts = event.split(",");
                    String[] heroEvent = event.split(",",2);
                    if(i>1){
                        if(guild.getHero(parts[0]) == null){
                            guild.addHero(new Hero(parts[0], Integer.parseInt(parts[5]), Integer.parseInt(parts[26]),Integer.parseInt(parts[12]), Integer.parseInt(parts[24])));
                        }
                        guild.getHero(parts[0]).addEvent(findWeek(f.toString()) +"," + heroEvent[1]);
                        guild.addEvent(w);
                    }
                    graphPanel().revalidate();
                }
                guild.getHero("nuuska").getHeroPower(30);
            }
        });

        control.add(returnButton);

        control.add(weekField);
        control.add(saveButton);
        pasteField.add(statPasteField);

        panel.add(control);
        panel.add(pasteField);

        frame.add(panel);
        frame.pack();
        frame.setSize(1200,800);
        frame.setVisible(true);
    }

    public static void menuInterface(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame menuFrame = new JFrame("Questland Tools  a0.4  © Nuuskapoeka#9061");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setSize(400,600);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton builderButton = new JButton("Builder");
        builderButton.setMinimumSize(new Dimension(menuFrame.getWidth(),builderButton.getHeight()));
        builderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuilderGUI builder = new BuilderGUI(items,itemList,buildPath);
                builder.startGUI();
            }
        });

        JButton trackerButton = new JButton("Tracker");

        trackerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndShowGUI();
            }
        });

        JButton configButton = new JButton("Settings");

        panel.add(builderButton);
        panel.add(trackerButton);
        panel.add(configButton);


        menuFrame.add(panel);
        menuFrame.pack();
        menuFrame.setLocation(screenSize.width/3,0);
        menuFrame.setSize(400,600);
        menuFrame.setVisible(true);
    }

    private static String findWeek(String fileName){
        String[] parts1 = fileName.split("_");
        String[] parts2 = parts1[parts1.length-1].split("\\.");

        return parts2[0];
    }
    private static List<Double> convertList(List<Integer> data){
        List<Double> list = new ArrayList<>();
        for(int i : data){
            list.add((double) i);
        }
        return list;
    }

    public static JPanel sceneControl(List<JPanel> panels){
        JPanel panel = new JPanel();

        JPanel buttonPane = new JPanel();
        buttonPane.setAlignmentX(Component.TOP_ALIGNMENT);
        JPanel contentPane = new JPanel();

        JButton button = new JButton("Builder");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                contentPane.add(panels.get(0));
                contentPane.revalidate();
                contentPane.repaint();

            }
        });

        JButton button2 = new JButton("Graph");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                contentPane.add(graphPanel());
                contentPane.revalidate();
                contentPane.repaint();
            }
        });

        BoxLayout grid = new BoxLayout(buttonPane,BoxLayout.Y_AXIS);
        buttonPane.setLayout(grid);
        buttonPane.add(button);
        buttonPane.add(button2);
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        panel.setLayout(layout);
        panel.add(buttonPane);
        panel.add(contentPane);



        return panel;
    }

    public static ArrayList<String> filterType(){

        String[] types = {"heroName", "attack", "defence", "magic"};
        List<String> list = Arrays.asList(types);

        //System.out.println(type);
        ArrayList<String> filtered = new ArrayList<>();
        for(Hero h : guild.getHeroes()){
            if(h.getEvents().size()>1){
                filtered.add(h.getHeroName());
            }
        }
        filtered.add("GUILD");
        return filtered;
    }

}
