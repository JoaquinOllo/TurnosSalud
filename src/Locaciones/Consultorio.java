package Locaciones;

import Citas.Turno;
import Interfaces.I_CompatibilidadHorarios;

import java.util.ArrayList;

public class Consultorio implements I_CompatibilidadHorarios {
    private Sede sede;
    private ArrayList<Turno> turnos;

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public ArrayList<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(ArrayList<Turno> turnos) {
        this.turnos = turnos;
    }

    public <T extends Turno> boolean citaCompatibleConFranjaHoraria(T turno) {
        return this.sede.citaCompatibleConFranjaHoraria(turno);
    }
}
