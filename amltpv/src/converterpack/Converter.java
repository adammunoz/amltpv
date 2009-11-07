/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package converterpack;
import amltpv.ProductosModel;
import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;
/**
 *
 * @author adam
 */
public class Converter {
    ProductosModel model;
    String representation = "";
    public Converter(ProductosModel model){
        this.model = model;
        System.out.println("Converter object initiliased");
    }

    public void startConvert(){
        DefaultMutableTreeNode rootNode = model.getRootNode();
        String nodeStr = rootNode.toString();
        String parentStr = "none";
        representation = nodeRep(nodeStr,parentStr);
        convertAll(rootNode.children());
    }

    public void convertAll(Enumeration<DefaultMutableTreeNode> children){
        DefaultMutableTreeNode currentNode;
        while (children.hasMoreElements()){
            currentNode = children.nextElement();
            if (!currentNode.isLeaf()){
                convertAll(currentNode.children());
            }
            convert(currentNode);
        }
    }

    void convert(DefaultMutableTreeNode node){
        String nodeStr = node.toString();
        String parentStr = node.getParent().toString();
        representation = representation + "#" + nodeRep(nodeStr,parentStr);
    }

    public String getRepresentation(){
        return representation;
    }

    String nodeRep(String node,String parent){
        String rep = node + "<son>" + parent;
        return rep;
    }
}
