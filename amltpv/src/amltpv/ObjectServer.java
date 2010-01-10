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
        AmltpvView.util.log("Object Server started");
        this.start();
   }

   public void run() {
     while(true) {
       try {
        AmltpvView.util.log("Waiting for connections server.");
        Socket client = objectServer.accept();
        ObjectServerThread c = new ObjectServerThread(client);
       } catch(Exception e) {}
     }
   }
}

