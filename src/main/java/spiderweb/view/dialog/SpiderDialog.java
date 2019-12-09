/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.dialog;

/**
 *
 * @author pokemonterkep
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import spiderweb.view.MainWindow;
import spiderweb.view.constant.SpiderFont;

public abstract class SpiderDialog extends JDialog {
 
    protected final int DIALOG_WIDTH = 400;
    protected final int DIALOG_HEIGHT = 200;
    protected final int DIALOG_BORDER = 10;
    protected final int BUTTON_HEIGHT = 26;
    protected final int BUTTON_WIDTH = 100;
    protected final int IMAGE_SIZE = 32;
    protected final int ROW_HEIGHT = 28;
    
    protected static final int GRID_HEIGHT = 24;
    protected static final int LABEL_WIDTH = 100;
    protected static final int FIELD_WIDTH = 160;
    
    protected MainWindow window;
    protected JPanel mainPanel;
    protected JPanel bottomPanel;
    
    public SpiderDialog(MainWindow frame, String title) {
        super(frame,title,true);
        
        this.window = frame;
        this.setVisible(false);
        setLayout(new BorderLayout());
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(DIALOG_BORDER, DIALOG_BORDER, DIALOG_BORDER, DIALOG_BORDER));
        mainPanel.setPreferredSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT - DIALOG_BORDER - BUTTON_HEIGHT));     
        fillMainPanel();
        add(mainPanel,BorderLayout.CENTER);
        
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, DIALOG_BORDER, 0));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, DIALOG_BORDER, DIALOG_BORDER, DIALOG_BORDER));
        bottomPanel.setPreferredSize(new Dimension(DIALOG_WIDTH, BUTTON_HEIGHT + DIALOG_BORDER));   
        //bottomPanel.setBorder(BorderFactory.createLineBorder(Color.yellow));
        fillBottomPanel();
        add(bottomPanel,BorderLayout.SOUTH);
        
        pack();
        setResizable(false);
    }
    
    protected abstract void fillMainPanel();
    protected abstract void fillBottomPanel();
    
    public void showDialog() {
        Point windowLocation = window.getLocation();
        setLocation(new Point(windowLocation.x + window.getWidth() / 2 - DIALOG_WIDTH/2, windowLocation.y + window.getHeight()/2 - DIALOG_HEIGHT/2)); 
        setVisible(true);
    }

    protected class SpiderDialogLabel extends JLabel {

        public SpiderDialogLabel(String text) {
            super(text);
            setFont(SpiderFont.DIALOG);
            setPreferredSize(new Dimension(LABEL_WIDTH,GRID_HEIGHT));
        }
    }
    
    protected class SpiderTextField extends JTextField {

        public SpiderTextField() {
            super();
            setFont(SpiderFont.DIALOG);
            setPreferredSize(new Dimension(FIELD_WIDTH,GRID_HEIGHT));
        }
    }
    
}

