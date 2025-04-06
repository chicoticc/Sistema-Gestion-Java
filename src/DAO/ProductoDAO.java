package DAO;

import Utils.DatabaseUtils;
import java.sql.*;
import config.DatabaseConfig;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ncamp
 */
public class ProductoDAO {

    private final DatabaseConfig conexion;
    DatabaseUtils dbUtils = new DatabaseUtils();

    public ProductoDAO() {
        this.conexion = new DatabaseConfig();
    }

    public DefaultTableModel obtenerModeloTablaProductos() {
        String[] columnNames = {"ID", "Nombre", "Descripción", "Precio", "Stock", "Categoría", "Proveedor"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        String sql = "SELECT p.*, c.nombre_categoria, pr.nombre as nombre_proveedor "
                + "FROM productos p "
                + "JOIN categorias c ON p.id_categoria = c.id_categoria "
                + "JOIN proveedores pr ON p.id_proveedor = pr.id_proveedor";

        try (Connection conn = conexion.establecerConexion(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getInt("precio"),
                    rs.getInt("cantidad"),
                    rs.getString("nombre_categoria"),
                    rs.getString("nombre_proveedor")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        }

        return model;
    }

    public DefaultTableModel obtenerModeloTablaProductosParaCliente() {
        String[] columnNames = {"Seleccionar", "ID", "Nombre", "Descripción", "Precio Unitario", "Stock", "Categoría", "Cantidad"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class; // Checkbox
                }
                if (columnIndex == 7) {
                    return Integer.class; // Cantidad
                }
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 7; // Solo editable checkbox y cantidad
            }
        };

        String sql = "SELECT p.*, c.nombre_categoria FROM productos p JOIN categorias c ON p.id_categoria = c.id_categoria";

        try (Connection conn = conexion.establecerConexion(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] row = {
                    false, // Checkbox inicialmente desmarcado
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getInt("precio"),
                    rs.getInt("cantidad"),
                    rs.getString("nombre_categoria"),
                    1 // Cantidad inicial
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        }

        return model;
    }
    
    public void agregarProducto(String idProducto, String nombre, String descripcion, int precio, int cantidad) {
        try {
            String sqlGuardar = "INSERT INTO Productos (id_producto, nombre, descripcion, precio, cantidad) VALUES " +
                    "('" + idProducto + "','" + nombre + "','" + descripcion + "','" + precio + "','" + cantidad + "')";
            int filas = dbUtils.agregar(sqlGuardar);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void modificarProducto(String idProducto, String nombre, String descripcion, int precio, int cantidad) {
        try {
            String sqlModificar = "UPDATE Productos SET nombre = '" + nombre + "', " +
                    "descripcion = '" + descripcion + "', " +
                    "precio = '" + precio + "', " +
                    "cantidad = '" + cantidad + "' WHERE id_producto = '" + idProducto + "'";
            int registroModificar = dbUtils.eliminarModificar(sqlModificar);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarProducto(String idProducto) {
        try {
            String sqlEliminar = "DELETE FROM Productos WHERE id_producto = " + idProducto;
            int registroEliminado = dbUtils.eliminarModificar(sqlEliminar);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
