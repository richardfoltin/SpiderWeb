/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.panel;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author pokemonterkep
 */
public abstract class SpiderPanel extends JPanel {
    

    public static final int PANEL_HEIGHT = 500;
    public static final int SIDE_MARGIN = 50;
    
    public static final int HEADER_PADDING = 20;

    protected JPanel textArea;
    
    public SpiderPanel() {
        
        setLayout(new BorderLayout());
                
        textArea = new JPanel();
        add(textArea,BorderLayout.CENTER);
        
        textArea.setLayout(new BorderLayout());
        textArea.setBorder(new EmptyBorder(HEADER_PADDING,SIDE_MARGIN,0,SIDE_MARGIN));
    }
    
}
