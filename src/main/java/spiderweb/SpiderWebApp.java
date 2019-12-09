/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb;

import spiderweb.model.SpiderWebModel;
import spiderweb.view.MainWindow;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.time.LocalDate;
import spiderweb.entity.House;
import spiderweb.entity.Character;
import spiderweb.jdbcdao.dbexception.SpiderImageException;
import spiderweb.jdbcdao.dbexception.SpiderWriteException;
/**
 *
 * @author eandgna
 */
public class SpiderWebApp {
    
    private static Connection con;
    private static SpiderWebApp app;
    
    private SpiderWebModel model;
    
    private static final String URL = "jdbc:derby://localhost:1527/spiderweb;create=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static final String CREATE_PATH = "init.sql";
    private static final String DROP_PATH = "drop.sql";
    private static final String DELIMITER = "(;(\r)?\n)|(--\n)";
    
    public static void main(String[] args) {
        
        app = new SpiderWebApp();
        fillInitialData(app.model);
        
        MainWindow window = MainWindow.getInstance();
        window.setModel(app.model);
        window.showMenu();
        
    }
    
    private SpiderWebApp() {
        openConnection();
        executeScript(DROP_PATH);
        executeScript(CREATE_PATH);
        model = new SpiderWebModel();
    }
    
    @Override
    protected void finalize() throws Throwable {
       super.finalize();
       closeConnection();
    }
    
    public static Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) openConnection();
        return con;
    }
    
    private static void openConnection() {
        System.out.println("openConnection");
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection(URL, USER , PASSWORD);
            con.setAutoCommit(false);
            System.out.println("Successfully Connected");
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
    }
   
    private static void fillInitialData(SpiderWebModel model) {
        
        try {
            House targaryen = model.addNewHouse("Targaryen", "Fire and Blood", "crest/targaryen.png");
            House stark = model.addNewHouse("Stark", "Winter is Coming", "crest/stark.png");
            House lannister = model.addNewHouse("Lannister", "Hear Me Roar", "crest/lannister.png");
            House tully = model.addNewHouse("Tully", "Family, Duty, Honor", "crest/tully.png");
            House tyrell = model.addNewHouse("Tyrell", "Growing Strong", "crest/tyrell.png");
            House baratheon = model.addNewHouse("Baratheon", "Ours is the Fury", "crest/baratheon.png");
            House martell = model.addNewHouse("Martell", "Unbowed, Unbent, Unbroken", "crest/martell.png");

            model.addAlliance(lannister, baratheon, LocalDate.now());
            model.addAlliance(martell, lannister, LocalDate.now(), LocalDate.now());
            model.addAlliance(tyrell, lannister, LocalDate.now());
            model.addAlliance(stark, tully, LocalDate.now());

            model.addNewCharacter("Eddard Stark", 0, Character.Status.Deceased, stark);
            model.addNewCharacter("Jon Snow", 12000, Character.Status.Living, null);
            model.addNewCharacter("Sansa Stark", 3500, Character.Status.Living, stark);
            model.addNewCharacter("Joffrey Baratheon", 0, Character.Status.Deceased, lannister);
            model.addNewCharacter("Tyrion Lannister", 6000, Character.Status.Living, lannister);
            model.addNewCharacter("Daenerys Targaryen", 0, Character.Status.Deceased, targaryen);
            model.addNewCharacter("Edmure Tully", 2000, Character.Status.Living, tully);
            model.addNewCharacter("Jamie Lannister", 9000, Character.Status.Living, lannister);
            model.addNewCharacter("Cersei Lannister", 15, Character.Status.Living, lannister);
            model.addNewCharacter("Khal Drogo", 0, Character.Status.Deceased, null);
            model.addNewCharacter("Margaery Tyrell", 0, Character.Status.Deceased, tyrell);
            model.addNewCharacter("Renly Baratheon", 0, Character.Status.Deceased, baratheon);
            model.addNewCharacter("Stannis Baratheon", 0, Character.Status.Deceased, baratheon);
            model.addNewCharacter("Robert Baratheon", 0, Character.Status.Deceased, baratheon);
            model.addNewCharacter("Jorah Mormont", 0, Character.Status.Living, null);
            model.addNewCharacter("Rhaegar Targaryen", 0, Character.Status.Deceased, targaryen);
            model.addNewCharacter("Oberyn Martell", 0, Character.Status.Deceased, martell);
            
        } catch (SpiderImageException | SpiderWriteException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void executeScript(String path) {
        System.out.println("executeScript " + path);
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream(new File(path)));
            scanner.useDelimiter(DELIMITER);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        
        try(Statement st = con.createStatement()){
            while (scanner.hasNext()) {
                String line = scanner.next();
                if (line.contains(";")) {
                    line = line.replace(";", "");
                }
                st.execute(line);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
   
    private void closeConnection() {
        System.out.println("closeConnection");
        try {
            if ((con != null) && !con.isClosed()) {
                con.close();
                System.out.println("Successfully closed");
                con = null;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
