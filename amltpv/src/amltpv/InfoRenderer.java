/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amltpv;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author adam
 */
public class InfoRenderer extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent (JTable table,
        Object obj, boolean isSelected, boolean hasFocus, int row, int column) {

        Component cell = super.getTableCellRendererComponent(
                         table, obj, isSelected, hasFocus, row, column);

       
       if (isSelected || !isSelected){
        if (row % 2 == 0) {
          cell.setBackground(Color.orange);
        }
       }
      
       return cell;
 }
}
