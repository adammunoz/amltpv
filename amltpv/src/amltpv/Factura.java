/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amltpv;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.Vector;
import java.awt.print.*;
import java.util.HashMap;

/**
 *
 * @author adam
 */
public class Factura implements Printable{

    String fecha = null;
    String mesa = null;
    String nombreNegocio = null;
    String lastMsg = null;
    Vector<FacturaItem> contents = null;
    String total = null;
    String direccion = null;
    String telefono = null;
    String cif = null;
    String email = null;
    String web = null;
    Graphics2D g2d;
    
    final int headerX = 20;
    final int cantX = 20;
    final int prodX = 35;
    final int subX = 150;
    final int lineHeight = 15;
    final int numberOfProductsPerPage = 10;
    int pages = 0;  
    Vector<Integer> pageBreaks = new Vector();
    int productIndex = 1;
    int currentPage = 1;
    public Factura(String mesa,Vector<FacturaItem> contents, String total,
            HashMap strings){
        this.mesa = mesa;
        this.contents = contents;
        this.total = total;
        
        fecha = (String) strings.get("fecha");
        nombreNegocio = (String) strings.get("nombreNegocio");
        direccion = (String) strings.get("direccion");
        telefono = (String) strings.get("telefono");
        cif = (String) strings.get("cif");
        email = (String) strings.get("email");
        web = (String) strings.get("web");
        
        
    }
    
    void calculatePages(){
        int productsSize = contents.size() +1;
        int pagesDiv = productsSize / numberOfProductsPerPage;
        int remainder = productsSize % numberOfProductsPerPage;

        if (pagesDiv < 1){
            pages = 1;
        }
        else if (remainder > 0){
            pages = pagesDiv +1;
        }
        else{
            pages = 1;
        }
    }

    void drawHeader(int page){
        if (page == 1){
            AmltpvView.util.log("Drawing header...");
            g2d.drawString(nombreNegocio, headerX, 20);
            g2d.drawString(fecha, headerX, 35);
            g2d.drawString("Mesa " + mesa, headerX, 50);
            g2d.drawLine(headerX,65,subX+30,65);
        }
        else{
            AmltpvView.util.log("Not drawing header");
        }
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex)throws PrinterException{
        if (pages == 0){
            AmltpvView.util.log("Calculating pages");
            calculatePages();
            AmltpvView.util.log("We need " + pages + " pages");
        }
        if (pageIndex + 1 > pages) {
            return NO_SUCH_PAGE;
        }

        this.g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        drawHeader(pageIndex +1);
        drawContents(pageIndex +1);
        return PAGE_EXISTS;

    }

    void drawContents(int page){
        AmltpvView.util.log("Drawing contents... page " + page);
        FacturaItem item;
        Iterator<FacturaItem> iter = contents.iterator();

        int lineFac;
        if (page == 1){
            lineFac = 65;
            AmltpvView.util.log("Line starting from " + lineFac);
        }
        else{
            lineFac = 5;
            AmltpvView.util.log("Line starting from " + lineFac);
        }

        productIndex = 1;
        while (iter.hasNext()){
            //AmltpvView.util.log("LineFac is " + lineFac);
            AmltpvView.util.log("Processing product index for printing " + productIndex);
            
            AmltpvView.util.log("LineFac is " + lineFac);
            item = iter.next();
            if (productIndex < page*numberOfProductsPerPage &&
                    productIndex >= (page-1)*numberOfProductsPerPage){
                lineFac += lineHeight;
                drawFacItem(item,lineFac);
                AmltpvView.util.log("Printed index " + productIndex +
                        " with lineFac " + lineFac);
            }
            productIndex += 1;
        }
        AmltpvView.util.log("No more productos");
        if (pages == page){ //It's the last page
            drawFooter(lineFac);
        }
        lineFac = 0;

    }

    void drawFacItem(FacturaItem item,int y){
        this.g2d.drawString(item.cant,cantX,y);
        this.g2d.drawString(item.prod, prodX,y);
        this.g2d.drawString(item.subTotal, subX,y);
    }

    void drawFooter(int lineFac) {
        g2d.drawString("Total con IVA: "+total, prodX, lineFac+lineHeight*2);
        g2d.drawString(web, prodX, lineFac+lineHeight*3);
        g2d.drawString(cif, prodX, lineFac+lineHeight*4);
        g2d.drawString(direccion, prodX, lineFac+lineHeight*5);
    }

}
