/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.view.table;

import javax.swing.RowSorter;
import javax.swing.SortOrder;

import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.view.table.model.TableAlliancesModel;

/**
 * Egy házhoz tartozó szövetségeket tartalmazó táblázat
 * 
 * @author Foltin Csaba Richárd
 */
public class TableHouseAlliances extends SpiderTable {
    
    public TableHouseAlliances(House house) throws SpiderReadException {
        super(new TableAlliancesModel(house));
        
        getColumnModel().removeColumn(getColumnModel().getColumn(2));
        getColumnModel().removeColumn(getColumnModel().getColumn(2));
        getColumnModel().removeColumn(getColumnModel().getColumn(0));
        
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        setRowSorter(sorter);


        //getColumnModel().getColumn(0).setPreferredWidth(30);
        //getColumnModel().getColumn(1).setPreferredWidth(140);
        getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        
        getColumnModel().getColumn(0).setHeaderValue("Alliances");
        
        //setPreferredSize(new Dimension(TABLE_WIDTH,10));
    }
}
