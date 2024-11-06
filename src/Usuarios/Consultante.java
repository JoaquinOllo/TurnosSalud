package Usuarios;

import Interfaces.I_GestionTurnos;

public class Consultante extends Usuario implements I_GestionTurnos {

    @Override
    public boolean agendaTurnos() {
        return false;
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
}
