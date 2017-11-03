/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amltpv;
import java.net.*;
import java.io.*;


import java.util.Iterator;
import java.util.Vector;
import javax.swing.JOptionPane;



/**
 *
 * @author adam
 */
public class Servidor {
    private final static int PORT = 8001;
    private ServerSocket serverSocket;
    static Vector<ConnectionThread> connectedClients = new Vector();
    ConnectionThread connection;
    Servidor(){
        System.out.println("Servidor en puerto " + PORT);
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
             System.out.println("Conexion con servidor principal aceptada");
             Thread t = new Thread(connection);
             connectedClients.add(connection);
             t.start();
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null,"No se pudo aceptar conexión");
        }
        }
    }
    void comandaCocina(String msg){
        Iterator iter = Servidor.connectedClients.iterator();
            ConnectionThread con;
            while (iter.hasNext()){
                con = (ConnectionThread) iter.next();
                con.sendMsg(msg);
            }
    }

    void killClients(){
        Iterator iter = Servidor.connectedClients.iterator();
            ConnectionThread con;
            while (iter.hasNext()){
                con = (ConnectionThread) iter.next();
                con.sendMsg("kill@now");
            }
    }

    void propagate(String operation, String operand){
        System.out.println("Propagating "+ operation + "@" + operand);
        Iterator iter = connectedClients.iterator();
            ConnectionThread con;
            while (iter.hasNext()){
                con = (ConnectionThread) iter.next();
                con.sendMsg(operation + "@" + operand);
            }
    }
}
