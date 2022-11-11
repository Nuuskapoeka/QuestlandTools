package org.nuuskapoeka;

import org.nuuskapoeka.domain.Config;
import org.nuuskapoeka.logic.Build;
import org.nuuskapoeka.logic.Copy;
import org.nuuskapoeka.logic.Items;
import org.nuuskapoeka.sql.SQLDatabaseConnection;
import org.nuuskapoeka.ui.MainUI;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Config config = new Config("config.txt");

        SQLDatabaseConnection dbCon = new SQLDatabaseConnection();

        //dbCon.TestConnection();

        Build b = new Build(new Items());
        
        MainUI ui = new MainUI(config);
        ui.startAnalyzer();

    }
}
