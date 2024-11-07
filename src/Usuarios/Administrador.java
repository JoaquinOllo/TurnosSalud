package Usuarios;

import Excepciones.UsuarioInvalidoException;
import Interfaces.I_GestionAdministrativa;
import Interfaces.I_GestionConsultorios;
import Interfaces.I_GestionHC;
import Interfaces.I_GestionTurnos;

public class Administrador extends Usuario implements I_GestionTurnos, I_GestionAdministrativa, I_GestionConsultorios {
    public Administrador(String nombreUsuario, String contrasenha) throws UsuarioInvalidoException {
        super(nombreUsuario, contrasenha);
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
    public boolean administraUsuarios() {
        return true;
    }

    @Override
    public boolean defineHorariosConsultorios() {
        return false;
    }

    @Override
    public boolean administraConsultorios() {
        return false;
    }

}
