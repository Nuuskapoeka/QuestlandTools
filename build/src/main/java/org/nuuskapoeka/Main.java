package org.nuuskapoeka;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.nuuskapoeka.domain.Config;
import org.nuuskapoeka.domain.Item;
import org.nuuskapoeka.logic.*;
import org.nuuskapoeka.ui.MainUI;
import org.nuuskapoeka.utils.JsonConverter;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) throws IOException {

        Config config = new Config("config.txt");

        Build b = new Build(new Items());

        //Items i = new Items();
        //i.load();
        //TalentManager talentManager = new TalentManager("https://docs.google.com/spreadsheets/d/e/2PACX-1vQACdbvpCIg7Uri2UZ_ZpoPLqEQzB0tWtnf8J8awM7s7DwvZQkoet1V-8TYyEKYPPo_CtU4QdtQDHxo/pub?gid=851318924&single=true&output=csv");
        //GuildBossManager gbManager = new GuildBossManager("https://docs.google.com/spreadsheets/d/e/2PACX-1vQACdbvpCIg7Uri2UZ_ZpoPLqEQzB0tWtnf8J8awM7s7DwvZQkoet1V-8TYyEKYPPo_CtU4QdtQDHxo/pub?gid=1714212025&single=true&output=csv");
        //DailyBossManager dbManager = new DailyBossManager();

        MainUI ui = new MainUI(config);
        ui.startBuilder();

    }
}
