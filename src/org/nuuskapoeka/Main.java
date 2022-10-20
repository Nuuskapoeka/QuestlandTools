package org.nuuskapoeka;

import org.nuuskapoeka.domain.Config;
import org.nuuskapoeka.logic.Build;
import org.nuuskapoeka.logic.Copy;
import org.nuuskapoeka.logic.Items;
import org.nuuskapoeka.ui.MainUI;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Config config = new Config("config.txt");


        Build b = new Build(new Items());

        System.out.println(b.getOrbPowerEst(2000,200,8));


        MainUI ui = new MainUI(config);
        ui.startAnalyzer();

    }
}
