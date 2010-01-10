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
public class ObjectServerMobile extends Thread{
    ServerSocket objectServer;
    public ObjectServerMobile() throws Exception {
        objectServer = new ServerSocket(3001);
        AmltpvView.util.log("Object server mobile started");
        this.start();
   }

   public void run() {
     while(true) {
       try {
        AmltpvView.util.log("Waiting for connections mobile.");
        Socket client = objectServer.accept();
        ObjectServerThreadMobile c = new ObjectServerThreadMobile(client);
       } catch(Exception e) {}
     }
   }
}
