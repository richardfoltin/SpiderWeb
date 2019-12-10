/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
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
 * A programban használt megjelnített táblázatok absztrakt szülőosztálya
 * 
 * @author Foltin Csaba Richárd
 */
public abstract class SpiderTable extends JTable{
    
    protected static final int ROW_HEIGHT = 24;
    
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
        
    /**
     * Visszaadja a táblán aktuálisan kiválasztott sor kulcsát
     * 
     * @return 
     */
    public int getSelectedId() {
        int modelRow = convertRowIndexToModel(getSelectedRow());
        return (Integer)getModel().getValueAt(modelRow,0);
    }
       
    /**
     * Egy soron duplán kattintás történt
     * @return 
     */
    protected ActionListener selectedAction() {
        return (ActionEvent e) -> {
            // double click
        };
    }
    
}
