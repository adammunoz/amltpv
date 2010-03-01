/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amltpv;


import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;




/**
 *
 * @author adam
 */

public class ProductosModel extends DefaultTreeModel implements Serializable{
    
    private ArrayList<String> categorias = new ArrayList();
    private ArrayList<String> productos = new ArrayList();
    private String[] categoriasArray; //We access it with getCategorias;
    private DefaultMutableTreeNode foundNode = null;
    private String test = "";
    private DefaultMutableTreeNode currentNode;
    //PROPERTY
    private Hashtable productosHash = new Hashtable();
    //PROPERTY
    private DefaultMutableTreeNode rootNode = null;

    public ProductosModel(){
        super(new DefaultMutableTreeNode("NO ACTIVADO"));
        System.out.println("productos model created");
    }
    public ProductosModel(DefaultMutableTreeNode node){
        super(node);
        setRootNode(node);
        this.insertNodeInto(new DefaultMutableTreeNode("vacío"),(MutableTreeNode) rootNode, 0);
        categorias.clear();
        populateCategoriasEnumeration(rootNode);
        productos.clear();
        populateProductosEnumeration(rootNode);
    }
    //This is use to activate the object when is recuperated from xml
    //If we don't do this super constructor is never called and so the tree moel is not properly initialized
    //If we don't call this we'll see 'NO ACTIVAD' in the JTREE
    public void activate(){
        System.out.println("El modelo de productos va a ser activado");
        super.root = rootNode;
        System.out.println("Root node ha sido establecido. (método activate)");
        System.out.println("Llamando a populateCategoriasEnumeration");
        populateCategoriasEnumeration(rootNode);
    }

    public Hashtable getProductosHash(){ // for property
        return productosHash;
    }

    public void setProductosHash(Hashtable hash){ // for property
        productosHash = hash;
    }

    //In order for rootNode to be serialized we need a set and get method
    public void setRootNode(DefaultMutableTreeNode n){
        rootNode = n;
    }
    public DefaultMutableTreeNode getRootNode(){
        return rootNode;
    }
    
    
    public float getPrice(String producto){
        System.out.println("Getting price of " + producto);
        return (Float)productosHash.get(producto);
    }
    
    public String getPriceFormatted(String producto){
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("es","ES"));
        System.out.println(productosHash.toString());
        return nf.format(productosHash.get(producto));
    }
    
    public void populateProductosEnumeration(DefaultMutableTreeNode node){
        
        if (node.isLeaf() && !node.toString().equals("vacío")){
            productos.add(node.toString());
            System.out.println("Added to productos:"+node);
        }
        else{
            System.out.println("Node:"+node+" is not leaf so not added to productos");
            Enumeration children = node.children();
            while (children.hasMoreElements()){
                populateProductosEnumeration((DefaultMutableTreeNode) children.nextElement());
            }
        }
    }
    public void populateCategoriasEnumeration(DefaultMutableTreeNode node){
        if (!node.isLeaf()){ //if node is parent
            categorias.add(node.toString());
            //System.out.println("Added to categorias:"+node);
            Enumeration children = node.children();
            while (children.hasMoreElements()){
                populateCategoriasEnumeration((DefaultMutableTreeNode) children.nextElement());
            }

        }
        else{
                System.out.println("Node:"+node+" is leaf so not added to categories");
        }
    }
    private void listToArray(){
        Iterator itr = categorias.iterator();
        categoriasArray = new String[categorias.size()];
        int i = 0;
        while (itr.hasNext()){
            categoriasArray[i] = itr.next().toString();
            i = i+1;
        }
    }
    public String[] getCategorias(){
        listToArray(); //In order to pass it to ComboBox constructor we need an array and not arraylist
        return categoriasArray;
    }

    public void performFindNode(TreeNode node, String text) {
        System.out.println("Node:"+node.toString()+" comparing with "+text);
        if (node.toString().equals(text)){

            foundNode = (DefaultMutableTreeNode) node;
            System.out.println("Found node:"+node);
        }
        else{
            Enumeration children = node.children();
            while (children.hasMoreElements()){
                performFindNode((DefaultMutableTreeNode)children.nextElement(),text);
            }
        }
    }

    public DefaultMutableTreeNode findNode(String text){
        foundNode = null;
        performFindNode(rootNode,text);
        return foundNode;
    }

    public Boolean isCategoria(String s){
        categorias.clear();
        populateCategoriasEnumeration(rootNode);
        System.out.println(categorias.toString());
        if (categorias.contains(s)){
            return true;
        }
        else{
            System.out.println("Categorias does not contain " + s);
            return false;
        }
    }

    public Boolean isProducto(String s){
        productos.clear();
        populateProductosEnumeration(rootNode);
        System.out.println(productos.toString());
        if (productos.contains(s)){
            return true;
        }
        else{
            System.out.println("Productos does not contain " + s);
            return false;
        }
    }

    public void addCategoria(DefaultMutableTreeNode newNode, DefaultMutableTreeNode fatherNode,JDialog parentDialog){
        if (isCategoria(fatherNode.toString()) && !isCategoria(newNode.toString())){

            if (fatherNode.getFirstChild().toString().equals("vacío")){
                removeNodeFromParent(fatherNode.getFirstLeaf());
                insertNodeInto(newNode, fatherNode, fatherNode.getChildCount());
                insertNodeInto(new DefaultMutableTreeNode("vacío"), fatherNode.getLastLeaf(), 0);
            }
            else{
                insertNodeInto(newNode,fatherNode, fatherNode.getChildCount());
                insertNodeInto(new DefaultMutableTreeNode("vacío"), fatherNode.getLastLeaf(), 0);
            }
        }
        else{
            JOptionPane.showMessageDialog(parentDialog,"Error: No ha seleccionado una categoría padre o ha introuducido una categoria nueva que ya existe");
        }
    }

    public void addProducto(DefaultMutableTreeNode newNode, DefaultMutableTreeNode fatherNode,JDialog parentDialog){
        if (isCategoria(fatherNode.toString()) && !isProducto(newNode.toString()) &&
                !isCategoria(newNode.toString())){
            if (fatherNode.getFirstChild().toString().equals("vacío")){
                removeNodeFromParent(fatherNode.getFirstLeaf());
            }
            insertNodeInto(newNode,fatherNode, fatherNode.getChildCount());
        }
        else{
            JOptionPane.showMessageDialog(parentDialog,"Error: No ha seleccionado una categoría padre o ha introuducido un producto nuevo que ya existe");
        }
    }

    public void addProducto(String text,float price){
        productosHash.put(text, price);
    }

    public Enumeration browse(int key){
        currentNode = (DefaultMutableTreeNode) currentNode.getChildAt(key);
        System.out.println("current node is" + currentNode.toString());
        Enumeration children = currentNode.children();
        return children;

    }
    public Enumeration browse(int key, JLabel label){
        currentNode = (DefaultMutableTreeNode) currentNode.getChildAt(key);
        label.setText(currentNode.toString());
        Enumeration children = currentNode.children();
        return children;
    }

    public void upCurrentNode(JLabel label){
        if (currentNode.equals(rootNode)){
            System.out.println("nothing");
        }
        else{
            currentNode = (DefaultMutableTreeNode) currentNode.getParent();
            label.setText(currentNode.toString());
        }
    }
    public void resetCurrentNode(){
        currentNode = rootNode;
        System.out.println("current node is" + currentNode.toString());
    }
    public DefaultMutableTreeNode getCurrentNode(){
        return currentNode;
    }
}
