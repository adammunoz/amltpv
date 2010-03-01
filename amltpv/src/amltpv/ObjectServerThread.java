/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amltpv;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author adam
 */
public class ObjectServerThread extends Thread {
    private Socket client = null;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;

    public ObjectServerThread(Socket clientSocket){
        client = clientSocket;
     try {
      ois = new ObjectInputStream(client.getInputStream());
      oos = new ObjectOutputStream(client.getOutputStream());
     } catch(Exception e1) {
         try {
            client.close();
         }catch(Exception e) {
           System.out.println(e.getMessage());
         }
         return;
     }
        this.start();
    }

    public void run() {
      try {
            System.out.println("sending productos Object");
            ProductosModel pm = AmltpvView.productosModel;
            oos.writeObject(AmltpvView.productosModel);
            oos.flush();
            // close streams and connections
            ois.close();
            oos.close();
            client.close();
         
      } catch(Exception e) {}
   }


}
