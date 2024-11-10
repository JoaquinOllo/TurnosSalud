package Utilidades;

import Citas.Turno;
import Enumeradores.Especialidad;
import Locaciones.Consultorio;
import Locaciones.Sede;
import Usuarios.Profesional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Agenda<T extends Turno> extends ArrayList<T> {

    public Agenda(ArrayList<T> turnos) {
        super(turnos);
    }

    public Agenda() {
    }

    public boolean franjaDisponible(T turno) {
        Agenda<T> agendaFiltrada = this.filtrarPorProfesional(turno.getProfesional()).filtrarPorConsultorio(turno.getConsultorio());
        //completar
        return agendaFiltrada.stream().noneMatch(t -> t.colisiona(turno));//recorre el array de agenda filtrada
    }

    public Agenda<T> filtrarPorConsultorio(Consultorio consultorio) {
        Agenda <T> turnosPorConsultorio=new Agenda<>();
        for (T turno:this){
            if(turno.getConsultorio().equals(consultorio)){
                turnosPorConsultorio.add(turno);
            }
        }
        return turnosPorConsultorio;
    }

    public Agenda<T> filtrarPorEspecialidad(Especialidad especialidad) {
        Agenda <T> turnosPorEspecialidad=new Agenda<>();
        for (T turno:this){
            if(turno.getProfesional().getEspecialidad().equals(especialidad)){
                turnosPorEspecialidad.add(turno);
            }
        }
        return turnosPorEspecialidad;
    }

    public Agenda<T> filtrarPorProfesional(Profesional profesional) {
        Agenda<T> turnosPorProfesional=new Agenda<>();
        for(T turno:this){
            if(turno.getProfesional() != null){
                if (turno.getProfesional().equals(profesional)) {
                    turnosPorProfesional.add(turno);
                }
            }
        }
        return turnosPorProfesional;
    }

    public Agenda<T> filtrarPorSede(Sede sede) {
        Agenda<T> turnosPorSede=new Agenda<>();
        for(T turno:this){
            if(turno.getSede().equals(sede)){
                turnosPorSede.add(turno);
            }
        }
        return turnosPorSede;
    }

    public Agenda<T> filtrarPorDia(LocalDate dia) {
        Agenda<T> turnosPorDia =new Agenda<>();
        for(T turno:this){
            if (turno.getDia() != null){
                if(turno.getDia().equals(dia)){
                    turnosPorDia.add(turno);
                }
            }
        }
        return turnosPorDia;
    }

    public Agenda<T> filtrarPorHoraInicio(LocalTime horario) {
        Agenda<T> turnosPorHorario =new Agenda<>();
        for(T turno:this){
            if(turno.getHoraInicio().equals(horario)){
                turnosPorHorario.add(turno);
            }
        }
        return turnosPorHorario;
    }

    public Agenda<T> filtrarPorHoraFin(LocalTime horario) {
        Agenda<T> turnosPorHorario =new Agenda<>();
        for(T turno:this){
            if(turno.getHoraInicio().equals(horario)){
                turnosPorHorario.add(turno);
            }
        }
        return turnosPorHorario;
    }

    public Agenda<T> filtrarPorPeriodo(FranjaHoraria franja) {
        Agenda<T> turnosPorPeriodo =new Agenda<>();
        for(T turno:this){
            if(turno.getFranjaHoraria().citaCompatibleConFranjaHoraria(franja)){
                turnosPorPeriodo.add(turno);
            }
        }
        return turnosPorPeriodo;
    }

    @Override
    public String toString() {
        return "Agenda: " + super.toString();
    }
}