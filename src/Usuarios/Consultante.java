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
