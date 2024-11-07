package Utilidades;

import Citas.Turno;
import Locaciones.Consultorio;
import Locaciones.Sede;
import Usuarios.Profesional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Agenda<T extends Turno> extends ArrayList<T> {

    public boolean franjaDisponible(T turno) {
        Agenda<T> agendaFiltrada = this.filtrarPorProfesional(turno.getProfesional()).filtrarPorConsultorio(turno.getConsultorio());
        //completar
        return agendaFiltrada.stream().anyMatch(t -> t.colisiona(turno));//recorre el array de agenda filtrada
    }

    private Agenda<T> filtrarPorConsultorio(Consultorio consultorio) {
        Agenda <T> turnosPorConsultorio=new Agenda<>();
        for (T turno:this){
            if(turno.getConsultorio().equals(consultorio)){
                turnosPorConsultorio.add(turno);
            }
        }
        return turnosPorConsultorio;
    }

    private Agenda<T> filtrarPorProfesional(Profesional profesional) {
        Agenda<T> turnosPorProfesional=new Agenda<>();
        for(T turno:this){
            if(turno.getProfesional().equals(profesional)){
                turnosPorProfesional.add(turno);
            }
        }
        return turnosPorProfesional;
    }

    private Agenda<T> filtrarPorSede(Sede sede) {
        Agenda<T> turnosPorSede=new Agenda<>();
        for(T turno:this){
            if(turno.getSede().equals(sede)){
                turnosPorSede.add(turno);
            }
        }
        return turnosPorSede;
    }

    private Agenda<T> filtrarPorDia(LocalDate dia) {
        Agenda<T> turnosPorDia =new Agenda<>();
        for(T turno:this){
            if(turno.getDia().equals(dia)){
                turnosPorDia.add(turno);
            }
        }
        return turnosPorDia;
    }

    private Agenda<T> filtrarPorHoraInicio(LocalTime horario) {
        Agenda<T> turnosPorHorario =new Agenda<>();
        for(T turno:this){
            if(turno.getHoraInicio().equals(horario)){
                turnosPorHorario.add(turno);
            }
        }
        return turnosPorHorario;
    }

    private Agenda<T> filtrarPorHoraFin(LocalTime horario) {
        Agenda<T> turnosPorHorario =new Agenda<>();
        for(T turno:this){
            if(turno.getHoraInicio().equals(horario)){
                turnosPorHorario.add(turno);
            }
        }
        return turnosPorHorario;
    }


}