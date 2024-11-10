package Usuarios;

import Citas.Turno;
import Enumeradores.Especialidad;
import Excepciones.UsuarioInvalidoException;
import Interfaces.I_CompatibilidadHorarios;
import Interfaces.I_GestionTurnos;
import Locaciones.Sede;
import Utilidades.Agenda;
import Utilidades.FranjaHoraria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class Profesional extends Usuario implements I_GestionTurnos, I_CompatibilidadHorarios {
    private Especialidad especialidad;
    private Set<FranjaHoraria> horarioDeTrabajo = new HashSet<>();
    private final int duracionTurnoMinutos = 60;

    public Profesional(String nombre, String apellido, int edad, String correo, String telefono, String nombreUsuario, String contrasena, String direccion, String especialidad, HashSet<FranjaHoraria> horarios) throws UsuarioInvalidoException {
        super(nombre, apellido, edad, correo, telefono, nombreUsuario, contrasena, direccion);
        this.setEspecialidad(Especialidad.valueOf(especialidad));
        this.setHorarioDeTrabajo(horarios);
    }

    public int getDuracionTurnoMinutos() {
        return duracionTurnoMinutos;
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

    @Override
    public boolean soloAutoGestion() {
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

    public HashSet<LocalTime> getHorariosHabilitados(Agenda<Turno> turnos, int duracionTurnoEnMinutos) {
        HashSet<LocalTime> horariosHabilitados = new HashSet<>();

        for (FranjaHoraria franja : this.horarioDeTrabajo){
            horariosHabilitados.addAll(franja.getHorariosHabilitados(turnos, duracionTurnoEnMinutos));
        }

        return horariosHabilitados;
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
            HashSet<FranjaHoraria> turnosDelDia = turnos.filtrarPorDia(fecha).stream().map(Turno::getFranjaHoraria).collect(Collectors.toCollection(HashSet::new));
            for (FranjaHoraria franja: this.horarioDeTrabajo){
                if (franja.quedanEspaciosEnFranja(turnosDelDia, this.duracionTurnoMinutos)){
                    fechasHabilitadas.add(fecha);
                }
            }
        }
        return fechasHabilitadas;
    }
}
