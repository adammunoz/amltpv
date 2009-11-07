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
class ObjectServerThreadMobile extends Thread{
private Socket client = null;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;

    public ObjectServerThreadMobile(Socket clientSocket){
        client = clientSocket;
        System.out.println("Conexión aceptada de mobile");
     try {
      ois = new ObjectInputStream(client.getInputStream());
      System.out.println("Input stream abierto");
      oos = new ObjectOutputStream(client.getOutputStream());
      System.out.println("Output stream abierto");
     } catch(Exception e1) {
         try {
            client.close();
            System.out.println(e1.toString());
         }catch(Exception e) {
           System.out.println(e.getMessage());
         }
         return;
     }
        this.start();
    }

    public void run() {
      try {
            System.out.println("sending productos Object to mobile");
            oos.reset();
            oos.writeObject(AmltpvView.productosModel.getRootNode());
            oos.flush();
            // close streams and connections
            ois.close();
            oos.close();
            client.close();

      } catch(Exception e) {
            System.out.println(e.toString());
      }
   }
}
