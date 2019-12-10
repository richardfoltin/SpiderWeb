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

import spiderweb.entity.Alliance;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.view.MainWindow;
import spiderweb.view.table.TableAlliances;

/**
 * Szövetségeket megjenítő panel
 * 
 * @author Foltin Csaba Richárd
 */
public class PanelAlliances extends SpiderTablePanel {

    public PanelAlliances() {
        super("Alliances", "End Alliance");
                
        try {
            addTable(new TableAlliances());
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
     * Szövetség lezárása
     * 
     * @return 
     */
    @Override
    protected ActionListener actionAction() {
        return (ActionEvent e) -> {
            try {
                Alliance alliance = MainWindow.getInstance().findAlliance(table.getSelectedId());
                
                if (alliance != null) {
                    if (alliance.isClosed()) {
                        MainWindow.getInstance().infoMessage("Alliance is already closed!");
                    } else {
                        MainWindow.getInstance().closeAlliance(alliance);
                        changeTable(new TableAlliances());
                    }
                }
            } catch (Exception ex) {
                MainWindow.getInstance().infoMessage("Alliance is not selected.");
            }
        };
    }
}
