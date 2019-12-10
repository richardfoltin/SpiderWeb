/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.view.dialog;

import spiderweb.view.MainWindow;
import spiderweb.entity.Character;

import spiderweb.jdbcdao.dbexception.SpiderImageException;
import spiderweb.jdbcdao.dbexception.SpiderWriteException;

/**
 *
 * @author pokemonterkep
 */
public class DialogModifyCharacter extends DialogAddCharacter {

    private final Character character;
    
    public DialogModifyCharacter(MainWindow frame, Character character) {
        super(frame, "Modify Character");
        
        this.character = character;
        setCharacterInfo();
    }
    
    /**
     * Az ablak mezőinek kitöltése módosítandó karakter adatival
     */
    private void setCharacterInfo() {
        
        nameField.setText(character.getName());
        armyField.setText(character.getArmySize().toString());
        statusBox.setSelected(!character.isLiving());
        houseList.selectHouse(character.getHouse());
        
        nameField.setEditable(false);
        
        if (!character.isLiving()) {
            armyField.setEditable(false);
            statusBox.setEnabled(false);
        }
    }
    
    /**
     * Az ablak adatainak adatbázisba mentése
     */
    @Override
    protected void save() {
        
        Character updatedCharacter = collectData();
        if (updatedCharacter == null) return;
        updatedCharacter.setId(character.getId());
        
        try {
            window.getModel().updateCharacter(updatedCharacter);
        } catch (SpiderWriteException | SpiderImageException ex) {
            window.errorMessage("Can't save to database!");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return;
        }
        
        this.setVisible(false);
        window.infoMessage("Character modified.");
    }    
}
