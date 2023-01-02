package org.nuuskapoeka;

import org.nuuskapoeka.domain.Config;
import org.nuuskapoeka.logic.*;
import org.nuuskapoeka.ui.MainUI;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Config config = new Config("config.txt");

        Build b = new Build(new Items());

        Items i = new Items();
        TalentManager talentManager = new TalentManager("https://docs.google.com/spreadsheets/d/e/2PACX-1vQACdbvpCIg7Uri2UZ_ZpoPLqEQzB0tWtnf8J8awM7s7DwvZQkoet1V-8TYyEKYPPo_CtU4QdtQDHxo/pub?gid=851318924&single=true&output=csv");
        GuildBossManager gbManager = new GuildBossManager("https://docs.google.com/spreadsheets/d/e/2PACX-1vQACdbvpCIg7Uri2UZ_ZpoPLqEQzB0tWtnf8J8awM7s7DwvZQkoet1V-8TYyEKYPPo_CtU4QdtQDHxo/pub?gid=1714212025&single=true&output=csv");
        gbManager.load();
        talentManager.load();
        System.out.println(talentManager.getTalent("blue moon rising").getFourSpirit().getDamage(7));
        System.out.println(gbManager.estimatedDamage(2188377,2848499,1521377,1650945,300,1.16,1.25,2.675)/1000000000);
        MainUI ui = new MainUI(config);
        ui.startAnalyzer();

    }
}
