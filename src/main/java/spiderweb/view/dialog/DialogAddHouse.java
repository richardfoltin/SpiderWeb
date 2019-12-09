/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.dialog;

import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import spiderweb.jdbcdao.dbexception.SpiderImageException;
import spiderweb.jdbcdao.dbexception.SpiderWriteException;
import spiderweb.view.MainWindow;

/**
 *
 * @author pokemonterkep
 */

public final class DialogAddHouse extends SpiderDialog {
    
    private SpiderTextField nameField;
    private SpiderTextField mottoField;
    private SpiderDialogLabel crestField;
    private File crest;
    
    public DialogAddHouse(MainWindow frame){
        super(frame, "Add House");   
    }

    @Override
    protected void fillMainPanel() {
        
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        // name
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        namePanel.setPreferredSize(new Dimension(DIALOG_WIDTH, GRID_HEIGHT + 5));
        mainPanel.add(namePanel);
        SpiderDialogLabel nameLabel = new SpiderDialogLabel("House Name:");
        namePanel.add(nameLabel);
        nameField = new SpiderTextField();
        namePanel.add(nameField);
        
        // motto
        JPanel mottoPanel = new JPanel();
        mottoPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        mottoPanel.setPreferredSize(new Dimension(DIALOG_WIDTH, GRID_HEIGHT + 5));
        mainPanel.add(mottoPanel);
        SpiderDialogLabel mottoLabel = new SpiderDialogLabel("House Motto:");
        mottoPanel.add(mottoLabel);
        mottoField = new SpiderTextField();
        mottoPanel.add(mottoField);
        
        // crest
        JPanel crestPanel = new JPanel();
        crestPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        crestPanel.setPreferredSize(new Dimension(DIALOG_WIDTH, GRID_HEIGHT + 5));
        mainPanel.add(crestPanel);
        SpiderDialogLabel crestLabel = new SpiderDialogLabel("House Crest:");
        crestPanel.add(crestLabel);
        
        crestField = new SpiderDialogLabel("");
        crestPanel.add(crestField);
        crestField.setPreferredSize(new Dimension(FIELD_WIDTH, GRID_HEIGHT));
        
        JButton browseButton = new JButton("Browse");
        browseButton.setPreferredSize(new Dimension(BUTTON_WIDTH,GRID_HEIGHT));
        browseButton.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                JFileChooser fc = new JFileChooser();
                FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
                fc.setFileFilter(imageFilter);
                
                int returnVal = fc.showOpenDialog(DialogAddHouse.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    crest = fc.getSelectedFile();
                    crestField.setText(crest.getName());
                } 
            } 
        });    
        crestPanel.add(browseButton);
    }

    @Override
    protected void fillBottomPanel() {
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));
        cancelButton.setAlignmentX(CENTER_ALIGNMENT);
        cancelButton.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                DialogAddHouse.this.setVisible(false);
            } 
        });    
        bottomPanel.add(cancelButton);
        
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));
        okButton.setAlignmentX(CENTER_ALIGNMENT);
        okButton.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                DialogAddHouse.this.save();
            } 
        });    
        bottomPanel.add(okButton);
    }
    
    protected void save() {
        String name = nameField.getText();
        String motto = mottoField.getText();
        String crestPath = crest.getPath();
        
        if ("".equals(name)) {
            window.errorMessage("Name is empty.");
            return;
        }
        
        if ("".equals(motto)) {
            window.errorMessage("Motto is empty.");
            return;
        }
        
        if ("".equals(crestPath)) {
            window.errorMessage("Crest is not selected.");
            return;
        }
        
        try {
            window.getModel().addNewHouse(name, motto, crestPath);
        } catch (SpiderImageException ex) {
            window.errorMessage("Can't load image: " + crestPath);
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return;
        } catch (SpiderWriteException ex) {
            window.errorMessage("Can't save to database!");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return;
        }
        
        this.setVisible(false);
        window.infoMessage("House saved.");
    }
    
    
}
