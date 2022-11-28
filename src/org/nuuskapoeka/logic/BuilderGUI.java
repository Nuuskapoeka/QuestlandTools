package org.nuuskapoeka.logic;

import org.nuuskapoeka.domain.BuildSlot;
import org.nuuskapoeka.domain.Item;
import org.nuuskapoeka.tools.Writer;

import javax.imageio.ImageIO;
import javax.print.attribute.HashPrintJobAttributeSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuilderGUI extends JPanel{

    private static List<String> items;
    private static Items itemList;

    private static List<JComboBox> panels;
    private static List<JLabel> labels;
    private static List<JLabel> images;
    private static List<JCheckBox> oneStats;
    private static Build currentlyLoaded;

    private static String buildPath;
    public BuilderGUI(List<String> items, Items itemList){
        this.items = items;
        this.itemList = itemList;
        this.panels = new ArrayList<>();
        this.images = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.currentlyLoaded = new Build(itemList);
    }
    public BuilderGUI(List<String> items, Items itemList, String buildPath){
        this.items = items;
        this.itemList = itemList;
        this.panels = new ArrayList<>();
        this.images = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.currentlyLoaded = new Build(itemList);
        this.buildPath = buildPath;
    }

    private static void createAndShowGUI() {
        //Create and set up the window.

        JFrame frame = new JFrame("Build Planner ©Nuuskapoeka#9061");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        JLabel maxHealth = new JLabel();
        JLabel maxAttack = new JLabel();
        JLabel maxDefence = new JLabel();
        JLabel maxMagic = new JLabel();
        JLabel maxTotal = new JLabel();

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

        JPanel miscPanel = new JPanel();
        GridLayout miscLayout = new GridLayout(1,4);
        miscPanel.setLayout(miscLayout);
        JPanel statPanel = new JPanel();
        BoxLayout statLayout = new BoxLayout(statPanel,BoxLayout.Y_AXIS);
        JPanel maxStatPanel = new JPanel();
        BoxLayout maxStatLayout = new BoxLayout(maxStatPanel,BoxLayout.Y_AXIS);
        statPanel.setLayout(statLayout);
        statPanel.setSize(200,50);
        statPanel.add(health);
        statPanel.add(attack);
        statPanel.add(defence);
        statPanel.add(magic);
        statPanel.add(total);
        maxStatPanel.setLayout(maxStatLayout);
        maxStatPanel.add(maxHealth);
        maxStatPanel.add(maxAttack);
        maxStatPanel.add(maxDefence);
        maxStatPanel.add(maxMagic);
        maxStatPanel.add(maxTotal);

        JPanel orbPanel = new JPanel();
        GridLayout orbLayout = new GridLayout(8,1);
        JPanel orbEnhPanel = new JPanel();
        orbEnhPanel.setLayout(orbLayout);
        JTextField orbBaseField = new JTextField("2000");
        JTextField orbPotField = new JTextField("200");
        JTextField orbEnhField = new JTextField("8");
        JTextField guildBonus = new JTextField("1.15");

        orbEnhPanel.add(new JLabel("Orb Base Power:"));
        orbEnhPanel.add(orbBaseField);
        orbEnhPanel.add(new JLabel("Orb Potential:"));
        orbEnhPanel.add(orbPotField);
        orbEnhPanel.add(new JLabel("Orb Enhance:"));
        orbEnhPanel.add(orbEnhField);
        orbEnhPanel.add(new JLabel("Guild Bonus:"));
        orbEnhPanel.add(guildBonus);
        orbPanel.add(orbEnhPanel);

        miscPanel.add(statPanel);
        miscPanel.add(orbPanel);
        //GridLayout gridLayout = new GridLayout(5,1);
        BoxLayout boxLayout = new BoxLayout(panel,BoxLayout.Y_AXIS);

        panel.setLayout(boxLayout);

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

        JPanel weaponsPanel = new JPanel();
        GridLayout weaponLayout = new GridLayout(2,1);
        weaponsPanel.setLayout(weaponLayout);
        weaponsPanel.add(createPanel("main hand"));
        weaponsPanel.add(createPanel("off hand"));

        miscPanel.add(weaponsPanel);
        miscPanel.add(maxStatPanel);

        JButton button = new JButton("Check Links");
        JButton visiualize = new JButton("Visualize");
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
                    currentlyLoaded.setAvgOrbBase(Integer.valueOf(orbBaseField.getText()));
                    currentlyLoaded.setAvgOrbPot(Integer.valueOf(orbPotField.getText()));
                    currentlyLoaded.setAvgEnhance(Integer.valueOf(orbEnhField.getText()));
                    currentlyLoaded.setGuildBonus(Double.valueOf(guildBonus.getText()));
                    currentlyLoaded.checkLinks();
                    checkLinks(currentlyLoaded);
                    //System.out.println(currentlyLoaded.getMaxStats());
                    health.setText(currentlyLoaded.getHealth());
                    attack.setText(currentlyLoaded.getAttack());
                    defence.setText(currentlyLoaded.getDefence());
                    magic.setText(currentlyLoaded.getMagic());
                    total.setText(currentlyLoaded.getTotal());
                    maxHealth.setText(currentlyLoaded.getMaxHealth());
                    maxAttack.setText(currentlyLoaded.getMaxAttack());
                    maxDefence.setText(currentlyLoaded.getMaxDefence());
                    maxMagic.setText(currentlyLoaded.getMaxMagic());
                    maxTotal.setText(currentlyLoaded.getMaxTotal());
                    currentlyLoaded.checkOverview();
                    saveBuild(build);
                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });
        visiualize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkLinks(currentlyLoaded);
                try {
                    createAndShowGearVisualizer(currentlyLoaded);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JPanel buttons = new JPanel();
        BoxLayout buttonLayout = new BoxLayout(buttons,BoxLayout.X_AXIS);
        buttons.setLayout(buttonLayout);
        buttons.add(button);
        buttons.add(visiualize);

        panel.add(buttons);
        panel.add(equipped);
        panel.add(collections);
        panel.add(miscPanel);

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
            }else if((itemList.getItem(s).getType()!= null) && itemList.getItem(s).getType().equalsIgnoreCase(type)){
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

        String[] types = {"health", "attack", "defence", "magic"};

        JComboBox list = new JComboBox(filterType(type).toArray());
        list.setSelectedIndex(-1);
        list.setMaximumSize(new Dimension(250,35));

        JCheckBox oneStat = new JCheckBox();

        JButton button = new JButton("print");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(itemList.getItem(list.getSelectedItem().toString()));
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
        //oneStats.add(oneStat);
        if(!Arrays.asList(types).contains(type)){
            //panel.add(oneStat);
        }
        label2.setSize(new Dimension(label2.getWidth(),label2.getHeight()*2));
        //images.add(imageLabel);
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
                try{
                    URL url = new URL(bs.getItem().getIconUrl());
                    BufferedImage image = ImageIO.read(url);

                    //images.get(i).setIcon(new ImageIcon(resizeImage(image,15,15)));
                }catch (Exception e){
                    System.out.println(bs.getItem().getIconUrl() + " image not found");
                }
        	}
            //System.out.println(bs.toString());
            i++;
        }
    }
    static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image tmp = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }
    public static boolean isInUse(String s){
        for(JComboBox jcb : panels){
            if(jcb.getSelectedItem().toString().equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }
    private static void createAndShowGearVisualizer(Build build) throws FileNotFoundException {
        JFrame frame = new JFrame("Visualize");
        GridLayout panelLayout = new GridLayout(1,3);
        JPanel panel = new JPanel();
        panel.setLayout(panelLayout);
        GridLayout mainLayout = new GridLayout(4,2);
        JPanel mainGearHolder = new JPanel();
        BoxLayout holderLayout = new BoxLayout(mainGearHolder,BoxLayout.Y_AXIS);
        mainGearHolder.setLayout(holderLayout);
        JPanel mainGear = new JPanel();
        mainGear.setLayout(mainLayout);
        mainGearHolder.add(new JLabel("Equipped"));
        mainGearHolder.add(mainGear);

        JPanel collectionOne = new JPanel();
        GridLayout colOneLayout = new GridLayout(4,3);

        JPanel collectionTwo = new JPanel();
        GridLayout colTwoLayout = new GridLayout(4,3);
        mainGear.add(createSlot(build.getEquipped()[0]));
        double imageScale = 2.5;
        try{
            BufferedImage bi = ImageIO.read(new URL(build.getEquipped()[0].getItem().getFullUrl()));
            mainGear.add(new JLabel(new ImageIcon(resizeImage(bi, (int) (bi.getWidth()/imageScale), (int) (bi.getHeight()/imageScale)))));
        }catch(Exception e){

        }
        mainGear.add(createSlot(build.getEquipped()[4]));
        mainGear.add(createSlot(build.getEquipped()[1]));
        try{
            BufferedImage bi = ImageIO.read(new URL(build.getEquipped()[1].getItem().getFullUrl()));
            mainGear.add(new JLabel(new ImageIcon(resizeImage(bi, (int) (bi.getWidth()/imageScale), (int) (bi.getHeight()/imageScale)))));
        }catch(Exception e){

        }
        mainGear.add(createSlot(build.getEquipped()[5]));
        mainGear.add(createSlot(build.getEquipped()[2]));
        try{
            BufferedImage bi = ImageIO.read(new URL(build.getEquipped()[2].getItem().getFullUrl()));
            mainGear.add(new JLabel(new ImageIcon(resizeImage(bi, (int) (bi.getHeight()/imageScale), (int) (bi.getHeight()/imageScale)))));
        }catch(Exception e){

        }
        mainGear.add(createSlot(build.getEquipped()[6]));
        mainGear.add(createSlot(build.getEquipped()[3]));
        try{
            BufferedImage bi = ImageIO.read(new URL(build.getEquipped()[3].getItem().getFullUrl()));
            mainGear.add(new JLabel(new ImageIcon(resizeImage(bi, (int) Math.round(bi.getHeight()/imageScale), (int) (bi.getHeight()/imageScale)))));
        }catch(Exception e){

        }

        JPanel colOneHolder = new JPanel();
        BoxLayout colOneHolderLayout = new BoxLayout(colOneHolder,BoxLayout.Y_AXIS);
        colOneHolder.setLayout(colOneHolderLayout);
        colOneHolder.add(new JLabel("Collection I"));
        colOneHolder.add(collectionOne);
        collectionOne.setLayout(colOneLayout);
        collectionOne.add(createSlot(build.getHealthSlots()[0]));
        collectionOne.add(createSlot(build.getMagicSlots()[0]));
        collectionOne.add(createSlot(build.getHealthSlots()[1]));
        collectionOne.add(createSlot(build.getMagicSlots()[1]));
        collectionOne.add(new JPanel());
        collectionOne.add(createSlot(build.getDefenceSlots()[0]));
        collectionOne.add(createSlot(build.getAttackSlots()[0]));
        collectionOne.add(new JPanel());
        collectionOne.add(createSlot(build.getAttackSlots()[1]));
        collectionOne.add(createSlot(build.getDefenceSlots()[1]));
        collectionOne.add(createSlot(build.getHealthSlots()[2]));
        collectionOne.add(createSlot(build.getMagicSlots()[2]));

        JPanel colTwoHolder = new JPanel();
        BoxLayout colTwoHolderLayout = new BoxLayout(colTwoHolder,BoxLayout.Y_AXIS);
        colTwoHolder.setLayout(colTwoHolderLayout);
        colTwoHolder.add(new JLabel("Collection II"));
        colTwoHolder.add(collectionTwo);
        collectionTwo.setLayout(colTwoLayout);
        collectionTwo.add(createSlot(build.getAttackSlots()[2]));
        collectionTwo.add(createSlot(build.getDefenceSlots()[2]));
        collectionTwo.add(createSlot(build.getAttackSlots()[3]));
        collectionTwo.add(createSlot(build.getDefenceSlots()[3]));
        collectionTwo.add(new JPanel());
        collectionTwo.add(createSlot(build.getMagicSlots()[3]));
        collectionTwo.add(createSlot(build.getHealthSlots()[3]));
        collectionTwo.add(new JPanel());
        collectionTwo.add(createSlot(build.getHealthSlots()[4]));
        collectionTwo.add(createSlot(build.getMagicSlots()[4]));
        collectionTwo.add(createSlot(build.getAttackSlots()[4]));
        collectionTwo.add(createSlot(build.getDefenceSlots()[4]));

        panel.add(mainGearHolder);
        panel.add(colOneHolder);
        panel.add(colTwoHolder);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private static JPanel createSlot(BuildSlot bs){
        JPanel slot = new JPanel();
        BoxLayout layout = new BoxLayout(slot,BoxLayout.Y_AXIS);
        slot.setLayout(layout);
        JLabel imagePanel = new JLabel();
        try{
            URL url = new URL(bs.getItem().getIconUrl());
            BufferedImage image = ImageIO.read(url);
            imagePanel.setIcon(new ImageIcon(image));
        }catch(Exception e){
            System.out.println(bs.getItem().getName() + " image not found");
        }
        slot.add(imagePanel);
        JLabel linksLabel = new JLabel(bs.activeLinksToStringStars());
        linksLabel.setFont(new Font(linksLabel.getFont().getFontName(),linksLabel.getFont().getStyle(),20));
        linksLabel.setHorizontalTextPosition(JLabel.CENTER);
        linksLabel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        slot.add(linksLabel);

        //slot.add(new JLabel(bs.getItem().getName()));
        return slot;
    }
    private static void createAndShowItemSearchGUI() throws FileNotFoundException {
        JFrame frame = new JFrame("Item Search");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        //frame.setVisible(true);
    }
    private JLabel displayImage(String link) throws IOException {
        URL url = new URL(link);
        BufferedImage image = ImageIO.read(url);
        return new JLabel(new ImageIcon(image));
    }
}
