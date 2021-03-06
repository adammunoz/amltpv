/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ProductosDialog.java
 *
 * Created on 06-may-2009, 22:34:12
 */

package amltpv;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author adam
 */
public class ProductosDialog extends javax.swing.JDialog {
    private ProductosModel model;
    private DefaultMutableTreeNode selectedNode;
    
    /** Creates new form ProductosDialog */
    public ProductosDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        model = AmltpvView.productosModel;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nombreCategoriaTextField = new javax.swing.JTextField();
        addCategoriaButton = new javax.swing.JButton();
        padreLabelCategoria = new javax.swing.JLabel();
        borrarCategoria = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        nombreProductoTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        priceField = new javax.swing.JTextField();
        addProductoButton = new javax.swing.JButton();
        padreLabelProducto = new javax.swing.JLabel();
        borrarProducto = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        productosTree = new javax.swing.JTree();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(amltpv.AmltpvApp.class).getContext().getResourceMap(ProductosDialog.class);
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jFormattedTextField1.setText(resourceMap.getString("jFormattedTextField1.text")); // NOI18N
        jFormattedTextField1.setName("jFormattedTextField1"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        nombreCategoriaTextField.setText(resourceMap.getString("nombreCategoriaTextField.text")); // NOI18N
        nombreCategoriaTextField.setName("nombreCategoriaTextField"); // NOI18N
        nombreCategoriaTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreCategoriaTextFieldActionPerformed(evt);
            }
        });

        addCategoriaButton.setText(resourceMap.getString("addCategoriaButton.text")); // NOI18N
        addCategoriaButton.setName("addCategoriaButton"); // NOI18N
        addCategoriaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCategoriaButtonActionPerformed(evt);
            }
        });

        padreLabelCategoria.setText(resourceMap.getString("padreLabelCategoria.text")); // NOI18N
        padreLabelCategoria.setName("padreLabelCategoria"); // NOI18N

        borrarCategoria.setText(resourceMap.getString("borrarCategoria.text")); // NOI18N
        borrarCategoria.setName("borrarCategoria"); // NOI18N
        borrarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarCategoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(padreLabelCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(addCategoriaButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addComponent(borrarCategoria))
                    .addComponent(nombreCategoriaTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(padreLabelCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nombreCategoriaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addCategoriaButton)
                    .addComponent(borrarCategoria)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), resourceMap.getString("jPanel2.border.title"))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        nombreProductoTextField.setName("nombreProductoTextField"); // NOI18N
        nombreProductoTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreProductoTextFieldActionPerformed(evt);
            }
        });

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        priceField.setText(resourceMap.getString("priceField.text")); // NOI18N
        priceField.setName("priceField"); // NOI18N
        priceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceFieldActionPerformed(evt);
            }
        });

        addProductoButton.setText(resourceMap.getString("addProductoButton.text")); // NOI18N
        addProductoButton.setName("addProductoButton"); // NOI18N
        addProductoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductoButtonActionPerformed(evt);
            }
        });

        padreLabelProducto.setName("padreLabelProducto"); // NOI18N

        borrarProducto.setText(resourceMap.getString("borrarProducto.text")); // NOI18N
        borrarProducto.setName("borrarProducto"); // NOI18N
        borrarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(addProductoButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addComponent(borrarProducto))
                    .addComponent(nombreProductoTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(priceField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(padreLabelProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(padreLabelProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(nombreProductoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addProductoButton)
                    .addComponent(borrarProducto)))
        );

        cancelButton.setText(resourceMap.getString("cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText(resourceMap.getString("okButton.text")); // NOI18N
        okButton.setMaximumSize(new java.awt.Dimension(75, 23));
        okButton.setMinimumSize(new java.awt.Dimension(75, 23));
        okButton.setName("okButton"); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        productosTree.setModel(model);
        productosTree.setName("productosTree"); // NOI18N
        productosTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                productosTreeValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(productosTree);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cancelButton)
                            .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void productosTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_productosTreeValueChanged
        try{
        selectedNode = (DefaultMutableTreeNode) productosTree.getLastSelectedPathComponent();
        padreLabelCategoria.setText(selectedNode.toString());
        padreLabelProducto.setText(selectedNode.toString());
        }
        catch(java.lang.NullPointerException ex){
            System.out.println(ex.toString());
        }
    }//GEN-LAST:event_productosTreeValueChanged

    private void addCategoriaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCategoriaButtonActionPerformed
        String fieldText = nombreCategoriaTextField.getText();
        DefaultMutableTreeNode father = selectedNode;
        model.addCategoria(new DefaultMutableTreeNode(fieldText), father, this);
}//GEN-LAST:event_addCategoriaButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        new TreeStorer().write(model);
        this.dispose();
        
}//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
}//GEN-LAST:event_cancelButtonActionPerformed

    private void nombreCategoriaTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreCategoriaTextFieldActionPerformed
        addCategoriaButtonActionPerformed(evt);
    }//GEN-LAST:event_nombreCategoriaTextFieldActionPerformed

    private void addProductoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductoButtonActionPerformed
        String fieldText = nombreProductoTextField.getText();
        
        try{
            float price = Float.parseFloat(priceField.getText());
            DefaultMutableTreeNode father = selectedNode;
            model.addProducto(new DefaultMutableTreeNode(fieldText), father, this);
            model.addProducto(fieldText, price);
        }
        catch (java.lang.NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Error:Debe introducir un número en el precio");
        }

}//GEN-LAST:event_addProductoButtonActionPerformed

    private void priceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceFieldActionPerformed
        addProductoButtonActionPerformed(evt);
}//GEN-LAST:event_priceFieldActionPerformed

    private void nombreProductoTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreProductoTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreProductoTextFieldActionPerformed

    private void borrarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarCategoriaActionPerformed
//        String text = padreLabelCategoria.getText();
//        DefaultMutableTreeNode father = selectedNode;
//        model.removeCategoria(text);
        model.removeNodeFromParent(selectedNode);
    }//GEN-LAST:event_borrarCategoriaActionPerformed

    private void borrarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarProductoActionPerformed
        model.removeNodeFromParent(selectedNode);
    }//GEN-LAST:event_borrarProductoActionPerformed

    /**
    * @param args the command line arguments
    */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCategoriaButton;
    private javax.swing.JButton addProductoButton;
    private javax.swing.JButton borrarCategoria;
    private javax.swing.JButton borrarProducto;
    private javax.swing.JButton cancelButton;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nombreCategoriaTextField;
    private javax.swing.JTextField nombreProductoTextField;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel padreLabelCategoria;
    private javax.swing.JLabel padreLabelProducto;
    private javax.swing.JTextField priceField;
    private javax.swing.JTree productosTree;
    // End of variables declaration//GEN-END:variables

}
