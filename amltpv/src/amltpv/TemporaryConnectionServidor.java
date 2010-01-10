/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amltpv;

import java.io.IOException;
import java.net.ServerSocket;
import javax.swing.JOptionPane;

/**
 *
 * @author adam
 */
public class TemporaryConnectionServidor {
    private ServerSocket serverSocket;
    private ConnectionThread connection;

    TemporaryConnectionServidor(){
        AmltpvView.util.log("Servidor temporal en puerto 7");
        try {
            serverSocket = new ServerSocket(7);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"No se puede abrir el puerto");
        }
    }

    void waitForConnection(){
        while (true){
        try {
             connection = new ConnectionThread(serverSocket.accept());
             AmltpvView.util.log("Conexión temporal remota aceptada");
             Thread t = new Thread(connection);
             t.start();
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null,"No se pudo aceptar conexión");
        }
        }
    }
}
