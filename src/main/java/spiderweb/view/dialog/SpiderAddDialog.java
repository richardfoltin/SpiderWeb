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
import javax.swing.JButton;
import javax.swing.JPanel;
import spiderweb.view.MainWindow;
import static spiderweb.view.dialog.SpiderDialog.GRID_HEIGHT;

/**
 *
 * @author pokemonterkep
 */
public abstract class SpiderAddDialog extends SpiderDialog {

    public SpiderAddDialog(MainWindow frame, String title) {
        super(frame, title);
    }
    
    @Override
    protected void fillBottomPanel() {
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));
        cancelButton.setAlignmentX(CENTER_ALIGNMENT);
        cancelButton.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                SpiderAddDialog.this.setVisible(false);
            } 
        });    
        bottomPanel.add(cancelButton);
        
        JButton okButton = new JButton("Save");
        okButton.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));
        okButton.setAlignmentX(CENTER_ALIGNMENT);
        okButton.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                SpiderAddDialog.this.save();
            } 
        });    
        bottomPanel.add(okButton);
    }
    
    protected JPanel addNewRowWithLabel(String text) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEADING));
        panel.setPreferredSize(new Dimension(DIALOG_WIDTH, GRID_HEIGHT + 5));
        mainPanel.add(panel);
        SpiderDialogLabel label = new SpiderDialogLabel(text);
        panel.add(label);
        return panel;
    }
    
    protected abstract void save();
}
