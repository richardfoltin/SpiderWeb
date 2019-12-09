/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.table;

import spiderweb.view.table.model.TableHousesModel;
import java.awt.Dimension;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import spiderweb.jdbcdao.dbexception.SpiderReadException;

/**
 *
 * @author pokemonterkep
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
