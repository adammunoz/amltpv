/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amltpv;

/**
 *
 * @author adam
 */
public class ThreadServidorTemporal implements Runnable{
    static TemporaryConnectionServidor servidorTemporaryConnections;

    public void run(){
        servidorTemporaryConnections = new TemporaryConnectionServidor();
        servidorTemporaryConnections.waitForConnection();
    }
}
