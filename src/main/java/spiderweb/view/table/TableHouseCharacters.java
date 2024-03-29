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
import spiderweb.view.table.model.TableCharactersModel;

/**
 * Egy házhoz tartozó karaktereket tartalmazó táblázat
 * 
 * @author Foltin Csaba Richárd
 */
public class TableHouseCharacters extends SpiderTable {
    
    public TableHouseCharacters(House house) throws SpiderReadException {
        super(new TableCharactersModel(house));
        
        getColumnModel().removeColumn(getColumnModel().getColumn(1));
        getColumnModel().removeColumn(getColumnModel().getColumn(1));
        getColumnModel().removeColumn(getColumnModel().getColumn(1));
        
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        setRowSorter(sorter);

        //getColumnModel().getColumn(0).setPreferredWidth(30);
        //getColumnModel().getColumn(1).setPreferredWidth(140);
        getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        
        getColumnModel().getColumn(0).setHeaderValue("Characters");
        
        //setPreferredSize(new Dimension(TABLE_WIDTH,10));
    }
}
