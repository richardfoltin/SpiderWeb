/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author pokemonterkep
 */
public abstract class SpiderTable extends JTable{
    
    protected static final int ROW_HEIGHT = 24;
    //protected static final int TABLE_WIDTH = 140;
    //protected static final int TABLE_WIDTH_BIG = 400;
    
    protected TableRowSorter<TableModel> sorter;
    protected List<RowSorter.SortKey> sortKeys;
    protected DefaultTableCellRenderer centerRenderer;
    protected int selectedId;
    
    public SpiderTable(TableModel dataModel) {
        super(dataModel);
        
        setFillsViewportHeight(true);
        getColumnModel().removeColumn(getColumnModel().getColumn(0));
        sorter = new TableRowSorter<>(getModel());
        sortKeys = new ArrayList<>();
        
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1)
                    selectedAction().actionPerformed(null);
            }
        });
        
        setRowHeight(ROW_HEIGHT);
        
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(false);
    }
    
    public int getSelectedId() {
        int modelRow = convertRowIndexToModel(getSelectedRow());
        return (Integer)getModel().getValueAt(modelRow,0);
    }
        
    protected ActionListener selectedAction() {
        return (ActionEvent e) -> {
            // double click
        };
    }
    
}
