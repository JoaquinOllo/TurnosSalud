package Usuarios;

import Interfaces.I_GestionAdministrativa;
import Interfaces.I_GestionConsultorios;
import Interfaces.I_GestionHC;
import Interfaces.I_GestionTurnos;

public class Administrador extends Usuario implements I_GestionTurnos, I_GestionAdministrativa, I_GestionHC, I_GestionConsultorios {
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

    @Override
    public boolean lecturaHCPropia() {
        return false;
    }

    @Override
    public boolean editaHCPropia() {
        return false;
    }

    @Override
    public boolean lecturaHC() {
        return false;
    }

    @Override
    public boolean editaHC() {
        return false;
    }
}
