/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import spiderweb.view.MainWindow;
import spiderweb.view.button.SpiderBigButton;
import spiderweb.view.button.SpiderSmallButton;

/**
 *
 * @author pokemonterkep
 */
public class PanelMenu extends SpiderPanel {
    
    private static final int PADDINGBIG = 32;
    private static final int PADDING = 5;
    
    public PanelMenu() {
        super();
                
        textArea.setLayout(new BoxLayout(textArea, BoxLayout.PAGE_AXIS));
        //textArea.setPreferredSize(new Dimension(SpiderButton.BIGBUTTON_WIDTH + SIDE_MARGIN, PANEL_HEIGHT));
        
        textArea.add(Box.createRigidArea(new Dimension(0,PADDINGBIG)));
        
        JButton housesButton = new SpiderBigButton("Houses");
        textArea.add(housesButton);
        housesButton.setAlignmentX(RIGHT_ALIGNMENT);
        housesButton.addActionListener(housesAction());
        textArea.add(Box.createRigidArea(new Dimension(0,PADDING)));
        
        JButton housesFindButton = new SpiderSmallButton("Find");
        textArea.add(housesFindButton);
        housesFindButton.setAlignmentX(RIGHT_ALIGNMENT);
        housesFindButton.addActionListener(housesFindAction());
        textArea.add(Box.createRigidArea(new Dimension(0,PADDING)));
        
        JButton housesAddButton = new SpiderSmallButton("Add");
        textArea.add(housesAddButton);
        housesAddButton.setAlignmentX(RIGHT_ALIGNMENT);
        housesAddButton.addActionListener(housesAddAction());
        textArea.add(Box.createRigidArea(new Dimension(0,PADDINGBIG)));
        
        JButton charactersButton = new SpiderBigButton("Characters");
        textArea.add(charactersButton);
        charactersButton.setAlignmentX(RIGHT_ALIGNMENT);
        charactersButton.addActionListener(charactersAction());
        textArea.add(Box.createRigidArea(new Dimension(0,PADDING)));
        
        JButton charactersFindButton = new SpiderSmallButton("Find");
        textArea.add(charactersFindButton);
        charactersFindButton.setAlignmentX(RIGHT_ALIGNMENT);
        charactersFindButton.addActionListener(charactersFindAction());
        textArea.add(Box.createRigidArea(new Dimension(0,PADDING)));
        
        JButton charactersAddButton = new SpiderSmallButton("Add");
        textArea.add(charactersAddButton);
        charactersAddButton.setAlignmentX(RIGHT_ALIGNMENT);
        charactersAddButton.addActionListener(charactersAddAction());
        textArea.add(Box.createRigidArea(new Dimension(0,PADDINGBIG)));
        
        JButton alliancesButton = new SpiderBigButton("Alliances");
        textArea.add(alliancesButton);
        alliancesButton.setAlignmentX(RIGHT_ALIGNMENT);
        alliancesButton.addActionListener(allianceAction());
        textArea.add(Box.createRigidArea(new Dimension(0,PADDING)));
        
    }
    
    private void findHousePanel() {
        String name = JOptionPane.showInputDialog(this, "House Name", "Find House", JOptionPane.QUESTION_MESSAGE);
        
        if (name != null) {
            MainWindow.getInstance().findHouse(name);
        }
    }
    
    private void findCharacterPanel() {
        String name = JOptionPane.showInputDialog(this, "Character Name", "Find Character", JOptionPane.QUESTION_MESSAGE);
        
        if (name != null) {
            MainWindow.getInstance().findCharacter(name);
        }   
    }
    
    private ActionListener housesAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().showHouses();
        };
    }
    
    private ActionListener housesFindAction() {
        return (ActionEvent e) -> {
            findHousePanel();
        };
    }
    
    private ActionListener housesAddAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().addHouse();
        };
    }
    
    private ActionListener charactersAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().showCharacters();
        };
    }
    
    private ActionListener charactersFindAction() {
        return (ActionEvent e) -> {
            findCharacterPanel();
        };
    }
    
    private ActionListener charactersAddAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().addCharacter();
        };
    }
    
    private ActionListener allianceAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().showAlliances();
        };
    }
    
}
