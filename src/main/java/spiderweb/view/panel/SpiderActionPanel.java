/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.view.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.RIGHT_ALIGNMENT;

import spiderweb.view.button.SpiderButton;
import spiderweb.view.constant.SpiderColor;
import spiderweb.view.constant.SpiderFont;
import spiderweb.view.button.SpiderSmallButton;
import spiderweb.view.table.SpiderTable;
import static spiderweb.view.panel.SpiderPanel.SIDE_MARGIN;
import static spiderweb.view.MainWindow.WINDOW_WIDTH;

/**
 * Az ablakban használt panelek alul két gombot tartalmazó absztrakt szülőosztálya
 * 
 * @author Foltin Csaba Richárd
 */
public abstract class SpiderActionPanel extends SpiderPanel {

    public static final int FOOTER_PADDING = 20;
    public static final int FOOTER_HEIGHT = 80;
    
    protected JPanel footer;
    protected JLabel header;
    
    protected SpiderButton backButton;
    protected SpiderButton actionButton;
        
    /**
     * Panel léterhozása a megadott fejléccel, és megadott szövegű jobb oldali gombbal
     * 
     * @param title a panel fejléce
     * @param actionButtonText a jobb oldali gomb szövege
     */
    public SpiderActionPanel(String title, String actionButtonText) {
        super();
        
        header = new JLabel(title.toUpperCase());
        header.setOpaque(true);
        header.setBackground(SpiderColor.HIGHTLIGHT_LOW);
        header.setForeground(SpiderColor.BLACK);
        header.setFont(SpiderFont.HEADER2);
        header.setVerticalAlignment(JLabel.CENTER);
        header.setVerticalTextPosition(JLabel.CENTER);
        header.setBorder(new EmptyBorder(5, SIDE_MARGIN, 5, SIDE_MARGIN));
        add(header, BorderLayout.PAGE_START);

        footer = new JPanel();
        footer.setLayout(new BorderLayout());
        footer.setPreferredSize(new Dimension(WINDOW_WIDTH, FOOTER_HEIGHT));
        add(footer, BorderLayout.PAGE_END);
        footer.setBorder(new EmptyBorder(FOOTER_PADDING,SIDE_MARGIN,FOOTER_PADDING,SIDE_MARGIN));

        backButton = new SpiderSmallButton("Back");
        backButton.setAlignmentX(LEFT_ALIGNMENT);
        backButton.addActionListener(backAction());
        footer.add(backButton, BorderLayout.LINE_START);

        actionButton = new SpiderSmallButton(actionButtonText);
        actionButton.setAlignmentX(RIGHT_ALIGNMENT);
        actionButton.addActionListener(actionAction());
        footer.add(actionButton, BorderLayout.LINE_END);
    }
  
    /**
     * Táblát tartalmazó görgethető terület hozzáadása
     * 
     * @param table
     * @return 
     */
    protected JScrollPane addScrollPane(SpiderTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }    
    
    /**
     * A táblát tartalmzaó görgethető terület táblájának kicserélése (frissítése)
     * 
     * @param scrollPane
     * @param oldTable
     * @param newTable 
     */
    protected void changeTableOnScrollPane(JScrollPane scrollPane, SpiderTable oldTable, SpiderTable newTable) {
        scrollPane.getViewport().remove(oldTable);
        scrollPane.getViewport().add(newTable);
    }
    
    /**
     * A vissza gomb hatására meghívott metódus
     * @return 
     */
    protected abstract ActionListener backAction();
    
    /**
     * A jobb oldali gomb hatására meghívott metódus
     * @return 
     */
    protected abstract ActionListener actionAction();
}
