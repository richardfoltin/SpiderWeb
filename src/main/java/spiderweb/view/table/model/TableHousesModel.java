/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.view.table.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.view.MainWindow;

/**
 * A házakat tartalmazó táblázat modelje
 * 
 * @author Foltin Csaba Richárd
 */
public class TableHousesModel extends AbstractTableModel  {

    private final List<House> data;
    private final String[] colName = new String[]{"ID", "Name", "Motto"}; 

    public TableHousesModel() throws SpiderReadException {
        this.data = MainWindow.getInstance().getModel().getAllHouse();
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
        House house = data.get(rowIndex);

        switch (columnIndex) {
            case 0 : return house.getId();
            case 1 : return house.getName(); 
            case 2 : return house.getMotto();
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