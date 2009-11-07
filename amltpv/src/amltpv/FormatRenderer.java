
package amltpv;
import java.awt.Color;
import javax.swing.table.DefaultTableCellRenderer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adam
 */
public class FormatRenderer extends DefaultTableCellRenderer {
     public FormatRenderer(){
        super();
        setHorizontalAlignment(javax.swing.SwingConstants. RIGHT);
     }
     public FormatRenderer(boolean color){
        super();
        setHorizontalAlignment(javax.swing.SwingConstants. RIGHT);
        setForeground(Color.white);
        setBackground(Color.lightGray);
     }
}
