/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.view.dialog;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import static java.awt.Component.CENTER_ALIGNMENT;

import spiderweb.view.MainWindow;
import static spiderweb.view.dialog.SpiderDialog.GRID_HEIGHT;

/**
 * A programban használt adatmegjelnítő dialógusablakok absztrakt szülőosztálya
 * 
 * @author Foltin Csaba Richárd
 */
public abstract class SpiderDataDialog extends SpiderDialog {

    public SpiderDataDialog(MainWindow frame, String title) {
        super(frame, title);
    }
    
    /**
     * A dialógusablak alsó részének feltöltése a Cancel és a Save gombokkal
     */
    @Override
    protected void fillBottomPanel() {
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));
        cancelButton.setAlignmentX(CENTER_ALIGNMENT);
        cancelButton.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                SpiderDataDialog.this.setVisible(false);
            } 
        });    
        bottomPanel.add(cancelButton);
        
        JButton okButton = new JButton("Save");
        okButton.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));
        okButton.setAlignmentX(CENTER_ALIGNMENT);
        okButton.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                SpiderDataDialog.this.save();
            } 
        });    
        bottomPanel.add(okButton);
    }
    
    /**
     * Új sor hozzáadása az ablakhoz
     * @param text
     * @return 
     */
    protected JPanel addNewRowWithLabel(String text) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEADING));
        panel.setPreferredSize(new Dimension(DIALOG_WIDTH, GRID_HEIGHT + 5));
        mainPanel.add(panel);
        SpiderDialogLabel label = new SpiderDialogLabel(text);
        panel.add(label);
        return panel;
    }
    
    /**
     * A Save gomb megnyomásakor végrehajtott metódus
     */
    protected abstract void save();
}
