package Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import config.DatabaseConfig;

public class DatabaseUtils {

    public Statement sentencia;
    public ResultSet resultado;

    DatabaseConfig connect = new DatabaseConfig();

    public ResultSet query(String sql) {
        try {
            sentencia = connect.establecerConexion().createStatement();
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        return resultado;
    }

    public int agregar(String sql) {
        int filaAfectada = 0;
        Connection conexion = null;
        Statement sentencia = null;

        try {
            conexion = connect.establecerConexion();
            sentencia = conexion.createStatement();
            filaAfectada = sentencia.executeUpdate(sql);
        } catch (Exception e) {
            System.err.println("Error" + e);
        }
        return filaAfectada;
    }

    public int eliminarModificar(String sql) {
        int datos = 0;
        try {
            sentencia = connect.establecerConexion().createStatement();
            datos = sentencia.executeUpdate(sql);
        } catch (Exception e) {
            System.err.println("Error" + e);
        }
        return datos;
    }

}
