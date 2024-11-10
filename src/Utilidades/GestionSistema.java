package Utilidades;

import Citas.Turno;
import Enumeradores.Especialidad;
import Excepciones.HorarioNoDisponibleException;
import Excepciones.OperacionNoPermitidaException;
import Excepciones.UsuarioInvalidoException;
import Interfaces.I_GestionTurnos;
import Locaciones.Consultorio;
import Locaciones.Sede;
import Usuarios.*;

import javax.swing.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class  GestionSistema {

    private ArrayList<Sede> sedes;
    private ListaUsuarios<Usuario> usuarios;
    private Usuario usuarioConectado;
    private Interfaz menu;

    public final int maximoDiasTurnos = 45;

    private Agenda<Turno> turnos;

    public ArrayList<Consultorio> getConsultorios() {
        ArrayList<Consultorio> consultorios = new ArrayList<>();
        for (Sede sede: this.sedes){
            consultorios.addAll(sede.getConsultorios());
        }
        return consultorios;
    }

    public ArrayList<Sede> getSedes() {
        return sedes;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario getUsuarioConectado() {
        return usuarioConectado;
    }

    public Agenda<Turno> getTurnos() {
        return turnos;
    }

    /**
     * @param sedes
     */
    public void setSedes(ArrayList<Sede> sedes) {
        this.sedes = sedes;
    }

    public void addSede(Sede sede){
        this.sedes.add(sede);
    }

    public void setUsuarios(ListaUsuarios<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void setTurnos(Agenda<Turno> turnos) {
        this.turnos = turnos;
    }

    public void setUsuarioConectado(Usuario usuarioConectado) {
        this.usuarioConectado = usuarioConectado;
    }

    /** Constructor utilizado para la inicialización del sistema sin json de datos.
     */
    public GestionSistema() {
        this.turnos = new Agenda<>();
        this.sedes = new ArrayList<>();
        this.usuarios = new ListaUsuarios<>();
        this.menu = new Interfaz(this);
    }


    /** Método utilizado para validar que no se generen turnos que colisionen entre sí, y si estas validaciones pasan, se agrega a la agenda.
     * @param turno pasar turno a agendar, previamente generado por la interfaz
     * @throws HorarioNoDisponibleException
     * @throws OperacionNoPermitidaException
     */
    public void agendarTurno(Turno turno) throws HorarioNoDisponibleException, OperacionNoPermitidaException {
        if (!(usuarioConectado instanceof I_GestionTurnos) | ! ((I_GestionTurnos) usuarioConectado).agendaTurnos()){
            throw new OperacionNoPermitidaException(usuarioConectado.getClass().getSimpleName(), "agendar turno");
        }else if (citaDisponible(turno)){
            this.turnos.add(turno);
            System.out.println(this.turnos);
        } else {
            throw new HorarioNoDisponibleException(turno.getHorarioCompleto(), "No hay disponibilidad para este turno");
        }
    }

    private <T extends Turno> boolean citaDisponible(T turno) {
        boolean turnoDisponible = false;

        System.out.println("Evaluando si la cita está disponible.");
        System.out.println(turno.getProfesional().citaCompatibleConFranjaHoraria(turno));
        System.out.println(turno.getConsultorio().citaCompatibleConFranjaHoraria(turno));
        System.out.println(turnos.franjaDisponible(turno));
        if(turno.getProfesional().citaCompatibleConFranjaHoraria(turno)
        && turno.getConsultorio().citaCompatibleConFranjaHoraria(turno)
        &&  turnos.franjaDisponible(turno)) {
            turnoDisponible = true;
        }

        return turnoDisponible;
    }

    public void arranqueParaPruebas() throws UsuarioInvalidoException {
        Sede sede = new Sede();
        sedes.add(sede);
        sede.setDireccion("Maipú 3532");
        sede.setNombre("Aurora");

        Set<FranjaHoraria> horarios = new HashSet<>();
        horarios.add(new FranjaHoraria(8, 480));

        sede.setHorarios(horarios);

        Consultorio consultorio = new Consultorio();
        consultorio.setSede(sede);
        sede.addConsultorio(consultorio);

        Consultante consultante = new Consultante("pipo", "pipo");
        consultante.setNombre("Pipo");
        consultante.setApellido("Pescador");
        usuarios.add(consultante);

        Administrador administrador = new Administrador("admin", "admin");
        administrador.setNombre("Ardo");
        administrador.setApellido("Kiwi");
        usuarios.add(administrador);

        Profesional profesional = new Profesional("rosa", "rosa");
        profesional.setNombre("Rosa");
        profesional.setApellido("Atlante");
        profesional.setEspecialidad(Especialidad.ALERGISTA);
        HashSet<FranjaHoraria> horario = new HashSet<>();
        horario.add(new FranjaHoraria(8, 400));
        profesional.setHorarioDeTrabajo(horario);
        usuarios.add(profesional);

        sede.setResponsable(profesional);

        Profesional profesional2 = new Profesional("amalia", "rosa");
        profesional2.setNombre("Amalía");
        profesional2.setApellido("Sémola");
        profesional2.setEspecialidad(Especialidad.ALERGISTA);
        HashSet<FranjaHoraria> horario2 = new HashSet<>();
        horario2.add(new FranjaHoraria(8, 320));
        profesional2.setHorarioDeTrabajo(horario2);
        usuarios.add(profesional2);

        this.usuarioConectado=administrador;

        menu.menuConexion();

//        Turno turno=new Turno();
//        turnos.add(turno);

    }

    public void arranque(){
        // acá va el arranque estándar del programa, sin usuarios inventados, tomando todo del json
    }

    public boolean conectarse (String nombreUsuario, JPasswordField contrasenha){
        boolean credencialesCorrectas = false;

        for (Usuario usuario : usuarios){
            if (usuario.getNombreUsuario().equals(nombreUsuario)){
                Usuario usuarioEnConexion = usuario;
                String str_contrasenha = new String(contrasenha.getPassword());

                credencialesCorrectas = usuarioEnConexion.getContrasenha().equals(str_contrasenha);
            }
        }
        return credencialesCorrectas;
    }

    public ListaUsuarios<Profesional> getProfesionales (){
        ListaUsuarios<Profesional> profesionales = new ListaUsuarios<>();

        for (Usuario usuario: this.usuarios){
            if (usuario instanceof Profesional){
                profesionales.add((Profesional) usuario);
            }
        }
        return profesionales;
    }

    public ListaUsuarios<Administrativo> getAdministrativos (){
        ListaUsuarios<Administrativo> administrativos = new ListaUsuarios<>();

        for (Usuario usuario: this.usuarios){
            if (usuario instanceof Administrativo){
                administrativos.add((Administrativo) usuario);
            }
        }
        return administrativos;
    }

    public Set<Especialidad> getEspecialidadesDisponibles(){
        Set<Especialidad> especialidades = new HashSet<>();

        for (Profesional profesional: this.getProfesionales()){
            especialidades.add(profesional.getEspecialidad());
        }
        return especialidades;
    }

    //filtro para que de la lista de usuarios me guarde en el array solo los pacientes

    public ArrayList<Consultante> getPacientes() {
        ArrayList<Consultante> pacientes = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Consultante) {
                pacientes.add((Consultante) usuario);
            }
        }
        return pacientes;
    }

    public HashSet<LocalDate> getFechasHabilitadas(Profesional profesional) {
        HashSet<LocalDate> fechasHabilitadas = new HashSet<>(profesional.getFechasHabilitadas(this.turnos.filtrarPorProfesional(profesional),
                this.maximoDiasTurnos));
        return fechasHabilitadas;
    }

    public HashSet<LocalTime> getHorariosDisponibles(Profesional profesional, Sede sede, LocalDate dia) {
        HashSet<LocalTime> horariosDisponibles = new HashSet<>(profesional.getHorariosHabilitados(this.turnos.filtrarPorProfesional(profesional)
                .filtrarPorDia(dia), profesional.getDuracionTurnoMinutos()));

        System.out.println(horariosDisponibles);

        HashSet<LocalTime> horariosDispSede = new HashSet<>(sede.getHorariosHabilitados(this.turnos
                .filtrarPorDia(dia).filtrarPorSede(sede), profesional.getDuracionTurnoMinutos()));

        System.out.println(horariosDispSede);

        horariosDisponibles.retainAll(horariosDispSede);

        System.out.println(horariosDisponibles);
        return horariosDisponibles;
    }

    public ArrayList<Sede> getSedesDisponibles() {
        return this.sedes;
    }
}
