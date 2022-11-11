package org.nuuskapoeka;

import org.nuuskapoeka.domain.Config;
import org.nuuskapoeka.logic.Build;
import org.nuuskapoeka.logic.Copy;
import org.nuuskapoeka.logic.Items;
import org.nuuskapoeka.ui.MainUI;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Config config = new Config("config.txt");

        Build b = new Build(new Items());

        Items i = new Items();
        i.UrlInfo("https://docs.google.com/spreadsheets/d/e/2PACX-1vQACdbvpCIg7Uri2UZ_ZpoPLqEQzB0tWtnf8J8awM7s7DwvZQkoet1V-8TYyEKYPPo_CtU4QdtQDHxo/pub?gid=1493400110&single=true&output=csv");
        
        MainUI ui = new MainUI(config);
        ui.startAnalyzer();

    }
}
