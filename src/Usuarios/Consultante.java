package Usuarios;

import Excepciones.UsuarioInvalidoException;
import Interfaces.I_GestionTurnos;

public class Consultante extends Usuario implements I_GestionTurnos {

    public Consultante(String nombreUsuario, String contrasenha) throws UsuarioInvalidoException {
        super(nombreUsuario, contrasenha);
    }

    public Consultante()  {
        super();
    }

    @Override
    public boolean agendaTurnos() {
        return true;
    }

    @Override
    public boolean confirmaTurnos() {
        return true;
    }

    @Override
    public boolean cancelaTurnos() {
        return true;
    }

    @Override
    public boolean reprogramaTurnos() {
        return true;
    }

    @Override
    public boolean soloAutoGestion() {
        return true;
    }

    @Override
    public String toString() {
        return this.getNombreCompleto();
    }

}
