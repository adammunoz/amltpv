/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package converterpack;

import amltpv.ProductosModel;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;


/**
 *
 * @author adam
 */
public class Reader {
    String representation;
    String[] nodesRepArray;
    ProductosModel model;
    DefaultMutableTreeNode rootNode;

    public Reader(String representation){
        this.representation = representation;
        System.out.println("Reader object initialiased");
    }

    public ProductosModel getProductosModel(){
        return new ProductosModel(rootNode);
    }

    public void extractNodes(){
        nodesRepArray = representation.split("#");
        
    }

   

    void buildRootNode(){
        for (String nodeRep : nodesRepArray){
            if (parent(nodeRep).equals("none")){
                rootNode = new DefaultMutableTreeNode(node(nodeRep));
                System.out.println("Root node set:"+rootNode.toString());
            }
        }
    }

    public void buildAll(){
        extractNodes();
        buildRootNode();
        DefaultMutableTreeNode node,parent,foundParent,foundNode;
        processChildrenWithParent(rootNode);
    }

    void processChildrenWithParent(DefaultMutableTreeNode parent){
        Vector<String> childrenVector = new Vector();
        for (String nodeRep:nodesRepArray){
            if (isChild(nodeRep,parent.toString())){
                childrenVector.add(node(nodeRep));
            }
        }

        for (String child : childrenVector){
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
            processChildrenWithParent(childNode);
            parent.add(childNode);
        }
    }

    boolean isChild(String nodeRep,String parentStr){
        return parent(nodeRep).equals(parentStr);
    }

    
    String parent(String nodeRep){
        return nodeRep.split("<son>") [1];
    }

    String node(String nodeRep){
        return nodeRep.split("<son>") [0];
    }
}
