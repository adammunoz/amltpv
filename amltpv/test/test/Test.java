/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;
import amltpv.*;
import converterpack.*;
import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;
/**
 *
 * @author adam
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ProductosModel productosModel = new TreeStorer().read();
        productosModel.activate();
        Converter converter = new Converter(productosModel);
        converter.startConvert();
        System.out.println(converter.getRepresentation());

        Reader reader = new Reader(converter.getRepresentation());
        reader.buildAll();
        ProductosModel readProductosModel = reader.getProductosModel();
    }

}
