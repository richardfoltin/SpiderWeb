/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.panel;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import static spiderweb.view.panel.SpiderActionPanel.FOOTER_PADDING;
import static spiderweb.view.panel.SpiderPanel.HEADER_PADDING;
import static spiderweb.view.panel.SpiderPanel.SIDE_MARGIN;
import spiderweb.view.table.SpiderTable;

/**
 *
 * @author pokemonterkep
 */
public abstract class SpiderTablePanel extends SpiderActionPanel {

    protected SpiderTable table;
    private JScrollPane scrollPane;
    
    public SpiderTablePanel(String title, String actionButtonText)  {
        super(title, actionButtonText);
        
        textArea.setBorder(new EmptyBorder(HEADER_PADDING,SIDE_MARGIN,0,SIDE_MARGIN*3));
        footer.setBorder(new EmptyBorder(FOOTER_PADDING,SIDE_MARGIN,FOOTER_PADDING,SIDE_MARGIN*3));
    }

    protected final void addTable(SpiderTable table) {
        this.table = table;
        this.scrollPane = addScrollPane(table);
        textArea.add(scrollPane, BorderLayout.CENTER);
    }   
    
    protected void changeTable(SpiderTable newTable) {
        changeTableOnScrollPane(scrollPane, this.table, newTable);
        this.table = newTable;
    }
}
