/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.view.panel;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import spiderweb.view.table.SpiderTable;
import static spiderweb.view.panel.SpiderActionPanel.FOOTER_PADDING;
import static spiderweb.view.panel.SpiderPanel.HEADER_PADDING;
import static spiderweb.view.panel.SpiderPanel.SIDE_MARGIN;

/**
 * Az ablakban használt nagy táblát tartalmazó panel absztrakt szülőosztálya
 * 
 * @author Foltin Csaba Richárd
 */
public abstract class SpiderTablePanel extends SpiderActionPanel {

    protected SpiderTable table;
    private JScrollPane scrollPane;
    
    public SpiderTablePanel(String title, String actionButtonText)  {
        super(title, actionButtonText);
        
        textArea.setBorder(new EmptyBorder(HEADER_PADDING,SIDE_MARGIN,0,SIDE_MARGIN*3));
        footer.setBorder(new EmptyBorder(FOOTER_PADDING,SIDE_MARGIN,FOOTER_PADDING,SIDE_MARGIN*3));
    }

    /**
     * Tábla hozzáadása
     * 
     * @param table 
     */
    protected final void addTable(SpiderTable table) {
        this.table = table;
        this.scrollPane = addScrollPane(table);
        textArea.add(scrollPane, BorderLayout.CENTER);
    }   
    
    /**
     * Tábla kicserélése (frissítése)
     * 
     * @param newTable 
     */
    protected void changeTable(SpiderTable newTable) {
        changeTableOnScrollPane(scrollPane, this.table, newTable);
        this.table = newTable;
    }
}
