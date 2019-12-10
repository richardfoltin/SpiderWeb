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

import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.view.MainWindow;
import spiderweb.entity.Character;
import spiderweb.view.table.TableCharacters;

/**
 * Karaktereket megjenítő panel
 * 
 * @author Foltin Csaba Richárd
 */
public class PanelCharacters extends SpiderTablePanel {
    
    public PanelCharacters() {
        super("Characters", "View");
                        
        try {
            addTable(new TableCharacters());
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
     * A kiválasztott karakter adatait mutató panelre navigálás
     * 
     * @return 
     */
    @Override
    protected ActionListener actionAction() {
        return (ActionEvent e) -> {
            try {
                Character character = MainWindow.getInstance().findCharacter(table.getSelectedId());
                if (character != null) MainWindow.getInstance().showCharacter(character);
            } catch (Exception ex) {
                MainWindow.getInstance().infoMessage("Character is not selected.");
            }
        };
    }
}
