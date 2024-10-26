package Usuarios;

import Archivo.HistoriaClinica;
import Interfaces.I_GestionTurnos;

public class Consultante extends Usuario implements I_GestionTurnos {
    private HistoriaClinica historiaClinica;

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
}
