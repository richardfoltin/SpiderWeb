/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.dialog;

import java.awt.FlowLayout;
import java.time.LocalDate;
import javax.swing.JPanel;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderImageException;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.jdbcdao.dbexception.SpiderWriteException;
import spiderweb.view.MainWindow;

/**
 *
 * @author pokemonterkep
 */
public class DialogAddAlliance extends SpiderAddDialog {
    
    private SpiderHouseList houseList1;
    private SpiderHouseList houseList2;
    private SpiderTextField startField;
    private SpiderTextField endField;
    
    public DialogAddAlliance(MainWindow frame){
        super(frame, "Add Alliance");   
    }
    
    @Override
    protected void fillMainPanel() {
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        // houses
        JPanel housePanel1 = addNewRowWithLabel("House 1:");
        JPanel housePanel2 = addNewRowWithLabel("House 2:");
        
        try {
            houseList1 = new SpiderHouseList(window, false);
            housePanel1.add(houseList1);
            houseList2 = new SpiderHouseList(window, false);
            housePanel2.add(houseList2);
        } catch (SpiderReadException ex) {
            window.errorMessage("Can't find any houses in the database.");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            this.setVisible(false);
            return;
        }
        
        // dates
        JPanel startPanel = addNewRowWithLabel("Start Date:");
        startField = new SpiderTextField();
        startPanel.add(startField);
        
        JPanel endPanel = addNewRowWithLabel("End Date:");
        endField = new SpiderTextField();
        endPanel.add(endField);
    }

    protected void save() {
        
        House house1 = houseList1.getSelectedHouse();
        House house2 = houseList2.getSelectedHouse();
        
        if (house1.getId().intValue() == house2.getId().intValue()) {
            window.errorMessage("A house cannot ally with itself!");
            return;
        }
        
        
        LocalDate start_date = null;
        LocalDate end_date = null;
        
        try {
            start_date = LocalDate.parse(startField.getText());
            
            if (!endField.getText().equals("")) {
                end_date = LocalDate.parse(endField.getText());
            }
        } catch (Exception ex) {
            window.errorMessage("Not valid date!");
            return;
        }
        
        if (start_date.isAfter(end_date)) {
            window.errorMessage("End Date should be before Start Date!");
            return;
        }

        try {
            window.getModel().addAlliance(house1, house2, start_date, end_date);
        } catch (SpiderWriteException | SpiderImageException ex) {
            window.errorMessage("Can't save to database!");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return;
        }
        
        this.setVisible(false);
        window.infoMessage("Alliance added.");
    }
    
    public void setInitialHouse(House house) {
        houseList1.selectHouse(house);
    }
    
}
