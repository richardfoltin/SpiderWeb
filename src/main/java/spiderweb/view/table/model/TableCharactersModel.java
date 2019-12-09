/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.table.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import spiderweb.entity.Character;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.view.MainWindow;

/**
 *
 * @author pokemonterkep
 */
public class TableCharactersModel extends AbstractTableModel  {
    
    private final List<Character> data;
    private final String[] colName = new String[]{"ID", "Name", "Army Size", "Status", "House"}; 

    public TableCharactersModel() throws SpiderReadException {
        this.data = MainWindow.getInstance().getModel().getAllCharacter();
    }
    
    public TableCharactersModel(House house) throws SpiderReadException {
        this.data = MainWindow.getInstance().getModel().getAllCharacter(house);
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return colName.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Character character = data.get(rowIndex);

        switch (columnIndex) {
            case 0 : return character.getId();
            case 1 : return character.getName(); 
            case 2 : return character.getArmySize();
            case 3 : return character.getStatus().toString();
            case 4 : return (character.hasHouse()) ? character.getHouse().getName() : null;
            default: return null;
        }
    }

    @Override
    public String getColumnName(int i) { 
        return colName[i]; 
    }

    @Override
    public Class getColumnClass(int c) {
        for (int i = 0; i < getRowCount(); ++i) {
            if (getValueAt(i, c) != null) 
                return getValueAt(i, c).getClass();
        }
        
        return String.class;
    }

}
