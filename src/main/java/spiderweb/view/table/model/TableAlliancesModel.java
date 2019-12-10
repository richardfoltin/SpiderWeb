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

import spiderweb.entity.Alliance;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.view.MainWindow;

/**
 * A szövetségeket tartalmazó táblázat modelje
 * 
 * @author Foltin Csaba Richárd
 */
public class TableAlliancesModel extends AbstractTableModel  {
    
    private final List<Alliance> data;
    private final String[] colName = new String[]{"ID", "House 1", "House 2", "Start Date", "End Date"}; 

    public TableAlliancesModel() throws SpiderReadException {
        this.data = MainWindow.getInstance().getModel().getAllAlliance();
    }
    
    public TableAlliancesModel(House house) throws SpiderReadException {
        this.data = MainWindow.getInstance().getModel().getAllAlliance(house);
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
        Alliance alliance = data.get(rowIndex);

        switch (columnIndex) {
            case 0 : return alliance.getId();
            case 1 : return alliance.getHouse1().getName(); 
            case 2 : return alliance.getHouse2().getName();
            case 3 : return alliance.getStartDate();
            case 4 : return alliance.getEndDate();
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
