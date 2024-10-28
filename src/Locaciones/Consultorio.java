package Locaciones;

import Citas.Cita;
import Interfaces.I_CompatibilidadHorarios;

import java.util.ArrayList;

public class Consultorio implements I_CompatibilidadHorarios {
    private Locacion locacion;
    private ArrayList<Cita> turnos;

    public Locacion getLocacion() {
        return locacion;
    }

    public void setLocacion(Locacion locacion) {
        this.locacion = locacion;
    }

    public ArrayList<Cita> getTurnos() {
        return turnos;
    }

    public void setTurnos(ArrayList<Cita> turnos) {
        this.turnos = turnos;
    }

    public <T extends Cita> boolean citaCompatibleConFranjaHoraria(T turno) {
        this.locacion.citaCompatibleConFranjaHoraria(turno);
    }
}
