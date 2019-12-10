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
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import spiderweb.jdbcdao.dbexception.SpiderImageException;
import spiderweb.jdbcdao.dbexception.SpiderWriteException;
import spiderweb.view.MainWindow;

/**
 * Ház hozzáadó dialógusablak
 * 
 * @author Foltin Csaba Richárd
 */
public final class DialogAddHouse extends SpiderDataDialog {
    
    private SpiderTextField nameField;
    private SpiderTextField mottoField;
    private SpiderDialogLabel crestField;
    private File crest;
    
    public DialogAddHouse(MainWindow frame){
        super(frame, "Add House");   
    }

    @Override
    protected void fillMainPanel() {
        
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        // name
        JPanel namePanel = addNewRowWithLabel("House Name:");
        nameField = new SpiderDialog.SpiderTextField();
        namePanel.add(nameField);
        
        // motto
        JPanel mottoPanel = addNewRowWithLabel("House Motto:");
        mottoField = new SpiderDialog.SpiderTextField();
        mottoPanel.add(mottoField);
        
        // crest
        JPanel crestPanel = addNewRowWithLabel("House Crest:");
        crestField = new SpiderDialog.SpiderDialogLabel("");
        crestPanel.add(crestField);
        crestField.setPreferredSize(new Dimension(FIELD_WIDTH, GRID_HEIGHT));
        
        JButton browseButton = new JButton("Browse");
        browseButton.setPreferredSize(new Dimension(BUTTON_WIDTH,GRID_HEIGHT));
        browseButton.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                JFileChooser fc = new JFileChooser();
                FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
                fc.setFileFilter(imageFilter);
                
                int returnVal = fc.showOpenDialog(DialogAddHouse.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    crest = fc.getSelectedFile();
                    crestField.setText(crest.getName());
                } 
            } 
        });    
        crestPanel.add(browseButton);
        
    }
    
    /**
     * Az ablak adatainak adatbázisba mentése
     */
    @Override
    protected void save() {
        String name = nameField.getText();
        String motto = mottoField.getText();
        
        if ("".equals(name)) {
            window.errorMessage("Name is empty.");
            return;
        }
        
        if ("".equals(motto)) {
            window.errorMessage("Motto is empty.");
            return;
        }
        
        if (crest == null || "".equals(crest.getPath())) {
            window.errorMessage("Crest is not selected.");
            return;
        }
        
        try {
            URL crestURL = crest.toURI().toURL();
            window.getModel().addNewHouse(name, motto, crestURL);
        } catch (SpiderImageException | MalformedURLException ex) {
            window.errorMessage("Can't load image: " + crest.getPath());
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return;
        } catch (SpiderWriteException ex) {
            window.errorMessage("Can't save to database!");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return;
        }
        
        this.setVisible(false);
        window.infoMessage("House saved.");
        
    }
    
    
}
