/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amltpv;

import java.awt.print.PageFormat;
import java.awt.print.Paper;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import javax.print.attribute.standard.MediaSizeName;







/**
 *
 * @author adam
 */
public class Impresora {
    private Paper paper = new Paper();
    private PrinterJob job;
    private PageFormat pageFormat = new PageFormat();
    PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();

    public Impresora(){
        job = PrinterJob.getPrinterJob();
    }

    public void setSize(double width, double height){
        //final double inchConv = 0.39 / 72;
        //paper.setSize(width * inchConv, height * inchConv);
        //pageFormat.setPaper(paper);
        attributes.add(MediaSizeName.ISO_A7);
    }


    public void print(Factura fac){
        try {
            job.setPrintable(fac);
            job.print(attributes);
        } catch (PrinterException ex) {
            AmltpvView.util.log("Error de impresión");
        }
    }
 
 }


