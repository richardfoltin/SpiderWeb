/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.table;

import javax.swing.RowSorter;
import javax.swing.SortOrder;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.view.table.model.TableAlliancesModel;

/**
 *
 * @author pokemonterkep
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
