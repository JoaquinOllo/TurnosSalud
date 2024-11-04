package Usuarios;

import Interfaces.I_GestionAdministrativa;
import Interfaces.I_GestionConsultorios;
import Interfaces.I_GestionTurnos;

public class Administrativo extends Usuario implements I_GestionTurnos, I_GestionAdministrativa, I_GestionConsultorios {
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
        return true;
    }

    @Override
    public boolean administraConsultorios() {
        return true;
    }
}
