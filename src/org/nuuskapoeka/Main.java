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
        //i.UrlInfo("https://storage.googleapis.com/ql-files-eu/330721.bin");
        
        MainUI ui = new MainUI(config);
        ui.startAnalyzer();

    }
}
