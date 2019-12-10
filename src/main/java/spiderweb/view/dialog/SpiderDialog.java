/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.view.MainWindow;
import spiderweb.view.constant.SpiderFont;

/**
 * A programban használt dialógusablakok absztrakt szülőosztálya
 * 
 * @author Foltin Csaba Richárd
 */
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
    
    /**
     * Az dialógusablak felső terülétét kitöltő metódus
     */
    protected abstract void fillMainPanel();
    
    /**
     * Az dialógusablak alső területét kitöltő metódus
     */
    protected abstract void fillBottomPanel();
    
    /**
     * Az dialógusablak megjelenítése
     */
    public void showDialog() {
        Point windowLocation = window.getLocation();
        setLocation(new Point(windowLocation.x + window.getWidth() / 2 - DIALOG_WIDTH/2, windowLocation.y + window.getHeight()/2 - DIALOG_HEIGHT/2)); 
        setVisible(true);
    }

    /**
     * A dialógusablakban használt címkék
     */
    protected class SpiderDialogLabel extends JLabel {

        public SpiderDialogLabel(String text) {
            super(text);
            setFont(SpiderFont.DIALOG);
            setPreferredSize(new Dimension(LABEL_WIDTH,GRID_HEIGHT));
        }
    }
    
    /**
     * A dialógusablakban használt mezők
     */
    protected class SpiderTextField extends JTextField {

        public SpiderTextField() {
            super();
            setFont(SpiderFont.DIALOG);
            setPreferredSize(new Dimension(FIELD_WIDTH,GRID_HEIGHT));
        }
    }
    
    /**
     * A dialógusablakban használt házakat tartalmazó listák
     */
    protected class SpiderHouseList extends JComboBox{

        private final ArrayList<Integer> houseID = new ArrayList();
        private final ArrayList<String> houseNames = new ArrayList();
        private final boolean withNoHouse;
        
        public SpiderHouseList(MainWindow window, boolean withNoHouse) throws SpiderReadException {
            this.withNoHouse = withNoHouse;
            List<House> houses = window.getModel().getAllHouse();

            if (withNoHouse) {
                houseNames.add("No House");
                addItem("No House");
                houseID.add(-1);
            }

            if (houses != null){
                for (House house : houses){
                    houseNames.add(house.getName());
                    addItem(house.getName());
                    houseID.add(house.getId());
                }
            }
            
            setPreferredSize(new Dimension(FIELD_WIDTH,GRID_HEIGHT));
        }

        /**
         * Az adott ház kiválasztása a listában
         * @param house 
         */
        public void selectHouse(House house) {
            if (house == null) {
                setSelectedIndex(0);
                return;
            }
            
            int i;
            for (i = 0; i < houseID.size(); i++) {
                if (house.getId().intValue() == houseID.get(i)) break;
            }
            setSelectedIndex(i);
        }
        
        /**
         * A kiválasztott ház
         * @return 
         */
        public House getSelectedHouse() {
            int selectedHouse = getSelectedIndex();
            if (withNoHouse && selectedHouse == 0) return null;
            return new House(houseNames.get(selectedHouse), houseID.get(selectedHouse));
        }

    }
    
}

