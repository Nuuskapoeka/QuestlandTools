package org.nuuskapoeka.ui;

import org.nuuskapoeka.domain.Guild;
import org.nuuskapoeka.domain.Hero;
import org.nuuskapoeka.logic.BuilderGUI;
import org.nuuskapoeka.logic.GraphPanel;
import org.nuuskapoeka.logic.Items;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI {

    private static Guild guild;
    private static List<String> items;
    private static Items itemList;
    private static String buildPath;

    public GUI(Guild guild, List<String> items, Items itemList){
        this.guild = guild;
        this.items = items;
        this.itemList = itemList;
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
        frame.pack();
        frame.setSize(1200,800);
        frame.setVisible(true);
    }
    private static JPanel graphPanel(){
        JPanel panel = new JPanel();
        GraphPanel graphPanel = new GraphPanel(new ArrayList<>(),guild, new JComboBox(filterType().toArray()));

        JComboBox memberList = new JComboBox(filterType().toArray());

        JPanel jPanel = new JPanel();

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
                    panel.revalidate();
                    panel.repaint();
                }else{
                    if(panel.getComponentCount() > 1){
                        panel.remove(1);

                    }
                    if(powerRadio.isSelected()){
                        panel.add(new GraphPanel(convertList(guild.getAverageHeroPower()),guild,memberList));
                    }else if(trophiesRadio.isSelected()){
                        panel.add(new GraphPanel(convertList(guild.getGuildScores()),guild,memberList));
                    }
                    panel.revalidate();
                    panel.repaint();
                }
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
        panel.add(jPanel);

        panel.add(graphPanel);

        return panel;
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

        BoxLayout grid = new BoxLayout(buttonPane,BoxLayout.X_AXIS);
        buttonPane.setLayout(grid);
        buttonPane.add(button);
        buttonPane.add(button2);
        GridLayout layout = new GridLayout(2, 1);
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
