package DAO;

import model.Usuario;
import config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final DatabaseConfig conexion;

    public UsuarioDAO() {
        this.conexion = new DatabaseConfig();
    }

    /**
     * Obtiene todos los usuarios de la base de datos
     * @return Array con todos los usuarios registrados
     */
    public Usuario[] obtenerTodosUsuarios() {
        String sql = "SELECT id_user, username, password, nombre, apellido, email, telefono, direccion, tipoUsuario FROM users";
        List<Usuario> listaUsuarios = new ArrayList<>();

        try (Connection conn = conexion.establecerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    rs.getString("tipoUsuario")
                );
                usuario.setIdUser(rs.getInt("id_user"));
                listaUsuarios.add(usuario);
            }

            // Convertir la List a array
            return listaUsuarios.toArray(new Usuario[0]);

        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
            return new Usuario[0]; // Retorna array vacío si hay error
        }
    }
     public Usuario autenticar(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = conexion.establecerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("tipoUsuario")
                    );
                    usuario.setIdUser(rs.getInt("id_user"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en autenticación: " + e.getMessage());
        }
        return null;
    }
     public boolean registrarUsuario(Usuario nuevoUsuario) {
    // Verificar si el username ya existe
    if (existeUsername(nuevoUsuario.getUsername())) {
        System.err.println("Error: El nombre de usuario ya está en uso");
        return false;
    }

    String sql = "INSERT INTO users (username, password, nombre, apellido, email, telefono, direccion, tipoUsuario) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, 'cliente')";  // Tipo fijo como 'cliente' para registros
    
    try (Connection conn = conexion.establecerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        
        pstmt.setString(1, nuevoUsuario.getUsername());
        pstmt.setString(2, nuevoUsuario.getPassword()); // En producción usar hash
        pstmt.setString(3, nuevoUsuario.getNombre());
        pstmt.setString(4, nuevoUsuario.getApellido());
        pstmt.setString(5, nuevoUsuario.getEmail());
        pstmt.setString(6, nuevoUsuario.getTelefono());
        pstmt.setString(7, nuevoUsuario.getDireccion());
        
        int affectedRows = pstmt.executeUpdate();
        
        if (affectedRows == 0) {
            return false;
        }
        
        // Obtener el ID generado
        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                nuevoUsuario.setIdUser(generatedKeys.getInt(1));
            }
        }
        
        return true;
    } catch (SQLException e) {
        System.err.println("Error al registrar usuario: " + e.getMessage());
        return false;
    }
}

private boolean existeUsername(String username) {
    String sql = "SELECT 1 FROM users WHERE username = ?";
    
    try (Connection conn = conexion.establecerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, username);
        try (ResultSet rs = pstmt.executeQuery()) {
            return rs.next(); // Retorna true si encuentra coincidencia
        }
    } catch (SQLException e) {
        System.err.println("Error al verificar username: " + e.getMessage());
        return true; // Por seguridad, asumimos que existe si hay error
    }
}
    
    
}