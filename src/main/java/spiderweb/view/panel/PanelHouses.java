/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.view.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.view.MainWindow;
import spiderweb.view.table.TableHouses;

/**
 * Házakat megjenítő panel
 * 
 * @author Foltin Csaba Richárd
 */
public class PanelHouses extends SpiderTablePanel {
    
    public PanelHouses() {
        super("Houses", "View");
        
        try {
            addTable(new TableHouses());
        } catch (SpiderReadException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Visszanavigálás a főmenübe
     * 
     * @return 
     */
    @Override
    protected ActionListener backAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().showMenu();
        };
    }

    /**
     * A kiválasztott ház adatait mutató panelre navigálás
     * 
     * @return 
     */
    @Override
    protected ActionListener actionAction() {
        return (ActionEvent e) -> {
            try {
                House house = MainWindow.getInstance().findHouse(table.getSelectedId());
                if (house != null) MainWindow.getInstance().showHouse(house);
            } catch (Exception ex) {
                MainWindow.getInstance().infoMessage("House is not selected.");
            }
        };
    }
    
    
}
