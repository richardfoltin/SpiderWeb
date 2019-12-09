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
import spiderweb.view.table.TableAlliances;

/**
 *
 * @author pokemonterkep
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
    
    
    @Override
    protected ActionListener backAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().showMenu();
        };
    }

    @Override
    protected ActionListener actionAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().showHouses();
        };
    }
}
