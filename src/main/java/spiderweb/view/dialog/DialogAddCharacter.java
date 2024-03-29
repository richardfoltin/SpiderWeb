/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.view.dialog;

import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import spiderweb.jdbcdao.dbexception.SpiderImageException;
import spiderweb.jdbcdao.dbexception.SpiderWriteException;
import spiderweb.view.MainWindow;
import spiderweb.entity.Character.Status;
import spiderweb.entity.Character;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;

/**
 * Karakter hozzáadó dialógusablak
 * 
 * @author Foltin Csaba Richárd
 */
public class DialogAddCharacter extends SpiderDataDialog {
    
    protected SpiderTextField nameField;
    protected SpiderTextField armyField;
    protected JCheckBox statusBox;
    protected SpiderHouseList houseList;
    
    public DialogAddCharacter(MainWindow frame){
        super(frame, "Add Character");   
    }
    
    public DialogAddCharacter(MainWindow frame, String title){
        super(frame, title);   
    }

    @Override
    protected void fillMainPanel() {
        
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        // name
        JPanel namePanel = addNewRowWithLabel("Chacter Name:");
        nameField = new SpiderTextField();
        namePanel.add(nameField);
        
        // army
        JPanel armyPanel = addNewRowWithLabel("Army Size:");
        armyField = new SpiderTextField();
        armyPanel.add(armyField);
        
        // status
        JPanel statusPanel = addNewRowWithLabel("Status:");
        statusBox = new JCheckBox("Dead");
        statusPanel.add(statusBox);
        
        // house
        JPanel housePanel = addNewRowWithLabel("House:");
        
        try {
            houseList = new SpiderHouseList(window, true);
            housePanel.add(houseList);
        } catch (SpiderReadException ex) {
            window.errorMessage("Can't find any houses in the database.");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Az ablak mezői alapján egy Karakter entitás létrehozása
     * 
     * @return 
     */
    protected Character collectData() {
        
        String name = nameField.getText();
        Status status = statusBox.isSelected() ? Status.Deceased : Status.Living;
        
        if ("".equals(name)) {
            window.errorMessage("Name is empty.");
            return null;
        }
        
        Integer armySize = 0;
        try {
            armySize = Integer.parseInt(armyField.getText());
        } catch (Exception ex) {
            window.errorMessage("Army size is not numeric.");
            return null;
        }
        
        if (status == Status.Deceased) {
            armySize = 0;
        }
        
        House house = houseList.getSelectedHouse();
        
        return new Character(name, armySize, status, house);
    }
    
    /**
     * Az ablak adatainak adatbázisba mentése
     */
    @Override
    protected void save() {
        
        Character character = collectData();
        if (character == null) return;
        
        try {
            window.getModel().addNewCharacter(character);
        } catch (SpiderWriteException | SpiderImageException ex) {
            window.errorMessage("Can't save to database!");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return;
        }
        
        this.setVisible(false);
        window.infoMessage("Character saved.");
    }
    
}
