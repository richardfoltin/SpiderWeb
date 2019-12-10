/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.view.panel;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Az ablakban használt panelek absztrakt szülőosztálya
 * 
 * @author Foltin Csaba Richárd
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
