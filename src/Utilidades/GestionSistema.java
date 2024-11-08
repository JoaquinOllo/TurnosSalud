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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class  GestionSistema {

    private ArrayList<Consultorio> consultorios;
    private ArrayList<Sede> sedes;
    private ListaUsuarios<Usuario> usuarios;
    private Usuario usuarioConectado;
    private Interfaz menu;

    public final int maximoDiasTurnos = 45;

    private Agenda<Turno> turnos;

    public ArrayList<Consultorio> getConsultorios() {
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

    public ArrayList<Turno> getTurnos() {
        return turnos;
    }

    public void setConsultorios(ArrayList<Consultorio> consultorios) {
        this.consultorios = consultorios;
    }

    /**
     * @param sedes
     */
    public void setSedes(ArrayList<Sede> sedes) {
        this.sedes = sedes;
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
        this.consultorios = new ArrayList<>();
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
        }
    }

    private <T extends Turno> boolean citaDisponible(T turno) throws HorarioNoDisponibleException {
        boolean turnoDisponible = false;

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

        Set<FranjaHoraria> horarios = new HashSet<>();
        horarios.add(new FranjaHoraria(8, 480));

        sede.setHorarios(horarios);

        Consultorio consultorio = new Consultorio();
        consultorios.add(consultorio);

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
        usuarios.add(profesional);

        sede.setResponsable(profesional);

        Profesional profesional2 = new Profesional("amalia", "rosa");
        profesional2.setNombre("Amalía");
        profesional2.setApellido("Sémola");
        profesional2.setEspecialidad(Especialidad.ALERGISTA);
        usuarios.add(profesional2);

        this.usuarioConectado=administrador;

        menu.menuConexion();
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

}
