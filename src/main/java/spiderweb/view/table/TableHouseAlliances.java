/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.table;

import java.awt.Dimension;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.view.table.model.TableAlliancesModel;

/**
 *
 * @author pokemonterkep
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
