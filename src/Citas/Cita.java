package Citas;

import Enumeradores.EstadoCita;
import Excepciones.OperacionNoPermitidaException;
import Locaciones.Consultorio;
import Usuarios.Consultante;
import Usuarios.Profesional;
import Usuarios.Usuario;
import UtilidadesFechaYHora.FranjaHoraria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Cita {
    protected LocalDate dia;
    protected FranjaHoraria horario;
    private EstadoCita estado = EstadoCita.PENDIENTE_CONFIRMACION;
    private Consultante consultante;
    private Profesional profesional;
    private Usuario agendadoPor;
    private Consultorio consultorio;
    private String razon;


    public Cita(Usuario gestor) {
        this.agendadoPor = gestor;
    }

    public void confirmar() {
        this.estado = EstadoCita.CONFIRMADO;
    }

    public void cancelar(String motivo) throws OperacionNoPermitidaException {
        this.estado = EstadoCita.CANCELADO;
        this.razon = motivo;
    }

    public abstract void posponer(String motivo, LocalDateTime nuevaFechaYHora) throws OperacionNoPermitidaException;

    public LocalDate getDia() {
        return dia;
    }

    public setDia(LocalDate dia){
        this.dia = dia;
    }

    public LocalTime getHoraInicio() {
        return horario.getHoraInicio();
    }

    public void setHorario(FranjaHoraria horario){
        this.horario = horario;
    }

    public Consultante getConsultante() {
        return consultante;
    }

    public void setConsultante(Consultante consultante) {
        this.consultante = consultante;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public Usuario getAgendadoPor() {
        return agendadoPor;
    }

    public void setAgendadoPor(Usuario agendadoPor) {
        this.agendadoPor = agendadoPor;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public String getRazon() {
        return razon;
    }

    public abstract boolean admiteSimultaneidad();

    public LocalTime getHoraFin(){
        return this.horario.getHoraCierre();
    }

    public abstract boolean colisiona(Cita turno);
}