/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    private Character character;
    
    public DialogModifyCharacter(MainWindow frame, Character character) {
        super(frame, "Modify Character");
        
        this.character = character;
        setCharacterInfo();
    }
    
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
