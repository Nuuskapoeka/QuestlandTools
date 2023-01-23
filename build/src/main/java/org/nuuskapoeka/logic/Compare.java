package org.nuuskapoeka.logic;

import org.nuuskapoeka.domain.Hero;
import org.nuuskapoeka.tools.Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Compare {

    private Reader readerEarlier;
    private Reader readerLater;

    private List<Hero> earlierMembers;
    private List<Hero> laterMembers;

    public Compare(File file1, File file2) throws FileNotFoundException {
        this.readerEarlier = new Reader(file1);
        this.readerLater = new Reader(file2);

        this.earlierMembers = new ArrayList<>();
        this.laterMembers = new ArrayList<>();

        read();
    }

    public void read() {
        int i = 1;
        for (String s : readerEarlier.read()) {
            String[] parts = s.split(",");
            if (i > 1) {
                earlierMembers.add(new Hero(parts[0], Integer.parseInt(parts[5]), Integer.parseInt(parts[26]), Integer.parseInt(parts[12]), Integer.parseInt(parts[24])));
            }
            i++;
        }
        int i2 = 1;
        for (String s : readerLater.read()) {
            String[] parts = s.split(",");
            if (i2 > 1) {
                laterMembers.add(new Hero(parts[0], Integer.parseInt(parts[5]), Integer.parseInt(parts[26]), Integer.parseInt(parts[12]), Integer.parseInt(parts[24])));
            }
            i2++;
        }
    }

    public void compare() {

        int avrgSkullTokens = 0;
        int avrgGuildTrophies = 0;
        int avrgTicketsUsed = 0;

        int membersCompared = 0;

        for (Hero h : earlierMembers) {

            for (Hero h2 : laterMembers) {

                if(h.getHeroName().equals(h2.getHeroName())){

                    int diffSkullTokens = h2.getBossesHit()-h.getBossesHit();
                    int diffGuildTrophies = h2.getGuildTrophies()-h.getGuildTrophies();
                    int diffTicketsUsed = h2.getTicketsUsed()-h.getTicketsUsed();

                    avrgSkullTokens += diffSkullTokens;
                    avrgGuildTrophies += diffGuildTrophies;
                    avrgTicketsUsed += diffTicketsUsed;

                    System.out.println(h.getHeroName());
                    System.out.println("  Skull Tokens:   " + diffSkullTokens);
                    System.out.println("  Guild Trophies: " + diffGuildTrophies);
                    System.out.println("  Tickets Used:   " + diffTicketsUsed);

                    membersCompared++;
                }
            }
        }
        System.out.println("AVERAGE");
        System.out.println("  Skull Tokens:   " + avrgSkullTokens/membersCompared);
        System.out.println("  Guild Trophies: " + avrgGuildTrophies/membersCompared);
        System.out.println("  Tickets Used:   " + avrgTicketsUsed/membersCompared);
    }
}