package Citas;

import Enumeradores.Especialidad;
import Enumeradores.EstadoCita;
import Excepciones.OperacionNoPermitidaException;
import Locaciones.Consultorio;
import Locaciones.Sede;
import Usuarios.Consultante;
import Usuarios.Profesional;
import Usuarios.Usuario;
import Utilidades.FranjaHoraria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class Turno  {
    protected LocalDate dia;
    protected FranjaHoraria horario;
    private EstadoCita estado = EstadoCita.PENDIENTE_CONFIRMACION;
    private Consultante consultante;
    private Profesional profesional;
    private Usuario agendadoPor;
    private Consultorio consultorio;
    private String razon;

    public Turno(Usuario gestor) {
        this.agendadoPor = gestor;
    }

    public Turno() {

    }

    public Turno(String dia, FranjaHoraria franjaHoraria, String consultante, String profesional, String agendadoPor, String estado, String razon) {
    }

    public Turno(LocalDate dia, FranjaHoraria franjaHoraria, Usuario consultante, Usuario profesional, Usuario agendadoPor, EstadoCita estado) {
        this.dia = dia;
        this.horario = franjaHoraria;
        this.consultante = (Consultante) consultante;
        this.profesional = (Profesional) profesional;
        this.agendadoPor = agendadoPor;
        this.estado = estado;
    }

    /**
     * Este método confirma un turno, cambiando su estado.
     */
    public void confirmar() {
        this.estado = EstadoCita.CONFIRMADO;
    }

    /**
     * @param motivo
     * @throws OperacionNoPermitidaException
     */
    public void cancelar(String motivo) throws OperacionNoPermitidaException {
        this.estado = EstadoCita.CANCELADO;
        this.razon = motivo;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia){
        this.dia = dia;
    }

    public LocalDateTime getHorarioCompleto(){
        return LocalDateTime.of(this.dia, this.getFranjaHoraria().getHoraInicio());
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

    public Especialidad getEspecialidad() {
        return this.profesional.getEspecialidad();
    }

    public LocalTime getHoraFin(){
        return this.horario.getHoraCierre();
    }
    /*public void posponer(String motivo, LocalDateTime nuevaFechaYHora) {
        this.dia = nuevaFechaYHora.toLocalDate();
        this.horario = new FranjaHoraria(nuevaFechaYHora.toLocalTime(), this.horario.getDuracion());
        this.razon = motivo;
    }*/
    public boolean colisiona(Turno turno) {
        return ! this.horario.noColisiona(turno.horario);
    }


    public void pacienteAusente (){
        this.estado = EstadoCita.CANCELADO;
        this.razon = "Paciente ausente";
    }

    public void profesionalAusente(){
        this.estado = EstadoCita.CANCELADO;
        this.razon = "Profesional ausente";
    }

    public Sede getSede() {
        return this.getConsultorio().getSede();
    }

    public FranjaHoraria getFranjaHoraria (){
        return this.horario;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "dia=" + dia +
                ", horario=" + horario +
                ", estado=" + estado +
                ", consultante=" + consultante +
                ", profesional=" + profesional +
                ", agendadoPor=" + agendadoPor +
                ", consultorio=" + consultorio +
                '}';
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public void setEstado(EstadoCita estadoCita) {
        this.estado = estadoCita;
    }
}