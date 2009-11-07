/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amltpv;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author adam
 */
public class ObjectServer extends Thread{
    ServerSocket objectServer;
    public ObjectServer() throws Exception {
        objectServer = new ServerSocket(3000);
        System.out.println("Object Server started");
        this.start();
   }

   public void run() {
     while(true) {
       try {
        System.out.println("Waiting for connections server.");
        Socket client = objectServer.accept();
        ObjectServerThread c = new ObjectServerThread(client);
       } catch(Exception e) {}
     }
   }
}

