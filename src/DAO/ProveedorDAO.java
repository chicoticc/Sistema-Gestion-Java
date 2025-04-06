package DAO;

import Utils.DatabaseUtils;

/**
 *
 * @author leoar
 */
public class ProveedorDAO {

    DatabaseUtils dbUtils = new DatabaseUtils();

    public void crearProveedor(String nombre, String direccion, String telefono, String email) {
        try {
            String sqlCrear = "INSERT INTO proveedores (nombre, direccion, telefono, email) "
                    + "VALUES ('" + nombre + "', '" + direccion + "', '" + telefono + "', '" + email + "')";
            int filas = dbUtils.agregar(sqlCrear);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void modificarProveedor(String idProveedor, String nombre, String direccion, String telefono, String email) {
        try {
            String sqlModificar = "UPDATE Proveedores SET "
                    + "nombre = '" + nombre + "', "
                    + "direccion = '" + direccion + "', "
                    + "telefono = '" + telefono + "', "
                    + "email = '" + email + "' "
                    + "WHERE id_proveedor = '" + idProveedor + "'";

            int registroModificado = dbUtils.eliminarModificar(sqlModificar);
        } catch (Exception e) {
            throw new RuntimeException("Error al modificar el proveedor", e);
        }
    }

    public void eliminarProveedor(String idProveedor) {
        try {
            String sqlEliminar = "DELETE FROM proveedor WHERE id_proveedor" + idProveedor;
            int registroEliminado = dbUtils.eliminarModificar(sqlEliminar);
        } catch (Exception e) {
            throw new RuntimeException("Error al modificar el proveedor", e);
        }
    }

}
