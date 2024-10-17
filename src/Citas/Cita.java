package Citas;

import Enumeradores.EstadoCita;
import Locaciones.Consultorio;
import Usuarios.Consultante;
import Usuarios.Profesional;
import Usuarios.Usuario;

import javax.xml.datatype.DatatypeConfigurationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Cita {
    private LocalDate dia;
    private LocalTime horario;
    private EstadoCita estado = EstadoCita.PENDIENTE_CONFIRMACION;
    private Consultante consultante;
    private Profesional profesional;
    private Usuario agendadoPor;
    private Consultorio consultorio;
    private String razon;


    public Cita(Profesional profesional, Consultante consultante, Consultorio consultorio, Usuario gestor, LocalDateTime horarioInicio) throws DatatypeConfigurationException {
        dia = horarioInicio.toLocalDate();
        horario = horarioInicio.toLocalTime();
        this.profesional = profesional;
        this.consultante = consultante;
        this.consultorio = consultorio;
        this.agendadoPor = gestor;
    }

    public void confirmar() {
        this.estado = EstadoCita.CONFIRMADO;
    }

    public void cancelar(String motivo) {
        this.estado = EstadoCita.CANCELADO;
        this.razon = motivo;
    }

    public abstract void posponer(String motivo, LocalDateTime nuevaFechaYHora);

    public LocalDate getDia() {
        return dia;
    }

    public abstract void setDia(LocalDate dia);

    public LocalTime getHorario() {
        return horario;
    }

    public abstract void setHorario(LocalTime horario);

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
}