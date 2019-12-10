/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.view.dialog;

import java.awt.FlowLayout;
import java.time.LocalDate;
import javax.swing.JPanel;

import spiderweb.entity.Alliance;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderImageException;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.jdbcdao.dbexception.SpiderWriteException;
import spiderweb.view.MainWindow;

/**
 * Szövetség hozzáadó dialógusablak
 * 
 * @author Foltin Csaba Richárd
 */
public class DialogAddAlliance extends SpiderDataDialog {
    
    protected SpiderHouseList houseList1;
    protected SpiderHouseList houseList2;
    protected SpiderTextField startField;
    protected SpiderTextField endField;
    
    public DialogAddAlliance(MainWindow frame){
        super(frame, "Add Alliance");   
    }
    
    public DialogAddAlliance(MainWindow frame, String title){
        super(frame, title);   
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
    
    /**
     * Az ablak mezői alapján egy Szövetség entitás létrehozása
     * @return 
     */
    protected Alliance collectData() {
        
        House house1 = houseList1.getSelectedHouse();
        House house2 = houseList2.getSelectedHouse();
        
        if (house1.getId().intValue() == house2.getId().intValue()) {
            window.errorMessage("A house cannot ally with itself!");
            return null;
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
            return null;
        }
        
        if (end_date != null && start_date.isAfter(end_date)) {
            window.errorMessage("Alliance end date should be before start date!");
            return null;
        }
        
        return new Alliance(house1, house2, start_date, end_date);
    }
    
    /**
     * Az ablak adatainak adatbázisba mentése
     */
    @Override
    protected void save() {
        
        Alliance alliance = collectData();
        if (alliance == null) return;
        
        try {
            window.getModel().addAlliance(alliance);
        } catch (SpiderWriteException | SpiderImageException ex) {
            window.errorMessage("Can't save to database!");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return;
        }
        
        this.setVisible(false);
        window.infoMessage("Alliance added.");
    }
    
    /**
     * Az ablakban található ház listában az alapértelmezett ház beállítása
     * 
     * @param house 
     */
    public void setInitialHouse(House house) {
        houseList1.selectHouse(house);
        houseList1.setEnabled(false);
    }
    
}
