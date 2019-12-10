/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.view;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

import spiderweb.entity.Alliance;
import spiderweb.entity.House;
import spiderweb.entity.Character;
import spiderweb.jdbcdao.dbexception.*;
import spiderweb.view.panel.*;
import spiderweb.view.constant.SpiderFont;
import spiderweb.view.constant.SpiderColor;
import spiderweb.model.SpiderWebModel;
import spiderweb.view.dialog.DialogAddAlliance;
import spiderweb.view.dialog.DialogAddCharacter;
import spiderweb.view.dialog.DialogAddHouse;
import spiderweb.view.dialog.DialogModifyAlliance;
import spiderweb.view.dialog.DialogModifyCharacter;

/**
 * A program ablaka singleton
 * 
 * @author Foltin Csaba Richárd
 */
public class MainWindow extends JFrame {
    private static MainWindow instance = null;  
    
    private SpiderWebModel model;

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
        
    private SpiderPanel activePanel;
    
    public static MainWindow getInstance() {
        if (instance == null) instance = new MainWindow();
        return instance;
    }
    
    private MainWindow(){
        super("SpiderWeb");
        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                showExitConfirmation();
            }
        });
        
        setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT)); 
        setLayout(new BorderLayout());
        
        JLabel header = new JLabel("SPIDERWEB");
        header.setOpaque(true);
        header.setBackground(SpiderColor.HIGHTLIGHT);
        header.setForeground(SpiderColor.WHITE);
        header.setFont(SpiderFont.HEADER);
        header.setVerticalAlignment(JLabel.CENTER);
        header.setVerticalTextPosition(JLabel.CENTER);
        header.setBorder(new EmptyBorder(5, SpiderPanel.SIDE_MARGIN, 5, SpiderPanel.SIDE_MARGIN));
        add(header, BorderLayout.PAGE_START);
        
        
        setResizable(false);
        setVisible(true);
    }
    
    /**
     * A főmenü megjelenítése
     */
    public void showMenu() {
        changePanel(new PanelMenu());
    }
    
    /**
     * A házak megjelenítése
     */
    public void showHouses() {
        changePanel(new PanelHouses());
    }
    
    /**
     * A karakterek megjelenítése
     */
    public void showCharacters() {
        changePanel(new PanelCharacters());
    }
    
    /**
     * A szövetségek megjelenítése
     */
    public void showAlliances() {
        changePanel(new PanelAlliances());
    }
    
    /**
     * A megadott ház adatainak megjelenítése
     * @param house 
     */
    public void showHouse(House house) {
        changePanel(new PanelHouse(house));
    }
    
    /**
     * A megadott karakter adatainak megjelenítése
     * @param character 
     */
    public void showCharacter(Character character) {
        changePanel(new PanelCharacter(character));
    }
        
    /**
     * Az ablakon belöl a megadott képernyőre váltás
     * @param panel 
     */
    private void changePanel(SpiderPanel panel) {
        if (activePanel != null) remove(activePanel);
        
        add(panel, BorderLayout.CENTER);
        activePanel = panel;
        pack();
        centerWindow();
    }
    
    /**
     * Ház megkeresése az adatbázisban kulcs alapján
     * 
     * @param id
     * @return 
     */
    public House findHouse(int id) {
        try {
            return model.getHouse(id);
        } catch (SpiderReadException ex) {
            errorMessage("Can't find house: " + id);
        }
        return null;
    }
    
    /**
     * Ház megkeresése az adatbázisban neve alapján
     * 
     * @param houseName
     * @return 
     */
    public House findHouse(String houseName){
        try {
            return model.getHouse(houseName);
        } catch (SpiderReadException ex) {
            errorMessage("Can't find house: " + houseName);
        }
        return null;
    }
    
    /**
     * Karakter megkeresése az adatbázisban kulcs alapján
     * 
     * @param id
     * @return 
     */
    public Character findCharacter(int id) {
        try {
            return model.getCharacter(id);
        } catch (SpiderReadException ex) {
            errorMessage("Can't find character: " + id);
        }
        return null;
    }
    
    /**
     * Karakter megkeresése az adatbázisban neve alapján
     * 
     * @param characterName
     * @return 
     */
    public Character findCharacter(String characterName) {
        try {
            return model.getCharacter(characterName);
        } catch (SpiderReadException ex) {
            errorMessage("Can't find character: " + characterName);
        }
        return null;
    }
    
    /**
     * Szövetség megkeresése az adatbázisban kulcs alapján
     * 
     * @param id
     * @return 
     */
    public Alliance findAlliance(int id) {
        try {
            return model.getAlliance(id);
        } catch (SpiderReadException ex) {
            errorMessage("Can't find alliance: " + id);
        }
        return null;
    }
        
    /**
     * A ház hozzadása dialógusablak megnyitása
     */
    public void addHouse() {
        DialogAddHouse dialog = new DialogAddHouse(this);
        dialog.showDialog();
    }
    
    /**
     * A karakter hozzadása dialógusablak megnyitása
     */
    public void addCharacter() {
        DialogAddCharacter dialog = new DialogAddCharacter(this);
        dialog.showDialog();
    }
    
    /**
     * A karakter módosítása dialógusablak megnyitása
     * @param character 
     */
    public void modifyCharacter(Character character) {
        DialogModifyCharacter dialog = new DialogModifyCharacter(this, character);
        dialog.showDialog();
    }
    
    /**
     * A szövetség hozzadása dialógusablak megnyitása
     * @param house 
     */
    public void addAlliance(House house) {
        DialogAddAlliance dialog = new DialogAddAlliance(this);
        if (house != null) dialog.setInitialHouse(house);
        dialog.showDialog();
    }
    
    /**
     * A szövetség lezárása dialógusablak megnyitása
     * @param alliance 
     */
    public void closeAlliance(Alliance alliance){
        DialogModifyAlliance dialog = new DialogModifyAlliance(this, alliance);
        dialog.showDialog();
    }
        
    /**
     * Ház keresése dialógusablak megjelenítés
     */
    public void findHouseDialog() {
        String name = JOptionPane.showInputDialog(this, "House Name", "Find House", JOptionPane.QUESTION_MESSAGE);
        
        if (name != null) {
            House house = findHouse(name);
            if (house != null) showHouse(house);
        }
    }
    
    /**
     * Karakter keresése dialógusablak megjelenítése
     */
    public void findCharacterDialog() {
        String name = JOptionPane.showInputDialog(this, "Character Name", "Find Character", JOptionPane.QUESTION_MESSAGE);
        
        if (name != null) {
            Character character = findCharacter(name);
            if (character != null) showCharacter(character);
        }   
    }
    
    /**
     * Hibaüzenet kiírása
     * @param msg 
     */
    public void errorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
    
    /**
     * Információs üzenet kiírása
     * @param msg 
     */
    public void infoMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
    
    public void setModel(SpiderWebModel model) {
        this.model = model;
    }
    
    public SpiderWebModel getModel() {
        return model;
    }
    
    /**
     * Ablak bezárását megerősítő ablak feldobása
     */
    protected void showExitConfirmation() {
        JOptionPane opt = new JOptionPane(new JLabel("Are you sure you want to exit?",JLabel.CENTER),JOptionPane.PLAIN_MESSAGE,JOptionPane.YES_NO_OPTION);
        Dialog dialog = opt.createDialog(this, "Confirmation");
        dialog.setModal(true);
        dialog.setVisible(true);
        
        if (opt.getValue() != null && (Integer) opt.getValue() == JOptionPane.YES_OPTION)
            System.exit(0);
    }
    
    /**
     * Az ablak a képernyő közepére helyezése
     */
    private void centerWindow() {
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;  
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()-60) / 2;  
  
        this.setLocation(x, y);  
    }

}
