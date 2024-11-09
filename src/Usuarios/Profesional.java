package Usuarios;

import Citas.Turno;
import Enumeradores.Especialidad;
import Excepciones.UsuarioInvalidoException;
import Interfaces.I_CompatibilidadHorarios;
import Interfaces.I_GestionTurnos;
import Utilidades.Agenda;
import Utilidades.FranjaHoraria;

import java.awt.*;
import java.awt.List;
import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.Collectors;

public class Profesional extends Usuario implements I_GestionTurnos, I_CompatibilidadHorarios {
    private Especialidad especialidad;
    private Set<FranjaHoraria> horarioDeTrabajo = new HashSet<>();
    int duracionTurnoMinutos;

    public int getDuracionTurnoMinutos() {
        return duracionTurnoMinutos;
    }

    public void setDuracionTurnoMinutos(int duracionTurnoMinutos) {
        this.duracionTurnoMinutos = duracionTurnoMinutos;
    }

    public Profesional(String nombreUsuario, String contrasenha) throws UsuarioInvalidoException {
        super(nombreUsuario, contrasenha);
    }

    public Profesional() {
        super();
    }

    @Override
    public boolean agendaTurnos() {
        return false;
    }

    @Override
    public boolean confirmaTurnos() {
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

    public <T extends Turno> boolean citaCompatibleConFranjaHoraria(T turno) {
        Iterator<FranjaHoraria> iter_horarios =  horarioDeTrabajo.iterator();
        boolean compatible = false;
        while (iter_horarios.hasNext() && ! compatible){
            FranjaHoraria franja = iter_horarios.next();

            compatible = franja.citaCompatibleConFranjaHoraria(turno);
        }
        return compatible;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Set<FranjaHoraria> getHorarioDeTrabajo() {
        return horarioDeTrabajo;
    }

    public void setHorarioDeTrabajo(Set<FranjaHoraria> horarioDeTrabajo) {
        this.horarioDeTrabajo = horarioDeTrabajo;
    }

    @Override
    public String toString() {
        return this.getNombreCompleto();
    }

    public Set<LocalDate> getFechasHabilitadas(Agenda<Turno> turnos, int limiteEnDias) {
        Set<LocalDate> fechasHabilitadas = new HashSet<>();

        for (int i = 0; i < limiteEnDias; i++) {
            LocalDate fecha = LocalDate.now().plusDays(i);
            System.out.println(fecha);
            HashSet<FranjaHoraria> turnosDelDia = turnos.filtrarPorDia(fecha).stream().map(t -> t.getFranjaHoraria()).collect(Collectors.toCollection(HashSet::new));
            System.out.println(this.horarioDeTrabajo);
            for (FranjaHoraria franja: this.horarioDeTrabajo){
                System.out.println(franja);
                if (franja.quedanEspaciosEnFranja(turnosDelDia, this.duracionTurnoMinutos)){
                    System.out.println();
                    fechasHabilitadas.add(fecha);
                }
            }
        }

        return fechasHabilitadas;
    }
}
