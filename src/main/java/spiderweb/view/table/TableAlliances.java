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

import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.view.table.model.TableAlliancesModel;

/**
 * A szövetségeket tartalmazó táblázat
 * 
 * @author Foltin Csaba Richárd
 */
public class TableAlliances extends SpiderTable {
    
    public TableAlliances() throws SpiderReadException {
        super(new TableAlliancesModel());
        
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        setRowSorter(sorter);


        //getColumnModel().getColumn(0).setPreferredWidth(30);
        //getColumnModel().getColumn(1).setPreferredWidth(140);
        getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        
        //setPreferredSize(new Dimension(TABLE_WIDTH_BIG,10));
    }
}
