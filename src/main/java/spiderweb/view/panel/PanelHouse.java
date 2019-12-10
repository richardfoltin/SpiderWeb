/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import spiderweb.Resource;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.view.MainWindow;
import static spiderweb.view.MainWindow.WINDOW_WIDTH;
import spiderweb.view.constant.SpiderFont;
import spiderweb.view.table.SpiderTable;
import spiderweb.view.table.TableHouseAlliances;
import spiderweb.view.table.TableHouseCharacters;

/**
 *
 * @author pokemonterkep
 */
public class PanelHouse extends SpiderActionPanel {

    private static final int SMALL_TABLE_WIDTH = 300;
    private static final int SMALL_TABLE_HEIGHT = 100;
    private static final int INFO_HEIGHT = 200;
    private static final int CREST_WIDTH = 100;
    private static final int CREST_HEIGHT = 100;
    
    private SpiderTable charactersTable;
    private SpiderTable alliancesTable;
    private JScrollPane charactersPane;
    private JScrollPane alliancesPane;
    private House house;
    
    public PanelHouse(House house) {
        super(house.getName(), "Add Alliance");
        
        this.house = house;
        
        try {
            charactersTable = new TableHouseCharacters(house);
            alliancesTable = new TableHouseAlliances(house);
        } catch (SpiderReadException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
        
        charactersPane = addScrollPane(charactersTable);   
        charactersPane.setPreferredSize(new Dimension(SMALL_TABLE_WIDTH,SMALL_TABLE_HEIGHT));
        alliancesPane = addScrollPane(alliancesTable);
        alliancesPane.setPreferredSize(new Dimension(SMALL_TABLE_WIDTH,SMALL_TABLE_HEIGHT));

        textArea.add(charactersPane, BorderLayout.LINE_START);
        textArea.add(alliancesPane, BorderLayout.LINE_END);
        
        JPanel infoArea = new JPanel();
        infoArea.setLayout(new BoxLayout(infoArea, BoxLayout.LINE_AXIS));
        infoArea.setPreferredSize(new Dimension(WINDOW_WIDTH,INFO_HEIGHT));
        textArea.add(infoArea, BorderLayout.PAGE_START);
        
        infoArea.add(Box.createRigidArea(new Dimension(100,0)));
        
        ImageIcon crest = house.getCrest();
        crest = Resource.getScaledImage(crest, CREST_WIDTH, CREST_HEIGHT);
        JLabel crestImage = new JLabel(crest, JLabel.RIGHT);
        crestImage.setPreferredSize(new Dimension(CREST_WIDTH,CREST_HEIGHT));
        infoArea.add(crestImage);  
        
        infoArea.add(Box.createRigidArea(new Dimension(100,0)));
        
        //JTextArea mottoLabel = new JTextArea (house.getMotto());
        //mottoLabel.setLineWrap(true);
        //mottoLabel.setOpaque(false);
        JLabel mottoLabel = new JLabel ("<html>" + house.getMotto() + "</html>");
        mottoLabel.setAlignmentY(CENTER_ALIGNMENT);
        mottoLabel.setPreferredSize(new Dimension(200, INFO_HEIGHT));
        mottoLabel.setFont(SpiderFont.MOTTO);
        infoArea.add(mottoLabel);
    }
    
    private void refreshAlliancesTable() {
        
        SpiderTable newAlliancesTable;
        
        try {
            newAlliancesTable = new TableHouseAlliances(house);
        } catch (SpiderReadException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            return;
        }
        
        changeTableOnScrollPane(alliancesPane, this.alliancesTable, newAlliancesTable);
        this.alliancesTable = newAlliancesTable;
    }
    
    
    @Override
    protected ActionListener backAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().showHouses();
        };
    }

    @Override
    protected ActionListener actionAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().addAlliance(house);
            refreshAlliancesTable();
        };
    }
}
