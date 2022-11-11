package org.nuuskapoeka.sql;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.net.http.*;

public class SQLDatabaseConnection {
    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public SQLDatabaseConnection(){

    }
    public void Connect() throws FileNotFoundException {
        String connectionUrl = new Scanner(new File("serverConnection.txt")).nextLine();
        try (Connection connection = DriverManager.getConnection(connectionUrl);) {
            // Code here.
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void TestConnection() throws IOException {
        try{
            URL url=new URL("https://docs.google.com/spreadsheets/d/e/2PACX-1vQACdbvpCIg7Uri2UZ_ZpoPLqEQzB0tWtnf8J8awM7s7DwvZQkoet1V-8TYyEKYPPo_CtU4QdtQDHxo/pub?gid=1354238288&single=true&output=csv");
            Scanner scanner = new Scanner(url.openStream());
            while(scanner.hasNextLine()){
                System.out.println(scanner.nextLine());
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}