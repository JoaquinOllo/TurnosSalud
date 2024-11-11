package Excepciones;

import java.io.IOException;

public class NombreInvalidoException extends IOException {

    public NombreInvalidoException(String nombreUsuario){
        super("El nombre elegido " + nombreUsuario + " ya está tomado.");
    }
}
