package amltpv;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import javax.swing.JLabel;
import javax.swing.JFrame;
import java.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;
import org.apache.commons.io.*;

/**
 *
 * @author Adam
 */

/* This is a class with various utilities to communicate with the user,
 * check if a file exists, report errors, etc ...
 */

public class Utils {
    private JLabel statusMsg;
    private JFrame mainFrame;
    private StatusMsgThread statusMsgThreadInstance;
    private Thread statusMsgThread;
    public Utils(JLabel statusMsg){
        this.statusMsg = statusMsg;
        statusMsgThreadInstance = new StatusMsgThread(this.statusMsg);
        statusMsgThread = new Thread(statusMsgThreadInstance);
        statusMsgThread.start();
    }
    public void setMainFrame(JFrame f){
        this.mainFrame = f;
    }

    public JFrame getMainFrame(){
        return mainFrame;
    }
    public void setStatus(String s){
        statusMsgThread.interrupt();
        statusMsgThreadInstance.setTextField(s);
    }
    public void appendStatus(String s){
        String oldText = statusMsg.getText();
        setStatus(oldText +" - " +s);
    }
    public boolean existeArchivo(String archivo) {
        AmltpvView.util.log("Existe? "+archivo);
        File f = new File(archivo);
        return f.exists()? true : false;
    }
    public void log(String s){
        System.out.println(s);
        try{
            File f = new File("log.txt");
            BufferedWriter bw = new BufferedWriter (new FileWriter (f, true));  
            bw.write (getCurrentTimeString() + ":" + s);  
            bw.newLine();  
            bw.flush();  
            bw.close();   
        }     
        catch (java.io.IOException e){
            System.out.println("No se puede escribir al log");
        }
     }

    public void log(int s){
        System.out.println(s);
        try{
            File f = new File("log.txt");
            BufferedWriter bw = new BufferedWriter (new FileWriter (f, true));
            bw.write (getCurrentTimeString() + ":" + s);
            bw.newLine();
            bw.flush();
            bw.close();
        }
        catch (java.io.IOException e){
            System.out.println("No se puede escribir al log");
        }
     }

    public void log(boolean s){
        System.out.println(s);
        try{
            File f = new File("log.txt");
            BufferedWriter bw = new BufferedWriter (new FileWriter (f, true));
            bw.write (getCurrentTimeString() + ":" + s);
            bw.newLine();
            bw.flush();
            bw.close();
        }
        catch (java.io.IOException e){
            System.out.println("No se puede escribir al log");
        }
     }

    public void persistSetting(String setting,String value){
        if (existeArchivo("settings/"+setting+".txt")){
            AmltpvView.util.log("Existe el archivo y lo vamos a borrar");
            AmltpvView.util.log(
                    FileUtils.deleteQuietly(new File("settings/"+setting+".txt")));
            
        }
        try{
            File f = new File("settings/"+setting+".txt");
            BufferedWriter bw = new BufferedWriter (new FileWriter (f, true));
            bw.write(value);
            bw.flush();
            bw.close();
        }
        catch (java.io.IOException e){
            log(e.toString());
        }
    }

    public String readPersistentSetting(String setting){
        if (existeArchivo("settings/"+setting+".txt")){
            try{
                File f = new File("settings/"+setting+".txt");
                BufferedReader br = new BufferedReader (new FileReader(f));
                return br.readLine();

            }
            catch (java.io.IOException e){
                log(e.toString());
                return "";
            }
        }
        else{
            log("No hay setting persistent todavia");
            return "";
        }
    }

    public String getCurrentTimeString(){
        final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    public String getTodayString(){
        final String DATE_FORMAT_NOW = "yyyy-MM-dd";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    public String getTimeString(){
        final String DATE_FORMAT_NOW = "HH:mm:ss";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    public void deleteDirectory(File path) {
    if( path.exists() ) {
      File[] files = path.listFiles();
      for(int i=0; i<files.length; i++) {
         if(files[i].isDirectory()) {
           deleteDirectory(files[i]);
         }
         else {
           files[i].delete();
         }
      }
    }
  }
    public void deleteFile(File path){
        path.delete();
    }

    public String enumToString(Enumeration enu){
        String result = "";
        while (enu.hasMoreElements()){
            result = result + ";" + enu.nextElement();
        }
        try{
            return result.substring(1); // we don't need column in the beginning
        }
        catch (java.lang.StringIndexOutOfBoundsException ex) {
            return result; //because the table is empty;
        }

    }

    public Enumeration stringToEnum(String str){
        if (str.equals("")){
            return new Vector().elements();
        }
        String[] array = str.split(";");
        return new Vector(Arrays.asList(array)).elements();

    }

//    public String encodeUTF8(String str){
//        try {
//            byte[] utf8Bytes = str.getBytes("UTF8");
//            byte[] defaultBytes = str.getBytes();
//            return new String(utf8Bytes, "UTF8");
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }

public static String charToString(char[] c){
        String full_password = "";

        // get the total characters of the password
        int password_length = c.length;

        // Combining the char array into string
        for(int i = 0; i < password_length ; i++){
            full_password = full_password + c[i];
        }

        //Zero out the password
        Arrays.fill(c, '0');
        return full_password;
    }
}