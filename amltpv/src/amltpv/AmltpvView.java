/*
 * AmltpvView.java
 */

package amltpv;


import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;


import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * The application's main frame.
 */
public class AmltpvView extends FrameView {
    private Thread servidorThread;
    private Thread servidorTemporalThread;
    private ObjectServer objServer;
    ObjectServerMobile objServerMobile;
    CobradasDialog cobradasDialog;
    public AmltpvView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });

        //SEVERRAL INITIALIZATIONS
        util = new Utils(statusMessageLabel);
        util.setMainFrame(AmltpvApp.getApplication().getMainFrame());
        db = new DataBase();
        if (!db.checkIfExists()){
            FirstPassword firstPasswordDialog = new FirstPassword(util.getMainFrame(),true);
            firstPasswordDialog.setLocationRelativeTo(util.getMainFrame());
            firstPasswordDialog.setVisible(true);
        }
        if (util.existeArchivo("productos.xml")){
            productosModel = new TreeStorer().read();
            productosModel.activate();
        }
        else{
            util.log("No se ha encontrado arbol de productos. Se creará uno.");
            DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Productos");
            productosModel = new ProductosModel(rootNode);
        }
        super.getFrame().setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        self = this;
        cobradasDialog = new CobradasDialog(super.getFrame(),true);
        cobradasDialog.setLocationRelativeTo(super.getFrame());     
}
    void pintarMesas(){
        int numMesas = Byte.parseByte(db.queryValor("numMesas"));
        mesasArray = new JButton[numMesas+1];
        for (int i=1;i<=numMesas;i++){
           mesasArray[i] = new JButton(String.valueOf(i),new ImageIcon("imgs/mesa.jpg"));
           mesasArray[i].setFont(new Font("sansserif",Font.BOLD,18));
           mainPanel.add(mesasArray[i]);
           mesasArray[i].addActionListener(new ButtonHandler());
        }
        mainPanel.revalidate();
    }

    void borrarMesas(){
        mainPanel.removeAll();
        mainPanel.revalidate();
    }

    static void changeMesasIcon(String mesa,ImageIcon icon){
        mesasArray[Integer.parseInt(mesa)].setIcon(icon);
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = AmltpvApp.getApplication().getMainFrame();
            aboutBox = new AmltpvAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        AmltpvApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        abrirNegocio = new javax.swing.JMenuItem();
        cierre = new javax.swing.JMenuItem();
        gastoMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        confMenu = new javax.swing.JMenu();
        negocioMenuItem = new javax.swing.JMenuItem();
        mesasMenuItem = new javax.swing.JMenuItem();
        productosDialogMenuItem = new javax.swing.JMenuItem();
        userMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        cobradasMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        menuBar.setName("menuBar"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(amltpv.AmltpvApp.class).getContext().getResourceMap(AmltpvView.class);
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setFont(resourceMap.getFont("fileMenu.font")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        abrirNegocio.setFont(resourceMap.getFont("abrirNegocio.font")); // NOI18N
        abrirNegocio.setForeground(resourceMap.getColor("abrirNegocio.foreground")); // NOI18N
        abrirNegocio.setText(resourceMap.getString("abrirNegocio.text")); // NOI18N
        abrirNegocio.setName("abrirNegocio"); // NOI18N
        abrirNegocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirNegocioActionPerformed(evt);
            }
        });
        fileMenu.add(abrirNegocio);

        cierre.setFont(resourceMap.getFont("cierre.font")); // NOI18N
        cierre.setForeground(resourceMap.getColor("cierre.foreground")); // NOI18N
        cierre.setText(resourceMap.getString("cierre.text")); // NOI18N
        cierre.setName("cierre"); // NOI18N
        cierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cierreActionPerformed(evt);
            }
        });
        fileMenu.add(cierre);

        gastoMenuItem.setFont(resourceMap.getFont("gastoMenuItem.font")); // NOI18N
        gastoMenuItem.setForeground(resourceMap.getColor("gastoMenuItem.foreground")); // NOI18N
        gastoMenuItem.setText(resourceMap.getString("gastoMenuItem.text")); // NOI18N
        gastoMenuItem.setName("gastoMenuItem"); // NOI18N
        gastoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gastoMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(gastoMenuItem);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(amltpv.AmltpvApp.class).getContext().getActionMap(AmltpvView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setFont(resourceMap.getFont("exitMenuItem.font")); // NOI18N
        exitMenuItem.setForeground(resourceMap.getColor("exitMenuItem.foreground")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        confMenu.setText(resourceMap.getString("confMenu.text")); // NOI18N
        confMenu.setFont(resourceMap.getFont("confMenu.font")); // NOI18N
        confMenu.setName("confMenu"); // NOI18N

        negocioMenuItem.setFont(resourceMap.getFont("negocioMenuItem.font")); // NOI18N
        negocioMenuItem.setForeground(resourceMap.getColor("negocioMenuItem.foreground")); // NOI18N
        negocioMenuItem.setText(resourceMap.getString("negocioMenuItem.text")); // NOI18N
        negocioMenuItem.setName("negocioMenuItem"); // NOI18N
        negocioMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                negocioMenuItemActionPerformed(evt);
            }
        });
        confMenu.add(negocioMenuItem);

        mesasMenuItem.setFont(resourceMap.getFont("mesasMenuItem.font")); // NOI18N
        mesasMenuItem.setForeground(resourceMap.getColor("mesasMenuItem.foreground")); // NOI18N
        mesasMenuItem.setText(resourceMap.getString("mesasMenuItem.text")); // NOI18N
        mesasMenuItem.setName("mesasMenuItem"); // NOI18N
        mesasMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mesasMenuItemActionPerformed(evt);
            }
        });
        confMenu.add(mesasMenuItem);

        productosDialogMenuItem.setFont(resourceMap.getFont("productosDialogMenuItem.font")); // NOI18N
        productosDialogMenuItem.setForeground(resourceMap.getColor("productosDialogMenuItem.foreground")); // NOI18N
        productosDialogMenuItem.setText(resourceMap.getString("productosDialogMenuItem.text")); // NOI18N
        productosDialogMenuItem.setName("productosDialogMenuItem"); // NOI18N
        productosDialogMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productosDialogMenuItemActionPerformed(evt);
            }
        });
        confMenu.add(productosDialogMenuItem);

        userMenuItem.setFont(resourceMap.getFont("userMenuItem.font")); // NOI18N
        userMenuItem.setForeground(resourceMap.getColor("userMenuItem.foreground")); // NOI18N
        userMenuItem.setText(resourceMap.getString("userMenuItem.text")); // NOI18N
        userMenuItem.setName("userMenuItem"); // NOI18N
        userMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userMenuItemActionPerformed(evt);
            }
        });
        confMenu.add(userMenuItem);

        menuBar.add(confMenu);

        jMenu1.setForeground(resourceMap.getColor("jMenu1.foreground")); // NOI18N
        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setFont(resourceMap.getFont("jMenu1.font")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        cobradasMenuItem.setFont(resourceMap.getFont("cobradasMenuItem.font")); // NOI18N
        cobradasMenuItem.setForeground(resourceMap.getColor("cobradasMenuItem.foreground")); // NOI18N
        cobradasMenuItem.setText(resourceMap.getString("cobradasMenuItem.text")); // NOI18N
        cobradasMenuItem.setName("cobradasMenuItem"); // NOI18N
        cobradasMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cobradasMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(cobradasMenuItem);

        menuBar.add(jMenu1);

        helpMenu.setForeground(resourceMap.getColor("helpMenu.foreground")); // NOI18N
        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setFont(resourceMap.getFont("helpMenu.font")); // NOI18N
        helpMenu.setMargin(new java.awt.Insets(0, 10, 0, 0));
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setFont(resourceMap.getFont("aboutMenuItem.font")); // NOI18N
        aboutMenuItem.setForeground(resourceMap.getColor("aboutMenuItem.foreground")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void productosDialogMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productosDialogMenuItemActionPerformed
         ProductosDialog productosDialog = new ProductosDialog(super.getFrame(),true);
         productosDialog.setLocationRelativeTo(super.getFrame());
         new Policeman().requestDialog((JDialog) productosDialog, "Administrador");

    }//GEN-LAST:event_productosDialogMenuItemActionPerformed

    private void mesasMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mesasMenuItemActionPerformed
        MesasDialog mesasDialog = new MesasDialog(super.getFrame(),true);
        mesasDialog.setLocationRelativeTo(super.getFrame());
        mesasDialog.setVisible(true);
}//GEN-LAST:event_mesasMenuItemActionPerformed

    private void negocioMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_negocioMenuItemActionPerformed
        DatosNegocioDialog datosNegocioDialog = new DatosNegocioDialog(super.getFrame(),true);
        datosNegocioDialog.setLocationRelativeTo(super.getFrame());
        new Policeman().requestDialog((JDialog) datosNegocioDialog,"Administrador");
        
        
}//GEN-LAST:event_negocioMenuItemActionPerformed

    private void abrirNegocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirNegocioActionPerformed
        
        String cajaCerrada = db.queryValor("cajaCerrada");
        if (new Policeman().requestAcess("Supervisor")){
            servidorThread = new Thread(new ThreadServidor());
            servidorThread.start();
            servidorTemporalThread = new Thread(new ThreadServidorTemporal());
            servidorTemporalThread.start();
            try {
                objServer = new ObjectServer();
                objServerMobile = new ObjectServerMobile();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
            if (cajaCerrada.equals("true") || cajaCerrada.equals("nulo")){
                AmltpvView.db.addApertura();
                AmltpvView.db.updateConfItem("cajaCerrada", "false");
            }
            else{
                JOptionPane.showMessageDialog(this.getFrame(), "La caja no se cerró" +
                        " así que continuamos con la última sesión");
            }
            pintarMesas();
        }
    }//GEN-LAST:event_abrirNegocioActionPerformed

    private void cierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cierreActionPerformed
         CajaDialog dialog = new CajaDialog(this.getFrame(),true);
         dialog.setLocationRelativeTo(this.getFrame());
         new Policeman().requestDialog((JDialog) dialog,"Supervisor");


    }//GEN-LAST:event_cierreActionPerformed

    private void gastoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gastoMenuItemActionPerformed
        GastosDialog dialog = new GastosDialog(this.getFrame(),true);
        dialog.setLocationRelativeTo(this.getFrame());
        new Policeman().requestDialog((JDialog) dialog,"Supervisor");
    }//GEN-LAST:event_gastoMenuItemActionPerformed

    private void userMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userMenuItemActionPerformed
        UsuariosDialog dialog = new UsuariosDialog(this.getFrame(),true);
        dialog.setLocationRelativeTo(this.getFrame());
        new Policeman().requestDialog((JDialog) dialog,"Administrador");

    }//GEN-LAST:event_userMenuItemActionPerformed

    private void cobradasMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cobradasMenuItemActionPerformed
        cobradasDialog.setVisible(true);
    }//GEN-LAST:event_cobradasMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem abrirNegocio;
    private javax.swing.JMenuItem cierre;
    private javax.swing.JMenuItem cobradasMenuItem;
    private javax.swing.JMenu confMenu;
    private javax.swing.JMenuItem gastoMenuItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem mesasMenuItem;
    private javax.swing.JMenuItem negocioMenuItem;
    private javax.swing.JMenuItem productosDialogMenuItem;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JMenuItem userMenuItem;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
    static DataBase db;
    public static Utils util;
    static ProductosModel productosModel;
    static JButton[] mesasArray;
    static int remoteProcessing;
    public static AmltpvView self;
}
class ButtonHandler implements ActionListener{
    public void actionPerformed(java.awt.event.ActionEvent e){
        JButton sourceButton = (JButton) e.getSource();
        //sourceButton.setIcon(new ImageIcon("imgs/mesaBusy.jpg"));
        int numMesa = Integer.parseInt(sourceButton.getText());
        if (ConnectionThread.mesasOcupadas.contains(Integer.toString(numMesa))){
           JOptionPane.showMessageDialog(AmltpvView.util.getMainFrame(),"Esta mesa esta siendo procesada en otra ordenador"); 
        }
        else{
            ConnectionThread.mesasOcupadas.add(new Integer(numMesa).toString());
            ThreadServidor.servidor.propagate("mesaOcupada",new Integer(numMesa).toString());
            VentasDialogScreen ventasScreen = new VentasDialogScreen(AmltpvView.util.getMainFrame(),true,numMesa);
            ventasScreen.setTitle("Mesa "+numMesa);
            ventasScreen.setLocationRelativeTo(AmltpvView.util.getMainFrame());
            ventasScreen.setVisible(true);
        }
    }
}