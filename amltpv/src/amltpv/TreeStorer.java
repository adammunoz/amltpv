/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amltpv;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.*;


/**
 *
 * @author adam
 */
public class TreeStorer {
    static final String XMLFILENAME = "productos.xml";
    void write(ProductosModel tree){
        XMLEncoder encoder = null;
        try {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(XMLFILENAME)));
        } catch (FileNotFoundException ex) {
            AmltpvView.util.log(ex.toString());
            AmltpvView.util.setStatus(ex.toString());
        }
        encoder.writeObject(tree);
        encoder.close();
    }

    public ProductosModel read(){
        XMLDecoder decoder = null;
        try {
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(XMLFILENAME));
            decoder = new XMLDecoder(is);
            ProductosModel m = (ProductosModel)decoder.readObject();
            decoder.close();
            return m;
        } catch (FileNotFoundException ex) {
            AmltpvView.util.log(ex.toString());
            AmltpvView.util.setStatus(ex.toString());
            return null;
        }
    }

    
}
