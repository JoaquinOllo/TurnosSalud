package Excepciones;

import java.io.IOException;

public class UsuarioInvalidoException extends IOException {

    public UsuarioInvalidoException(String nombreUsuario){
        super("El nombre de usuario " + nombreUsuario + " ya está tomado.");
    }
}
