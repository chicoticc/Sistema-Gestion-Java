package DAO;

import Utils.DatabaseUtils;
import model.Venta;

/**
 *
 * @author leoar
 */
public class VentaDAO {

    DatabaseUtils dbUtils = new DatabaseUtils();

    public void crearVenta(Venta venta) {
        try {
            String sqlCrear = "INSERT INTO ventas (id_cliente, fecha_venta, total_venta) "
                    + "VALUES ('" + venta.getIdCliente() + "', '" + venta.getFechaVenta() + "', '"
                    + venta.getTotal() + "')";
            int filas = dbUtils.agregar(sqlCrear);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void editarVenta(Venta venta) {
        try {
            String sqlModificar = "UPDATE ventas SET "
                    + "id_cliente = '" + venta.getIdCliente() + "', "
                    + "fecha_venta = '" + venta.getFechaVenta() + "', "
                    + "total_venta = '" + venta.getTotal() + "', "
                    + "WHERE id_venta = '" + venta.getIdVenta() + "'";
            int registroModificado = dbUtils.eliminarModificar(sqlModificar);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void eliminarVenta(Venta venta) {
        try {
            String sqlEliminar = "DELETE FROM ventas WHERE id_venta = " + venta.getIdVenta();
            int registroEliminado = dbUtils.eliminarModificar(sqlEliminar);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
