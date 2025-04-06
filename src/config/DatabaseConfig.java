package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    
    String database = "inventario";
    String url = "jdbc:mysql://localhost:3306/" + database;
    String usuario = "root";
    String password = "administrador";
    
    Connection connectionStatus = null;
    
    public Connection establecerConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connectionStatus = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexión exitosa...!! " + database);
        } catch (ClassNotFoundException | SQLException e) {
             System.out.println("Error al conectar: " + e.getMessage());
        }
        return connectionStatus;
    }
    
    public void cerrarConexion() {
        try {
            if(connectionStatus != null)
                connectionStatus.close();
            System.out.println("Conexion cerrada...");
        } catch (Exception e) {
            System.err.println("Error...");
        }
    }
}
