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

import spiderweb.view.table.model.TableHousesModel;
import spiderweb.jdbcdao.dbexception.SpiderReadException;

/**
 * A házakat tartalmazó táblázat
 * 
 * @author Foltin Csaba Richárd
 */
public class TableHouses extends SpiderTable {

    public TableHouses() throws SpiderReadException {
        super(new TableHousesModel());
        
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        setRowSorter(sorter);


        getColumnModel().getColumn(0).setPreferredWidth(40);
        //getColumnModel().getColumn(1).setPreferredWidth(140);
        //getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        
        //setPreferredSize(new Dimension(TABLE_WIDTH_BIG,10));
    }
    
}
