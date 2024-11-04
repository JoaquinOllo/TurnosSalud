package Locaciones;

import Citas.Turno;
import Interfaces.I_CompatibilidadHorarios;

import java.util.ArrayList;

public class Consultorio implements I_CompatibilidadHorarios {
    private Locacion locacion;
    private ArrayList<Turno> turnos;

    public Locacion getLocacion() {
        return locacion;
    }

    public void setLocacion(Locacion locacion) {
        this.locacion = locacion;
    }

    public ArrayList<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(ArrayList<Turno> turnos) {
        this.turnos = turnos;
    }

    public <T extends Turno> boolean citaCompatibleConFranjaHoraria(T turno) {
        return this.locacion.citaCompatibleConFranjaHoraria(turno);

    }
}
