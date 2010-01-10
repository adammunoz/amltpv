/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amltpv;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author adam
 */
public class StatusMsgThread implements Runnable{
    JLabel statusMsg;
    String s = "";

    StatusMsgThread(JLabel statusMsg){
        this.statusMsg = statusMsg;
    }

    void setTextField(String s){
        this.s = s;
    }

    void sleep(int ms){
        try {
                Thread.sleep(ms);
                reset();
            } catch (InterruptedException ex) {
                AmltpvView.util.log("New msg arrived, interrupted thread");
            }
    }
    void reset(){
        s = "";
        statusMsg.setText("");
    }
    @Override
    public void run(){
    while (true){
        while (s.isEmpty()){
            sleep(1000);
        }
        statusMsg.setText(s);
        sleep(5000);

    }
    }
}
