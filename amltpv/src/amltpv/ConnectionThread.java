/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amltpv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import javax.swing.ImageIcon;
import converterpack.Converter;

/**
 *
 * @author adam
 */
public class ConnectionThread implements Runnable{
    private PrintWriter out;
    private Socket socket;
    private BufferedReader in;
    private boolean busy;
    static Vector<String> mesasOcupadas = new Vector();
    public ConnectionThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        startStreams();
        try {
            waitForMsgs();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }

    void startStreams(){
        try {
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
        }
        catch (IOException ex) {
            AmltpvView.util.log(ex.toString());
        }
    }

    void waitForMsgs() throws IOException{
        String msg="";
        while ((msg = in.readLine())!=null){
            decode(msg);
        }
    }

    void sendMsg(String msg){
        System.out.println("Sending:"+msg);
        out.println(msg);
    }

    

    

    private void decode(String msg) {
        System.out.println("client says:"+msg);
        String operation = "";
        String operand = "";

        String[] a = msg.split("@");
        operation = a[0];
        operand = a[1];

        if (operation.equals("mobileMesa")){
            String [] subDiv = operand.split(":");
            String mesa = subDiv[0];
            String prodsString = subDiv[1];
            String [] prods = prodsString.split(",");
            for (int i=0;i<prods.length;i++){
                AmltpvView.db.addProductoToMesasPool(mesa, prods[i],true);
            }
            decode("mesaCambiada@"+mesa);
        }
        else if (operation.equals("productosMobile")){
            Converter converter = new Converter(AmltpvView.productosModel);
            converter.startConvert();
            sendMsg(converter.getRepresentation());
        }
        else if (operation.equals("dbQuery")){
            busy = true;
            System.out.println("Database operation identified");
            String answeredQuery = AmltpvView.db.decodeRemoteQuery(operand);
            sendMsg(answeredQuery);
            System.out.println("server is free now");
            busy = false;
        }
        else if (operation.equals("clientMesaInsert")){
            String[] b = operand.split(":");
            String mesa = b[0];
            String producto = b[1];
            if (operation.contains("NoCocina")){
                AmltpvView.db.addProductoToMesasPool(mesa, producto,false);
            }
            else{
                AmltpvView.db.addProductoToMesasPool(mesa, producto,true);
            }
            sendMsg("done:"+operation);
        }

        else if (operation.equals("clientMesaDelete")){
            String[] b = operand.split(":");
            String mesa = b[0];
            String producto = b[1];
            AmltpvView.db.deleteFromMesasPool(mesa, producto);
            sendMsg("done:"+operation);
        }
        else if (operation.equals("moverMesa")){
            System.out.println("Mover mesa operation identified");
            String[] b = operand.split(":");
            String source = b[0];
            String target = b[1];
            AmltpvView.db.moverMesaInPool(source,target);
            sendMsg("done:"+operation);
            decode("mesaCambiada@"+ target);
            decode("mesaLiberada@"+ source);
            decode("mesaCerrada@"+ source);
        }
        else if (operation.equals("mesaCambiada")){
            System.out.println("Mesa cambiada operation identified");
            ThreadServidor.servidor.propagate(operation,operand);
            AmltpvView.changeMesasIcon(operand,new ImageIcon("imgs/mesaBusy.jpg"));
            sendMsg("done:"+operation);
        }
        else if (operation.equals("mesaLiberada")){
            System.out.println("Mesa liberada operation identified");
            ThreadServidor.servidor.propagate(operation,operand);
            AmltpvView.changeMesasIcon(operand,new ImageIcon("imgs/mesa.jpg"));
            sendMsg("done:"+operation);
        }
        else if (operation.equals("mesaOcupada")){
            System.out.println("Mesa ocupada operation identified");
            mesasOcupadas.add(operand);
            ThreadServidor.servidor.propagate(operation,operand);
            sendMsg("done:"+operation);
        }
        else if (operation.equals("mesaCerrada")){
            System.out.println("Mesa cerrada operation identified");
            mesasOcupadas.remove(operand);
            sendMsg("done:"+operation);
            ThreadServidor.servidor.propagate(operation,operand);
            
        }
        else if (operation.equals("mesaServida")){
            System.out.println("Mesa servida operation identified");
            AmltpvView.changeMesasIcon(operand,new ImageIcon("imgs/mesaBusyServida.jpg"));
            ThreadServidor.servidor.propagate(operation,operand);
            sendMsg("done:"+operation);

        }
        else if(operation.equals("fromPoolToCobradas")){
            System.out.println("fromPoolToCobradas operation identified");
            AmltpvView.db.fromPoolToCobradas(operand);
            sendMsg("done:"+operation);
        }
        else if(operation.equals("emptyPool")){
            System.out.println("emptyPool operation identified");
            AmltpvView.db.emptyPool(operand);
            sendMsg("done:"+operation);
            AmltpvView.changeMesasIcon(operand,new ImageIcon("imgs/mesa.jpg"));
            ThreadServidor.servidor.propagate("liberate", operand);
        }

        else{
            System.out.println("NO OPERATION IDENTIFIED. SENDING NULL...");
            sendMsg(null);
        }
    }
    
    void close(){
        try {
            out.close();
            in.close();
            socket.close();
            socket.close();
        } catch (IOException ex) {
            AmltpvView.util.log(ex.toString());
        }
    }
}
