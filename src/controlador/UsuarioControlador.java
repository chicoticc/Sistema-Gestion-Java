package controlador;

import DAO.UsuarioDAO;
import model.Usuario;

public class UsuarioControlador {
    private final UsuarioDAO usuarioDAO;

    public UsuarioControlador() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public void mostrarTodosUsuarios() {
        Usuario[] usuarios = usuarioDAO.obtenerTodosUsuarios();
        
        if (usuarios.length == 0) {
            System.out.println("No hay usuarios registrados en el sistema.");
            return;
        }
        
        System.out.println("\n=== LISTADO COMPLETO DE USUARIOS ===");
        System.out.printf("%-5s %-15s %-20s %-25s %-10s%n", 
                         "ID", "USERNAME", "NOMBRE COMPLETO", "EMAIL", "TIPO", "PASS");
        
        for (Usuario usuario : usuarios) {
            System.out.printf("%-5d %-15s %-20s %-25s %-10s%n",
                usuario.getIdUser(),
                usuario.getUsername(),
                usuario.getNombre() + " " + usuario.getApellido(),
                usuario.getEmail(),
              
                usuario.getTipoUsuario(),   
                usuario.getPassword());
        }
    }
    
    
}