/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import javax.swing.*;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.border.EmptyBorder;
import spiderweb.Resource;
import spiderweb.entity.Alliance;
import spiderweb.entity.House;
import spiderweb.entity.Character;
import spiderweb.jdbcdao.dbexception.*;
import spiderweb.view.panel.*;
import spiderweb.view.constant.SpiderFont;
import spiderweb.view.constant.SpiderColor;
import spiderweb.model.SpiderWebModel;
import spiderweb.view.dialog.DialogAddAlliance;
import spiderweb.view.dialog.DialogAddCharacter;
import spiderweb.view.dialog.DialogAddHouse;
import spiderweb.view.dialog.DialogModifyAlliance;
import spiderweb.view.dialog.DialogModifyCharacter;

/**
 *
 * @author eandgna
 */
public class MainWindow extends JFrame {
    private static MainWindow instance = null;  
    
    private SpiderWebModel model;

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
        
    private SpiderPanel activePanel;
    
    public static MainWindow getInstance() {
        if (instance == null) instance = new MainWindow();
        return instance;
    }
    
    private MainWindow(){
        super("SpiderWeb");
        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                showExitConfirmation();
            }
        });
        
        setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT)); 
        setLayout(new BorderLayout());
        
        JLabel header = new JLabel("SPIDERWEB");
        header.setOpaque(true);
        header.setBackground(SpiderColor.HIGHTLIGHT);
        header.setForeground(SpiderColor.WHITE);
        header.setFont(SpiderFont.HEADER);
        header.setVerticalAlignment(JLabel.CENTER);
        header.setVerticalTextPosition(JLabel.CENTER);
        header.setBorder(new EmptyBorder(5, SpiderPanel.SIDE_MARGIN, 5, SpiderPanel.SIDE_MARGIN));
        add(header, BorderLayout.PAGE_START);
        
        
        setResizable(false);
        setVisible(true);
    }
    
    public void showMenu() {
        changePanel(new PanelMenu());
    }
    
    public void showHouses() {
        changePanel(new PanelHouses());
    }
    
    public void showCharacters() {
        changePanel(new PanelCharacters());
    }
    
    public void showAlliances() {
        changePanel(new PanelAlliances());
    }
    
    public void showHouse(House house) {
        changePanel(new PanelHouse(house));
    }
    
    public void showCharacter(Character character) {
        changePanel(new PanelCharacter(character));
    }
        
    private void changePanel(SpiderPanel panel) {
        if (activePanel != null) remove(activePanel);
        
        add(panel, BorderLayout.CENTER);
        activePanel = panel;
        pack();
        centerWindow();
    }
    
    public House findHouse(int id) {
        try {
            return model.getHouse(id);
        } catch (SpiderReadException ex) {
            errorMessage("Can't find house: " + id);
        }
        return null;
    }
    
    public House findHouse(String houseName){
        try {
            return model.getHouse(houseName);
        } catch (SpiderReadException ex) {
            errorMessage("Can't find house: " + houseName);
        }
        return null;
    }
    
    public Character findCharacter(int id) {
        try {
            return model.getCharacter(id);
        } catch (SpiderReadException ex) {
            errorMessage("Can't find character: " + id);
        }
        return null;
    }
    
    public Character findCharacter(String characterName) {
        try {
            return model.getCharacter(characterName);
        } catch (SpiderReadException ex) {
            errorMessage("Can't find character: " + characterName);
        }
        return null;
    }
    
    public Alliance findAlliance(int id) {
        try {
            return model.getAlliance(id);
        } catch (SpiderReadException ex) {
            errorMessage("Can't find alliance: " + id);
        }
        return null;
    }
        
    public void addHouse() {
        DialogAddHouse dialog = new DialogAddHouse(this);
        dialog.showDialog();
    }
    
    public void addCharacter() {
        DialogAddCharacter dialog = new DialogAddCharacter(this);
        dialog.showDialog();
    }
    
    public void modifyCharacter(Character character) {
        DialogModifyCharacter dialog = new DialogModifyCharacter(this, character);
        dialog.showDialog();
    }
    
    public void addAlliance(House house) {
        DialogAddAlliance dialog = new DialogAddAlliance(this);
        if (house != null) dialog.setInitialHouse(house);
        dialog.showDialog();
    }
    
    public void closeAlliance(Alliance alliance){
        DialogModifyAlliance dialog = new DialogModifyAlliance(this, alliance);
        dialog.showDialog();
    }
    
    public void errorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
    
    public void infoMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
    
    public void setModel(SpiderWebModel model) {
        this.model = model;
    }
    
    public SpiderWebModel getModel() {
        return model;
    }
    
    protected void showExitConfirmation() {
        JOptionPane opt = new JOptionPane(new JLabel("Are you sure you want to exit?",JLabel.CENTER),JOptionPane.PLAIN_MESSAGE,JOptionPane.YES_NO_OPTION);
        Dialog dialog = opt.createDialog(this, "Confirmation");
        dialog.setModal(true);
        dialog.setVisible(true);
        
        if (opt.getValue() != null && (Integer) opt.getValue() == JOptionPane.YES_OPTION)
            System.exit(0);
    }
    
    private void centerWindow() {
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;  
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()-60) / 2;  
  
        this.setLocation(x, y);  
    }

}
