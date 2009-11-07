package amltpv;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;
import java.io.File;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.ImageIcon;
import org.apache.commons.io.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Adam
 */
public class DataBase {
    private String framework = "embedded";
    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String protocol = "jdbc:derby:";
    private Utils utils = AmltpvView.util;
    private String dbName = "baseDeDatos";
    private Boolean exists = false;
    
    /*Constructor. Carga la base de datos*/
    public DataBase(){
        try {
            Class.forName(driver).newInstance();
            utils.setStatus("Base de datos cargada");
            System.out.println("Loaded the Database driver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("\nUnable to load the JDBC driver " + driver);
            System.err.println("Please check your CLASSPATH.");
            cnfe.printStackTrace(System.err);
        } catch (InstantiationException ie) {
            System.err.println(
                        "\nUnable to instantiate the JDBC driver " + driver);
            ie.printStackTrace(System.err);
        } catch (IllegalAccessException iae) {
            System.err.println(
                        "\nNot allowed to access the JDBC driver " + driver);
            iae.printStackTrace(System.err);
        }
        if (utils.existeArchivo("baseDeDatos/db.lck")){
            utils.setStatus("Se ha encontrado la base de datos");
            exists = true;
        }
        else{
            crearBaseDatos();
        }
    }

    public DataBase(boolean son){ //It's called from AmltpvClient
        System.out.println("Database instance is a son");
    }

    Boolean checkIfExists(){
        return exists;
    }
    Statement conectar(){
        try{
            Connection conn = null;
            Statement s = null;
            Properties props = new Properties(); // connection properties
            // providing a user name and password is optional in the embedded
            // and derbyclient frameworks
            props.put("user", "user1");
            props.put("password", "user1");
            conn = DriverManager.getConnection(protocol + dbName
                    + ";create=true", props);
            return conn.createStatement();
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(
                null,
                    "Error de base de datos!" + e.toString());
            return null;
        }
    }

    void crearBaseDatos(){
        try{
            //Table de autentificación - NO ESTA ENCRYPTADA
            //TO-DO: Hacerle un "digest", aunque no es tan imporatante
            conectar().execute("create table auth(" +
                    "usuario varchar(20),codigo varchar(100))");
            //Tabla de derechos que tiene cada usuario
            conectar().execute("create table roles(" +
                    "usuario varchar(20),role varchar(20))");
            conectar().execute("create table roles_disponibles(" +
                    "role varchar(20)," +
                    "peso decimal(1,0))");
            //Tabla de configuración
            conectar().execute("create table conf(" +
                    "campo varchar(20),valor varchar(100))");
            //Pool de mesas
            conectar().execute("create table mesas_pool(" +
                    "mesa varchar(2),producto varchar(40))");

            

            //Cobradas
            conectar().execute("create table cobradas("+
                    "id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                    "mesa varchar(2)," +
                    "dia date," +
                    "hora time," +
                    "producto varchar(30)," +
                    "precio decimal(6,2))");

            //Caja
            conectar().execute("create table caja("+
                    "id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"+
                    "dia_apertura date,"+
                    "hora_apertura time,"+
                    "dia_cierre date,"+
                    "hora_cierre time,"+
                    "firma_usuario varchar(20),"+
                    "total decimal (10,2))");

            //Gastos
            conectar().execute("create table gastos("+
                    "dia date," +
                    "hora time," +
                    "concepto varchar(30)," +
                    "precio decimal(10,2)," +
                    "firma_usuario varchar(20))");

            //Cambio
            conectar().execute("create table cambio("+
                    "id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"+
                    "dia_apertura date,"+
                    "hora_apertura time,"+
                    "dia_cierre date,"+
                    "hora_cierre time,"+
                    "total decimal (10,2))");

            insertConfItem("numMesas","0");
            insertConfItem("nombreNegocio","nulo");
            insertConfItem("slogan","nulo");
            insertConfItem("direccion","nulo");
            insertConfItem("telefono","nulo");
            insertConfItem("cif","nulo");
            insertConfItem("email","nulo");
            insertConfItem("web","nulo");
            insertConfItem("cajaCerrada","nulo");
            insertRoleDisponible("Administrador","9");
            insertRoleDisponible("Supervisor","8");
            insertRoleDisponible("Empleado","7");
            insertRoleDisponible("Invitado","6");
        }
        catch (SQLException e){
            e.printStackTrace();

            try{
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            }

            catch (SQLException e2){
                e2.printStackTrace();
            }

            try{
                FileUtils.deleteDirectory(new File("baseDeDatos"));
                new File("derby.log").delete();
            }

            catch (java.io.IOException e3){
                e3.printStackTrace();
            }
        }
    }

    BigDecimal getYesterdayCambio(){
        String s = "select total" + 
                " from cambio where id=(select max(id) from cambio)";
        System.out.println(s);
        BigDecimal result = new BigDecimal(0);
        try{
            ResultSet rs = conectar().executeQuery(s);
            rs.next();
            result = rs.getBigDecimal(1);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        if (result == null){
            return new BigDecimal(0);
        }
        return result;
    }

    void insertCambio(BigDecimal cambio, String fechaCierre,String horaCierre ){
        String fechaApertura = AmltpvView.util.getTodayString();
        String horaApertura = AmltpvView.util.getTimeString();
        
        try{
            String s = "INSERT INTO cambio VALUES(DEFAULT,'"+
                    fechaApertura+"','"+horaApertura+
                    "','"+ fechaCierre + "','"+
                    horaCierre +"'," + cambio + ")";
            System.out.println(s);
            conectar().execute(s);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    BigDecimal totalCaja(){
        String diaApertura = getStringValueFromCaja("dia_apertura");
        String horaApertura = getStringValueFromCaja("hora_apertura");
        String s = "select sum(precio) from cobradas where " +
                "dia >= '" + diaApertura + "'" +
                " and hora >= '" + horaApertura + "'";
        System.out.println(s);
        BigDecimal result = new BigDecimal(0);
        try{
            ResultSet rs = conectar().executeQuery(s);
            rs.next();
            result = rs.getBigDecimal(1);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        if (result == null){
            return new BigDecimal(0);
        }
        return result;
    }

    BigDecimal totalGastos(){
        String diaApertura = getStringValueFromCaja("dia_apertura");
        String horaApertura = getStringValueFromCaja("hora_apertura");
        String s = "select sum(precio) from gastos where " +
                "dia >= '" + diaApertura + "'" +
                " and hora >= '" + horaApertura + "'";
        System.out.println(s);
        BigDecimal result = new BigDecimal(0);
        try{
            ResultSet rs = conectar().executeQuery(s);
            rs.next();
            result = rs.getBigDecimal(1);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        if (result == null){
            return new BigDecimal(0);
        }
        return result;
    }
    void addApertura(){
        System.out.println("Insetando apertura en base de datos");
        String fecha = AmltpvView.util.getTodayString();
        String hora = AmltpvView.util.getTimeString();
        try{
            String s = "INSERT INTO caja VALUES(DEFAULT,'"+fecha+"','"+hora+"',null,null,null,null)";
            System.out.println(s);
            conectar().execute(s);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }


    void addCierre(String usuario, BigDecimal total){
        System.out.println("Insertando cierre en base de datos");
        String fecha = AmltpvView.util.getTodayString();
        String hora = AmltpvView.util.getTimeString();
        try{
            
            ResultSet rs = conectar().executeQuery("select MAX(id) from caja");
            rs.next();
            int id = rs.getInt("1");
            
            
            String s = "UPDATE caja SET dia_cierre='"+fecha+"' WHERE id="+id;
            System.out.println(s);
            conectar().execute(s);
            s = "UPDATE caja SET hora_cierre='"+hora+"' WHERE id="+id;
            System.out.println(s);
            conectar().execute(s);
            s = "UPDATE caja SET firma_usuario='"+usuario+"' WHERE id="+id;
            System.out.println(s);
            conectar().execute(s);
            s = "UPDATE caja SET total="+total+" WHERE id="+id;
            System.out.println(s);
            conectar().execute(s);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    String getStringValueFromCaja(String campo){
        String s = "select " + campo + " from user1.caja where id=(select max(id) from user1.caja)";
        System.out.println(s);
        String result = null;
        try {
            ResultSet rs = conectar().executeQuery(s);
            rs.next();
            result = rs.getString(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return result;
    }
    
    void addProductoToMesasPool(String mesa,String producto,boolean cocina){
        System.out.println("adding producto called");
        try{
            String s = "INSERT INTO mesas_pool VALUES('"+ mesa + "','" +producto + "')";
            System.out.println(s);
            conectar().execute(s);
            if (cocina){ //Si es una comanda que se envia a cocina y no algo relacionado con borrar
                ThreadServidor.servidor.comandaCocina("comanda@"+mesa+"@"+producto);
                AmltpvView.mesasArray[Integer.parseInt(mesa)].setIcon(new ImageIcon("imgs/mesaBusy.jpg"));
                ThreadServidor.servidor.propagate("mesaCambiada", mesa);
            }
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    void addGasto(String gasto,BigDecimal precio,String user){
        System.out.println("add gasto called");
        String dia = AmltpvView.util.getTodayString();
        String hora = AmltpvView.util.getTimeString();
        try{
            String s = "INSERT INTO gastos VALUES('"+ dia +"','" +
                    hora + "','" +gasto + "'," + precio + ",'"+user+"')";
            System.out.println(s);
            conectar().execute(s);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    Vector queryGastos(String dia_apertura,String hora_apertura,
            String dia_cierre,String hora_cierre){
        
        Vector resultVector = new Vector();
        
        try{
            String s = "select dia,hora,concepto,precio,firma_usuario from gastos where " +
                    "dia >= '" + dia_apertura + "'" +
                    " and hora >= '" + hora_apertura + "'" +
                    " and dia <= '" + dia_cierre + "'" +
                    " and hora <= '" + hora_cierre + "'";
            System.out.println(s);
            ResultSet rs = conectar().executeQuery(s);
            Object[] temp = new Object[5];
            while (rs.next()){
                temp[0] = rs.getObject(1);
                temp[1] = rs.getObject(2);
                temp[2] = rs.getObject(3);
                temp[3] = rs.getObject(4);
                temp[4] = rs.getObject(5);
                resultVector.add(new Object[] {temp[0],temp[1],temp[2],temp[3],temp[4]});
                System.out.println("Read from gastos " + temp[2]);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return resultVector;

    }

    void emptyPool(String mesa){
        System.out.println("emptying pool:"+mesa);
        try{
            String s = "DELETE FROM mesas_pool WHERE mesa='"+mesa+"'";
            System.out.println(s);
            conectar().execute(s);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    void fromPoolToCobradas(String mesa){
        
        ResultSet rs = null;
        System.out.println("from pool to mesas called");
        String s = "select * from mesas_pool where mesa='"+mesa+"'";
        System.out.println(s);
        try {
            rs = conectar().executeQuery(s);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        String dia = AmltpvView.util.getTodayString();
        String hora = AmltpvView.util.getTimeString();
        String producto = null;
        BigDecimal precio;
                
        try{
            while (rs.next()){
                producto = rs.getString("producto");
                precio = new BigDecimal(new Float(AmltpvView.productosModel.getPrice(producto)).toString());
                System.out.println("Precio a insertar en base de datos " + precio.toString());
                s = "INSERT INTO cobradas VALUES(DEFAULT,'"+
                                    mesa + "','" +
                                    dia + "','"+
                                    hora + "','"+
                                    producto + "',"+
                                    precio + ")";
                System.out.println(s);
                conectar().execute(s);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
     void addHost(String hostname){
        try{
            String s = "INSERT INTO hosts VALUES('"+ hostname + "')";
            System.out.println(s);
            conectar().execute(s);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    String decodeRemoteQuery(String msg) {
        System.out.println("Decoding remote query:" + msg);
        
        if (msg.equals("numMesas")){
            return queryValor("numMesas");
        }
        else if (msg.contains("mesaContents")){
            String[] a = msg.split(":");
            return AmltpvView.util.enumToString(queryMesaContents(a[1]));
        }
        
        else if (msg.contains("cantOfProducto")){
            String[] a = msg.split(":");
            return Integer.toString(AmltpvView.db.queryCantOfProducto(a[1],a[2]));
        }
        else if (msg.startsWith("queryValor")){
            String[] queryArray = msg.split(":");
            return queryValor(queryArray[1]);
        }
        else{
            return null;
        }
    }

    void deleteFromMesasPool(String mesa, String producto) {
        int cant = queryCantOfProducto(mesa,producto);
        try{
            String s = "DELETE FROM mesas_pool WHERE producto ='"+ producto + "'" +
                    "AND MESA='"+mesa+"'";
            System.out.println(s);
            conectar().execute(s);
            ThreadServidor.servidor.comandaCocina("borrar@"+mesa+"@"+producto);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        for (int i=0;i<cant-1;i++){
                addProductoToMesasPool(mesa,producto,false);
        }

    }
    void deleteHost(String hostname) {
        try{
            String s = "DELETE FROM hosts WHERE hostname ='"+ hostname + "'";
            System.out.println(s);
            conectar().execute(s);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    String insertFromRemoteHost(String query){
        try{
            System.out.println("Remote update query:" + query);
            conectar().execute(query);
            return "updated";
        }
        catch (SQLException e){
            AmltpvView.util.log(e+"-query:"+query);
            return "not updated";
        }
    }

    String deleteFromRemoteHost(String query) {
        try{
            System.out.println("Remote update query:" + query);
            conectar().execute(query);
            return "updated";
        }
        catch (SQLException e){
            AmltpvView.util.log(e+"-query:"+query);
            return "not updated";
        }
    }

    Enumeration queryHosts(){
        String s;
        ResultSet rs;
        Vector resultVector = new Vector();
        try{
            s = "SELECT hostname from hosts";
            System.out.println(s);
            rs = conectar().executeQuery(s);
            while (rs.next()){
                resultVector.add(rs.getString("hostname"));
            }
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return resultVector.elements();
    }
    Enumeration queryMesaContents(String mesa){
        String s;
        ResultSet rs;
        Vector resultVector = new Vector();
        try{
            s = "SELECT producto from mesas_pool WHERE mesa='" + mesa + "'";
            System.out.println(s);
            rs = conectar().executeQuery(s);
            while (rs.next()){
                resultVector.add(rs.getString("producto"));
            }
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return resultVector.elements();
    }
    int queryCantOfProducto(String mesa,String producto){
        String s;
        ResultSet rs;
        try{
            s = "SELECT count(producto) from mesas_pool WHERE producto='" + producto + "'" + "and mesa='" + mesa + "'";
            System.out.println(s);
            rs = conectar().executeQuery(s);
            rs.next();
            return rs.getInt(1);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
            return 0;
        }
    }

    int queryCantOfProductosInMesa(String mesa){
        String s;
        ResultSet rs;
        try{
            s = "SELECT count(producto) from mesas_pool WHERE mesa='" + mesa + "'";
            System.out.println(s);
            rs = conectar().executeQuery(s);
            rs.next();
            return rs.getInt(1);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
            return 0;
        }
    }

    void insertConfItem(String campo, String valor){
        try{
            String s = "INSERT INTO conf VALUES('"+ campo + "','" + valor + "')";
            System.out.println(s);
            conectar().execute(s);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
    }


    void insertAuthItem(String usuario, String codigo, String role){
        try{
            String s = "INSERT INTO auth VALUES('"+ usuario + "','" + codigo + "')";
            //System.out.println(s); por seguridad no imprimimos
            conectar().execute(s);
            s = "INSERT INTO roles VALUES('"+ usuario + "','" + role + "')";
            //System.out.println(s); por seguridad no imprimimos
            conectar().execute(s);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }

    }

    void insertRoleDisponible(String role,String peso){
        try{
            String s = "INSERT INTO roles_disponibles VALUES('"+role+"',"+peso+")";
            //System.out.println(s); por seguridad no imprimimos
            conectar().execute(s);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    void updateConfItem(String campo, String valor){
        try{
            String s = "UPDATE conf SET valor='"+valor+"' WHERE campo='"+campo+"'";
            System.out.println(s);
            conectar().execute(s);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    String queryValor(String campo){
        try{
            String s = "SELECT valor from conf WHERE campo='" + campo + "'";
            System.out.println(s);
            ResultSet rs = conectar().executeQuery(s);
            rs.next();
            return rs.getString("valor");
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    String queryCodigo(String usuario){
        try{
            String s = "SELECT codigo from auth WHERE usuario='" + usuario + "'";
            System.out.println(s);
            ResultSet rs = conectar().executeQuery(s);
            rs.next();
            return rs.getString("codigo");
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    String queryRole(String usuario){
        try{
            String s = "SELECT role from roles WHERE usuario='" + usuario + "'";
            System.out.println(s);
            ResultSet rs = conectar().executeQuery(s);
            rs.next();
            return rs.getString("role");
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    BigDecimal queryPeso(String role){
        try{
            String s = "SELECT peso from roles_disponibles WHERE role='" + role + "'";
            System.out.println(s);
            ResultSet rs = conectar().executeQuery(s);
            rs.next();
            return rs.getBigDecimal("peso");
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    String [] queryAllUsuarios(){
        try{
            String s = "SELECT count(usuario) from auth";
            ResultSet rs = conectar().executeQuery(s);
            rs.next();
            int length = rs.getInt(1);
            System.out.println(length);

            s = "SELECT usuario from auth";
            rs = conectar().executeQuery(s);
            int i = 0;
            String[] a = new String[length];

            while (rs.next()){
                a[i] = rs.getString("usuario");
                System.out.println(a[i]);
                i = i+1;
            }
            return a;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

    String [] queryAllRoles(){
        try{
            String s = "SELECT count(role) from roles_disponibles";
            ResultSet rs = conectar().executeQuery(s);
            rs.next();
            int length = rs.getInt(1);
            System.out.println(length);

            s = "SELECT role from roles_disponibles";
            rs = conectar().executeQuery(s);
            int i = 0;
            String[] a = new String[length];

            while (rs.next()){
                a[i] = rs.getString("role");
                System.out.println(a[i]);
                i = i+1;
            }
            return a;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

}
