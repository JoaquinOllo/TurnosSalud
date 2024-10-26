package Usuarios;

import Interfaces.I_GestionAdministrativa;
import Interfaces.I_GestionConsultorios;
import Interfaces.I_GestionTurnos;

public class Administrativo extends Usuario implements I_GestionTurnos, I_GestionAdministrativa, I_GestionConsultorios {
    @Override
    public boolean agendaTurnos() {
        return false;
    }

    @Override
    public boolean confirmaTurnos() {
        return false;
    }

    @Override
    public boolean aceptaSobreturnos() {
        return false;
    }

    @Override
    public boolean cancelaTurnos() {
        return false;
    }

    @Override
    public boolean reprogramaTurnos() {
        return false;
    }

    @Override
    public boolean administraUsuarios() {
        return false;
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
