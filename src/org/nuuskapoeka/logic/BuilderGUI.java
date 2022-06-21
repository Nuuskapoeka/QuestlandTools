package org.nuuskapoeka.logic;

import org.nuuskapoeka.domain.BuildSlot;
import org.nuuskapoeka.domain.Item;
import org.nuuskapoeka.tools.Writer;

import javax.print.attribute.HashPrintJobAttributeSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuilderGUI extends JPanel{

    private static List<String> items;
    private static Items itemList;

    private static List<JComboBox> panels;
    private static List<JLabel> labels;

    private static Build currentlyLoaded;

    private static String buildPath;
    public BuilderGUI(List<String> items, Items itemList){
        this.items = items;
        this.itemList = itemList;
        this.panels = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.currentlyLoaded = new Build(itemList);
    }
    public BuilderGUI(List<String> items, Items itemList, String buildPath){
        this.items = items;
        this.itemList = itemList;
        this.panels = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.currentlyLoaded = new Build(itemList);
        this.buildPath = buildPath;
    }

    private static void createAndShowGUI() {
        //Create and set up the window.

        JFrame frame = new JFrame("Build Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        frame.add(createMainPanel());
        frame.pack();
        frame.setSize(1200,800);
        frame.setVisible(true);
    }

    public static JPanel createMainPanel(){
        JPanel panel = new JPanel();

        JLabel health = new JLabel();
        JLabel attack = new JLabel();
        JLabel defence = new JLabel();
        JLabel magic = new JLabel();
        JLabel total = new JLabel();

        JPanel equipped = new JPanel();
        GridLayout equipLayout = new GridLayout(2,5);
        equipped.setLayout(equipLayout);

        JPanel healthPanel = new JPanel();
        BoxLayout healthLayout = new BoxLayout(healthPanel,BoxLayout.Y_AXIS);
        healthPanel.setLayout(healthLayout);

        JPanel attackPanel = new JPanel();
        BoxLayout attackLayout = new BoxLayout(attackPanel,BoxLayout.Y_AXIS);
        attackPanel.setLayout(attackLayout);

        JPanel defencePanel = new JPanel();
        BoxLayout defenceLayout = new BoxLayout(defencePanel,BoxLayout.Y_AXIS);
        defencePanel.setLayout(defenceLayout);

        JPanel magicPanel = new JPanel();
        BoxLayout magicLayout = new BoxLayout(magicPanel,BoxLayout.Y_AXIS);

        magicPanel.setLayout(magicLayout);

        JPanel collections = new JPanel();
        GridLayout collectionLayout = new GridLayout(1,4);
        collections.setLayout(collectionLayout);
        collections.add(healthPanel);
        collections.add(attackPanel);
        collections.add(defencePanel);
        collections.add(magicPanel);

        JPanel statPanel = new JPanel();
        BoxLayout statLayout = new BoxLayout(statPanel,BoxLayout.Y_AXIS);
        statPanel.setLayout(statLayout);
        statPanel.setSize(200,50);
        statPanel.add(health);
        statPanel.add(attack);
        statPanel.add(defence);
        statPanel.add(magic);
        statPanel.add(total);

        //GridLayout gridLayout = new GridLayout(5,1);
        BoxLayout boxLayout = new BoxLayout(panel,BoxLayout.Y_AXIS);

        panel.setLayout(boxLayout);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Build Maker");
        equipped.add(createPanel("helm"));
        equipped.add(createPanel("chest"));
        equipped.add(createPanel("gloves"));
        equipped.add(createPanel("boots"));
        equipped.add(createPanel("necklace"));
        equipped.add(createPanel("ring"));
        equipped.add(createPanel("talisman"));

        healthPanel.add(createPanel("health"));
        healthPanel.add(createPanel("health"));
        healthPanel.add(createPanel("health"));
        healthPanel.add(createPanel("health"));
        healthPanel.add(createPanel("health"));

        attackPanel.add(createPanel("attack"));
        attackPanel.add(createPanel("attack"));
        attackPanel.add(createPanel("attack"));
        attackPanel.add(createPanel("attack"));
        attackPanel.add(createPanel("attack"));

        defencePanel.add(createPanel("defence"));
        defencePanel.add(createPanel("defence"));
        defencePanel.add(createPanel("defence"));
        defencePanel.add(createPanel("defence"));
        defencePanel.add(createPanel("defence"));

        magicPanel.add(createPanel("magic"));
        magicPanel.add(createPanel("magic"));
        magicPanel.add(createPanel("magic"));
        magicPanel.add(createPanel("magic"));
        magicPanel.add(createPanel("magic"));

        JButton button = new JButton("Check Links");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                List<String> build = new ArrayList<>();

                for(JComboBox jcb : panels){
                    if(jcb.getSelectedItem() == null) {
                        build.add("null");
                    }else {
                        build.add(jcb.getSelectedItem().toString());
                    }
                }
                try {
                    currentlyLoaded = itemList.loadBuild(build);
                    currentlyLoaded.checkLinks();
                    checkLinks(currentlyLoaded);
                    health.setText(currentlyLoaded.getHealth());
                    attack.setText(currentlyLoaded.getAttack());
                    defence.setText(currentlyLoaded.getDefence());
                    magic.setText(currentlyLoaded.getMagic());
                    total.setText(currentlyLoaded.getTotal());
                    currentlyLoaded.checkOverview();
                    saveBuild(build);
                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });

        panel.add(equipped);
        panel.add(collections);
        panel.add(statPanel);
        panel.add(button);

        return panel;
    }

    public static void saveBuild(List<String> build) throws IOException {
    	Writer w = new Writer(new File("build.csv"));
    	w.write(build);
    }

    public void startGUI(){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                    createAndShowItemSearchGUI();
                    loadBuildIn(currentlyLoaded.getFullBuild());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static ArrayList<String> filterType(String type){

        String[] types = {"health", "attack", "defence", "magic"};
        List<String> list = Arrays.asList(types);

        //System.out.println(type);
        ArrayList<String> filtered = new ArrayList<>();
        for(String s : items){
            //System.out.println(itemList.getItem(s).getSlot());
            if(itemList.getItem(s).getSlot().equalsIgnoreCase(type)){
                //System.out.println(itemList.getItem(s).getSlot());
                filtered.add(itemList.getItem(s).getName());
            }else if(itemList.getItem(s).getType().equalsIgnoreCase(type)){
                filtered.add(itemList.getItem(s).getName());
            }
        }
        return filtered;
    }
    public static ArrayList<String> filterInUse(){
        ArrayList<String> filtered = new ArrayList<>();
        for(String s : items){
            //System.out.println(itemList.getItem(s).getSlot());
            if(!isInUse(s)){
                //System.out.println(itemList.getItem(s).getSlot());
                filtered.add(s);
            }
        }
        return filtered;
    }
    public static JPanel createPanel(String type){
        JLabel label = new JLabel(type);

        JComboBox list = new JComboBox(filterType(type).toArray());
        list.setSelectedIndex(-1);
        list.setMaximumSize(new Dimension(250,35));

        JButton button = new JButton("print");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(itemList.getItem(list.getSelectedItem().toString()));
                items.remove(list.getSelectedItem().toString());
            }
        });

        JLabel label2 = new JLabel();

        labels.add(label2);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
        panel.setPreferredSize(new Dimension(250,80));
        panel.setMaximumSize(panel.getPreferredSize());
        GridLayout layout = new GridLayout(4,1);
        BoxLayout boxLayout = new BoxLayout(panel,BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        panel.add(label);
        panel.add(list);
        panel.add(label2);
        label2.setSize(new Dimension(label2.getWidth(),label2.getHeight()*2));

        panels.add(list);

        return panel;
    }
    public static void loadBuildIn(List<BuildSlot> fullBuild){
        int i = 0;
        for(JComboBox jcb : panels){
        	if(fullBuild.get(i).getItem()!=null) {
                jcb.setSelectedItem(fullBuild.get(i).getItem().getName());
                labels.get(i).setText(fullBuild.get(i).toString());
                //System.out.println(fullBuild.get(i).toString());
        	}
            i++;
        }
        currentlyLoaded.setFullBuild(fullBuild);
    }
    public static void checkLinks(Build build){
        int i = 0;
        for(BuildSlot bs : build.getFullBuild()){
        	if(bs.getItem()!=null) {
                labels.get(i).setText(bs.toString());
        	}
            //System.out.println(bs.toString());
            i++;
        }
    }
    public static boolean isInUse(String s){
        for(JComboBox jcb : panels){
            if(jcb.getSelectedItem().toString().equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }
    private static void createAndShowItemSearchGUI() throws FileNotFoundException {
        JFrame frame = new JFrame("Item Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        CardLayout cl = new CardLayout(1,1);
        panel.setSize(300,500);

        JTextField textField = new JTextField("egging on");
        textField.setPreferredSize(new Dimension(200,20));
        panel.add(textField);

        JPanel itemCardPanel = new JPanel();
        itemCardPanel.setLayout(new GridLayout(15,1));

        JLabel itemNameLabel = new JLabel();
        JLabel itemIdentifierLabel = new JLabel();
        JLabel itemEmblemLabel = new JLabel();
        JLabel itemTypeLabel = new JLabel();
        JLabel itemSlotLabel = new JLabel();
        JLabel itemPotentialLabel = new JLabel();
        JLabel statsLabel = new JLabel();
        JLabel healthLabel = new JLabel();
        JLabel attackLabel = new JLabel();
        JLabel defenceLabel = new JLabel();
        JLabel magicLabel = new JLabel();
        JLabel linksLabel = new JLabel();
        JLabel link1Label = new JLabel();
        JLabel link2Label = new JLabel();
        JLabel link3Label = new JLabel();

        JButton button = new JButton("Search");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = textField.getText();
                //System.out.println(itemList.getItem(itemName));
                itemNameLabel.setText(itemList.getItem(itemName).getName());
                itemIdentifierLabel.setText("    identifier = " + itemList.getItem(itemName).getIdentifier());
                itemEmblemLabel.setText("    emblem = " + itemList.getItem(itemName).getEmblem());
                itemTypeLabel.setText("    type = " + itemList.getItem(itemName).getType());
                itemSlotLabel.setText("    slot = " + itemList.getItem(itemName).getSlot());
                itemPotentialLabel.setText("    potential = " + itemList.getItem(itemName).getPotential());
                statsLabel.setText("    stats");
                healthLabel.setText("       health = " + itemList.getItem(itemName).getHealth());
                attackLabel.setText("       attack = " + itemList.getItem(itemName).getAttack());
                defenceLabel.setText("       defence = " + itemList.getItem(itemName).getDefence());
                magicLabel.setText("       magic = " + itemList.getItem(itemName).getMagic());
                linksLabel.setText("    links");
                link1Label.setText("       " + itemList.getItem(itemName).getLinks().get(0));
                link2Label.setText("       " + itemList.getItem(itemName).getLinks().get(1));
                link3Label.setText("       " + itemList.getItem(itemName).getLinks().get(2));

            }
        });

        panel.add(button);
        itemCardPanel.add(itemNameLabel);
        itemCardPanel.add(itemIdentifierLabel);
        itemCardPanel.add(itemEmblemLabel);
        itemCardPanel.add(itemTypeLabel);
        itemCardPanel.add(itemSlotLabel);
        itemCardPanel.add(itemPotentialLabel);
        itemCardPanel.add(statsLabel);
        itemCardPanel.add(healthLabel);
        itemCardPanel.add(attackLabel);
        itemCardPanel.add(defenceLabel);
        itemCardPanel.add(magicLabel);
        itemCardPanel.add(linksLabel);
        itemCardPanel.add(link1Label);
        itemCardPanel.add(link2Label);
        itemCardPanel.add(link3Label);

        panel.add(itemCardPanel);

        frame.add(panel);
        frame.pack();
        frame.setSize(300,500);
        frame.setVisible(true);
    }

}
