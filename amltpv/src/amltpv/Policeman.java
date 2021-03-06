/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amltpv;


import java.math.BigDecimal;
import javax.swing.JDialog;


/**
 *
 * @author adam
 */
public class Policeman {
    JDialog dialog;
    String usuario;
    LoggedDialog loggedDialog;
    void requestDialog(JDialog dialog, String requestedRole){
        if (requestAcess(requestedRole)){
            this.dialog = dialog;
            try{
                loggedDialog = (LoggedDialog) dialog;
                loggedDialog.setLoggedUser(usuario);
                System.out.println("Logged user set");
            }
            catch (Exception ex){
                System.out.println("Dialog doesn not support logged user");
            }
            this.dialog.setVisible(true);
        }
        else{
            System.out.println("not_authorised");
            AmltpvView.util.setStatus("Usuario no autorizado");
        }
    }

    boolean requestAcess(String role){
        System.out.println("Se ha pedido acceso de " + role);
        PasswordDialog passwordDialog = new PasswordDialog(null,true);
        passwordDialog.setLocationRelativeTo(null);
        passwordDialog.setVisible(true);
        usuario = passwordDialog.getReturnStatus();
        System.out.println("Pide acceso:" + usuario);
        if (! usuario.equals("not_authorised")){
            if (checkUserHasRole(usuario,role)){
                return true;
            }
            else {
                AmltpvView.util.setStatus("Usuario no autorizado");
                return false;
            }
        }
        else{
            AmltpvView.util.setStatus("Usuario no autorizado");
            return false;
        }
    }

    private boolean checkUserHasRole(String user, String requestedRole) {
        String userRoleInDataBase = AmltpvView.db.queryRole(user);
        BigDecimal userRolePesoInDatabase = AmltpvView.db.queryPeso(userRoleInDataBase);
        BigDecimal requestedRolePesoInDatabase = AmltpvView.db.queryPeso(requestedRole);

        if (userRolePesoInDatabase.intValue() >= requestedRolePesoInDatabase.intValue()){
            return true;
        }
        else{
            return false;
        }


        
    }
}
