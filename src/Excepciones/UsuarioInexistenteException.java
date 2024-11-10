package Excepciones;

public class UsuarioInexistenteException extends Exception {
    public UsuarioInexistenteException(String nombreUsuario) {
        super ("Usuario no encontrado: " + nombreUsuario);
    }

    public UsuarioInexistenteException() {

    }
}
