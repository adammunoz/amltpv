/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amltpv;




/**
 *
 * @author adam
 */
public class ThreadServidor implements Runnable {
    static Servidor servidor;

    @Override
    public void run(){
        servidor = new Servidor();
        servidor.waitForConnection();
    }
    
}
