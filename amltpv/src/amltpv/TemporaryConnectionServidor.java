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
    private final static int PORT = 8000;
    private ServerSocket serverSocket;
    private ConnectionThread connection;

    TemporaryConnectionServidor(){
        System.out.println("Servidor temporal en puerto " + PORT);
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"No se puede abrir el puerto");
        }
    }

    void waitForConnection(){
        while (true){
        try {
             connection = new ConnectionThread(serverSocket.accept());
             System.out.println("Conexión temporal remota aceptada");
             Thread t = new Thread(connection);
             t.start();
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null,"No se pudo aceptar conexión");
        }
        }
    }
}
