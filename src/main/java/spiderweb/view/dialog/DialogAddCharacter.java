/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.dialog;

import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import spiderweb.jdbcdao.dbexception.SpiderImageException;
import spiderweb.jdbcdao.dbexception.SpiderWriteException;
import spiderweb.view.MainWindow;
import spiderweb.entity.Character.Status;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;

/**
 *
 * @author pokemonterkep
 */
public class DialogAddCharacter extends SpiderAddDialog {
    
    private SpiderTextField nameField;
    private SpiderTextField armyField;
    private JCheckBox statusBox;
    private SpiderHouseList houseList;
    
    public DialogAddCharacter(MainWindow frame){
        super(frame, "Add Character");   
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

    
    protected void save() {
        String name = nameField.getText();
        Status status = statusBox.isSelected() ? Status.Deceased : Status.Living;
        
        if ("".equals(name)) {
            window.errorMessage("Name is empty.");
            return;
        }
        
        Integer armySize = 0;
        try {
            armySize = Integer.parseInt(armyField.getText());
        } catch (Exception ex) {
            window.errorMessage("Army size is not numeric.");
            return;
        }
        
        if (status == Status.Deceased) {
            armySize = 0;
        }
        
        House house = houseList.getSelectedHouse();
        
        try {
            window.getModel().addNewCharacter(name, armySize, status, house);
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
