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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import spiderweb.Resource;
import spiderweb.jdbcdao.dbexception.SpiderImageException;
import spiderweb.view.MainWindow;
import spiderweb.view.button.SpiderBigButton;
import spiderweb.view.button.SpiderSmallButton;
import static spiderweb.view.panel.SpiderPanel.HEADER_PADDING;
import static spiderweb.view.panel.SpiderPanel.SIDE_MARGIN;

/**
 * A főmenüt megjelenítő panel
 * 
 * @author Foltin Csaba Richárd
 */
public class PanelMenu extends SpiderPanel {
    
    private static final int PADDINGBIG = 32;
    private static final int PADDING = 5;
    
    public PanelMenu() {
        super();
        
        try {
            ImageIcon bg = new ImageIcon(Resource.getBytesFromResource("varys.png"));
            //bg = Resource.getScaledImage(bg, 400, 400);
            JLabel bgImage = new JLabel(bg);
            //bgImage.setPreferredSize(new Dimension(400, 400));
            add(bgImage, BorderLayout.LINE_START); 
        } catch (SpiderImageException ex) {
            System.err.println("Can't find background!");
            ex.printStackTrace();
        }
        
        textArea.setLayout(new BoxLayout(textArea, BoxLayout.PAGE_AXIS));
        textArea.setBorder(new EmptyBorder(HEADER_PADDING,0,0,SIDE_MARGIN));
        //textArea.setPreferredSize(new Dimension(SpiderButton.BIGBUTTON_WIDTH + SIDE_MARGIN, PANEL_HEIGHT));
        
        textArea.add(Box.createRigidArea(new Dimension(0,PADDINGBIG)));
        
        JButton housesButton = addButton("Houses", true);
        housesButton.addActionListener(housesAction());
        textArea.add(Box.createRigidArea(new Dimension(0,PADDING)));
        
        JButton housesFindButton = addButton("Find", false);
        housesFindButton.addActionListener(housesFindAction());
        textArea.add(Box.createRigidArea(new Dimension(0,PADDING)));
        
        JButton housesAddButton = addButton("Add", false);
        housesAddButton.addActionListener(housesAddAction());
        textArea.add(Box.createRigidArea(new Dimension(0,PADDINGBIG)));
        
        JButton charactersButton = addButton("Characters", true);
        charactersButton.addActionListener(charactersAction());
        textArea.add(Box.createRigidArea(new Dimension(0,PADDING)));
        
        JButton charactersFindButton = addButton("Find", false);
        charactersFindButton.addActionListener(charactersFindAction());
        textArea.add(Box.createRigidArea(new Dimension(0,PADDING)));
        
        JButton charactersAddButton = addButton("Add", false);
        charactersAddButton.addActionListener(charactersAddAction());
        textArea.add(Box.createRigidArea(new Dimension(0,PADDINGBIG)));
        
        JButton alliancesButton = addButton("Alliances", true);
        alliancesButton.addActionListener(allianceAction());
        textArea.add(Box.createRigidArea(new Dimension(0,PADDING)));
        
        JButton allianceAddButton = addButton("Add", false);
        allianceAddButton.addActionListener(allianceAddAction());
        textArea.add(Box.createRigidArea(new Dimension(0,PADDINGBIG)));
    }
        
    /**
     * Gomb hozzáadása
     * 
     * @param text a gomb szövege
     * @param big nagy, vagy kis gomb?
     * @return 
     */
    private JButton addButton(String text, boolean big) {
        JButton button = (big) ? new SpiderBigButton(text) : new SpiderSmallButton(text);
        textArea.add(button);
        button.setAlignmentX(RIGHT_ALIGNMENT);
        return button;
    }

    
    /**
     * Házak listáját tartlamzó panelre navigálás
     * @return 
     */
    private ActionListener housesAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().showHouses();
        };
    }
    
    /**
     * Ház keresése
     * 
     * @return 
     */
    private ActionListener housesFindAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().findHouseDialog();
        };
    }
    
    /**
     * Ház hozzáadása
     * 
     * @return 
     */
    private ActionListener housesAddAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().addHouse();
        };
    }
    
    /**
     * Karakterek listáját tartlamzó panelre navigálás
     * 
     * @return 
     */
    private ActionListener charactersAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().showCharacters();
        };
    }
    
    /**
     * Karakter keresése
     * 
     * @return 
     */
    private ActionListener charactersFindAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().findCharacterDialog();
        };
    }
    
    /**
     * Karakter hozzáadása
     * 
     * @return 
     */
    private ActionListener charactersAddAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().addCharacter();
        };
    }
    
    /**
     * Szövetségek listáját tartlamzó panelre navigálás
     * 
     * @return 
     */
    private ActionListener allianceAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().showAlliances();
        };
    }
    
    /**
     * Szövetség hozzáadása
     * 
     * @return 
     */
    private ActionListener allianceAddAction() {
        return (ActionEvent e) -> {
            MainWindow.getInstance().addAlliance(null);
        };
    }
}
