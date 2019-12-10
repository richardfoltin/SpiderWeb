/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.view.MainWindow;
import spiderweb.view.table.TableHouses;

/**
 *
 * @author pokemonterkep
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
                House house = MainWindow.getInstance().findHouse(table.getSelectedId());
                if (house != null) MainWindow.getInstance().showHouse(house);
            } catch (Exception ex) {
                MainWindow.getInstance().infoMessage("House is not selected.");
            }
        };
    }
    
    
}
