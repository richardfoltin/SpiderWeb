/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.view.MainWindow;
import spiderweb.entity.Character;
import spiderweb.view.table.TableCharacters;

/**
 *
 * @author pokemonterkep
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
    
    @Override
    protected ActionListener backAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().showMenu();
        };
    }

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
