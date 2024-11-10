package Locaciones;

import Citas.Turno;
import Interfaces.I_CompatibilidadHorarios;
import Utilidades.Agenda;
import Utilidades.FranjaHoraria;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Consultorio implements I_CompatibilidadHorarios {
    private Sede sede;
    private Agenda<Turno> turnos = new Agenda<>();
    private int numero;

    public Consultorio(int numero, Sede sede) {
        this.numero = numero;
        this.sede = sede;
    }

    public Consultorio() {
    }

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

    public Agenda<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(Agenda<Turno> turnos) {
        this.turnos = turnos;
    }

    public <T extends Turno> boolean citaCompatibleConFranjaHoraria(T turno) {
        return this.sede.citaCompatibleConFranjaHoraria(turno);
    }

    public void agendarTurno(Turno turno){
        this.turnos.add(turno);
    }

    public HashSet<LocalTime> getHorariosHabilitados(int duracionTurnoEnMinutos) {
        HashSet<LocalTime> horariosHabilitados = new HashSet<>();
        for (FranjaHoraria franja : this.sede.getHorarios()){
            horariosHabilitados.addAll(franja.getHorariosHabilitados(this.turnos, duracionTurnoEnMinutos));
        }
        return horariosHabilitados;
    }

    @Override
    public String toString() {
        return "Consultorio: " +
                "sede " + sede +
                ", numero " + numero;
    }

    public void setTurnos(ArrayList<Turno> turnos) {
        this.turnos = new Agenda<>(turnos);
    }
}
