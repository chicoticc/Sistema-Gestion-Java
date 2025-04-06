package model;

import config.DatabaseConfig;
import controlador.UsuarioControlador;

public class Principal {

    public static void main(String[] args) {
        DatabaseConfig connection = new DatabaseConfig();
        connection.establecerConexion();
        
        
       views.vistaPrincipal panelPrincipal= new views.vistaPrincipal();
       
       panelPrincipal.setVisible(true);
        
        
        
    }

}
