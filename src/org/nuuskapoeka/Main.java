package org.nuuskapoeka;

import org.nuuskapoeka.domain.Config;
import org.nuuskapoeka.logic.Copy;
import org.nuuskapoeka.ui.MainUI;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Config config = new Config("config.txt");

        MainUI ui = new MainUI(config);
        ui.start();

    }
}
