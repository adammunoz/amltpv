/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amltpv;

/**
 *
 * @author adam
 */
public class FacturaItem {

    String cant = null;
    String prod = null;
    String subTotal = null;

    public FacturaItem(String cant,String prod,String subTotal){
        this.cant = cant;
        this.prod = prod;
        this.subTotal = subTotal;
    }
}

