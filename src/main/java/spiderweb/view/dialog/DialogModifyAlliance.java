/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.view.dialog;

import spiderweb.entity.Alliance;
import spiderweb.jdbcdao.dbexception.SpiderImageException;
import spiderweb.jdbcdao.dbexception.SpiderWriteException;
import spiderweb.view.MainWindow;

/**
 *
 * @author pokemonterkep
 */
public class DialogModifyAlliance extends DialogAddAlliance {

    private final Alliance alliance;
    
    public DialogModifyAlliance(MainWindow frame, Alliance alliance) {
        super(frame, "End Alliance");
        
        this.alliance = alliance;
        setCharacterInfo();
    }
    
    /**
     * Az ablak mezőinek kitöltése módosítandó szövetség adatival
     */
    private void setCharacterInfo() {
        
        houseList1.selectHouse(alliance.getHouse1());        
        houseList2.selectHouse(alliance.getHouse2());
        startField.setText(alliance.getStartDate().toString());
        
        houseList1.setEnabled(false);
        houseList2.setEnabled(false);
        startField.setEditable(false);
    }
    
    /**
     * Az ablak adatainak adatbázisba mentése
     */
    @Override
    protected void save() {
        
        Alliance updatedAlliance = collectData();
        if (updatedAlliance == null) return;
        updatedAlliance.setId(alliance.getId());
        
        try {
            window.getModel().endAlliance(updatedAlliance, updatedAlliance.getEndDate());
        } catch (SpiderWriteException | SpiderImageException ex) {
            window.errorMessage("Can't save to database!");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return;
        }
        
        this.setVisible(false);
        window.infoMessage("Alliance eded.");
    }    
}