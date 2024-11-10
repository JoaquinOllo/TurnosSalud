package Locaciones;

import Citas.Turno;
import Interfaces.I_CompatibilidadHorarios;
import Utilidades.Agenda;
import Utilidades.FranjaHoraria;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;

public class Consultorio implements I_CompatibilidadHorarios {
    private Sede sede;
    private ArrayList<Turno> turnos;
    private int numero;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

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

    @Override
    public HashSet<LocalTime> getHorariosHabilitados(Agenda<Turno> turnos, int duracionTurnoEnMinutos) {
        HashSet<LocalTime> horariosHabilitados = new HashSet<>();

        for (FranjaHoraria franja : this.sede.getHorarios()){
            horariosHabilitados.addAll(franja.getHorariosHabilitados(turnos, duracionTurnoEnMinutos));
        }

        return horariosHabilitados;
    }

    @Override
    public String toString() {
        return "Consultorio: " +
                "sede " + sede +
                ", numero " + numero;
    }
}
