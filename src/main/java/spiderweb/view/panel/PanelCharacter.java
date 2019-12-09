/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import spiderweb.Resource;
import spiderweb.view.MainWindow;
import spiderweb.entity.Character;
import spiderweb.view.constant.SpiderFont;


/**
 *
 * @author pokemonterkep
 */
public class PanelCharacter extends SpiderActionPanel {

    private static final int CREST_WIDTH = 200;
    private static final int CREST_HEIGHT = 200;
    private static final int GRID_WIDTH = 200;
    private static final int GRID_HEIGHT = 40;
    
    public PanelCharacter(Character character) {
        super(character.getName(), "Modify");
        
        JPanel flowArea = new JPanel();
        flowArea.setLayout(new BorderLayout());
        flowArea.setPreferredSize(new Dimension(GRID_WIDTH*2, GRID_HEIGHT*3));
        textArea.add(flowArea, BorderLayout.LINE_START);
        
        JPanel infoArea = new JPanel();
        flowArea.add(infoArea, BorderLayout.PAGE_END);
        
        infoArea.setLayout(new GridLayout(3,2));
        
        // status
        JLabel statusLabel = new CharacterGridLabel("Status:");
        infoArea.add(statusLabel);
        
        JLabel statusValue = new CharacterGridLabel(character.getStatus().toString());
        infoArea.add(statusValue);
        
        // army size
        JLabel armyLabel = new CharacterGridLabel("Army size:");
        infoArea.add(armyLabel);
        
        JLabel armyValue = new CharacterGridLabel(character.getArmySize().toString());
        infoArea.add(armyValue);
        
        // house
        JLabel houseLabel = new CharacterGridLabel("House:");
        infoArea.add(houseLabel);
        
        JLabel houseValue = new CharacterGridLabel((character.getHouse() == null) ? "No House" : character.getHouse().getName());
        infoArea.add(houseValue);

        // house crest
        if (character.getHouse() != null) {
            ImageIcon crest = character.getHouse().getCrest();
            crest = Resource.getScaledImage(crest, CREST_WIDTH, CREST_HEIGHT);
            JLabel crestImage = new JLabel(crest, JLabel.RIGHT);
            crestImage.setPreferredSize(new Dimension(CREST_WIDTH,CREST_HEIGHT));
            textArea.add(crestImage, BorderLayout.LINE_END);  
        }
     
    }
    
    private class CharacterGridLabel extends JLabel {

        public CharacterGridLabel(String text) {
            super(text);
            
            setFont(SpiderFont.CHARACTER_GRID);
            setPreferredSize(new Dimension(GRID_WIDTH, GRID_HEIGHT));
        }
        
    }
    
    
    @Override
    protected ActionListener backAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().showCharacters();
        };
    }

    @Override
    protected ActionListener actionAction() {
        return (ActionEvent e) -> {
            
        };
    }
}
