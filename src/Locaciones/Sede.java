package Locaciones;

import Citas.Turno;
import Excepciones.HorarioNoDisponibleException;
import Excepciones.LugarNoDisponibleException;
import Interfaces.I_CompatibilidadHorarios;
import Usuarios.Consultante;
import Usuarios.Usuario;
import Utilidades.Agenda;
import Utilidades.FranjaHoraria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Sede implements I_CompatibilidadHorarios {
    private Set<FranjaHoraria> horarios = new HashSet<>();
    private Usuario responsable;
    private ArrayList<Consultorio> consultorios = new ArrayList<>();
    private String direccion;
    private String nombre;

    public Set<FranjaHoraria> getHorarios() {
        return horarios;
    }

    public void setHorarios(Set<FranjaHoraria> horarios) {
        this.horarios = horarios;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    public <T extends Turno> boolean citaCompatibleConFranjaHoraria(T turno) {
        Iterator<FranjaHoraria> iter_horarios =  horarios.iterator();
        boolean compatible = false;
        while (iter_horarios.hasNext() && ! compatible){
            FranjaHoraria franja = iter_horarios.next();

            compatible = franja.citaCompatibleConFranjaHoraria(turno);
        }
        return compatible;
    }

    @Override
    public HashSet<LocalTime> getHorariosHabilitados(Agenda<Turno> turnos, int duracionTurnoEnMinutos) {
        HashSet<LocalTime> horariosHabilitados = new HashSet<>();
        for (Consultorio consultorio : this.consultorios){
            horariosHabilitados.addAll(consultorio.getHorariosHabilitados(turnos.filtrarPorConsultorio(consultorio), duracionTurnoEnMinutos));
        }
        return horariosHabilitados;
    }

    public ArrayList<Consultorio> getConsultorios() {
        return consultorios;
    }

    public void setConsultorios(ArrayList<Consultorio> consultorios) {
        this.consultorios = consultorios;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return this.nombre + ": " + this.direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void addConsultorio(Consultorio consultorio) {
        this.consultorios.add(consultorio);
    }

    public Consultorio buscarConsultorioDisponible(Agenda<Turno> turnos, LocalDate dia, FranjaHoraria franjaHoraria) throws LugarNoDisponibleException {

        for (Consultorio consultorio : this.consultorios){
            if (turnos.filtrarPorDia(dia).filtrarPorHoraInicio(franjaHoraria.getHoraInicio()).filtrarPorConsultorio(consultorio).isEmpty()){
                return consultorio;
            }
        }
        throw new LugarNoDisponibleException(this, "sede sin consultorios disponibles para el día "
        + dia + " y para la franja horaria " + franjaHoraria);
    }
}
