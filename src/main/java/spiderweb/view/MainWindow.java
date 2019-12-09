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
    
    public void findHouse(int id) {
        try {
            House house = model.getHouse(id);
            showHouse(house);
        } catch (SpiderReadException ex) {
            System.err.println("Can't find house: " + id);
            ex.printStackTrace();
        }
    }
    
    public void findHouse(String houseName){
        try {
            House house = model.getHouse(houseName);
            showHouse(house);
        } catch (SpiderReadException ex) {
            System.err.println("Can't find house: " + houseName);
            ex.printStackTrace();
        }
    }
    
    public void findCharacter(int id) {
        try {
            Character character = model.getCharacter(id);
            showCharacter(character);
        } catch (SpiderReadException ex) {
            System.err.println("Can't find character: " + id);
            ex.printStackTrace();
        }
    }
    
    public void findCharacter(String characterName) {
        try {
            Character character = model.getCharacter(characterName);
            showCharacter(character);
        } catch (SpiderReadException ex) {
            System.err.println("Can't find character: " + characterName);
            ex.printStackTrace();
        }
    }
    
    public void addHouse() {
        DialogAddHouse dialog = new DialogAddHouse(this);
        dialog.showDialog();
    }
    
    public void addCharacter() {
        DialogAddCharacter dialog = new DialogAddCharacter(this);
        dialog.showDialog();
    }
    
    public void addAlliance(House house) {
        DialogAddAlliance dialog = new DialogAddAlliance(this);
        if (house != null) dialog.setInitialHouse(house);
        dialog.showDialog();
    }
    
    public void closeAlliance(int id){
        try {
            Alliance alliance = model.getAlliance(id);
            model.allianceEnd(alliance, LocalDate.MAX);
        } catch (SpiderReadException | SpiderImageException | SpiderWriteException ex) {
            System.err.println("Can't close alliance: " + id);
            ex.printStackTrace();
        }
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
