import Citas.Turno;
import Excepciones.HorarioNoDisponibleException;
import Excepciones.OperacionNoPermitidaException;
import Interfaces.I_GestionTurnos;
import Locaciones.Consultorio;
import Locaciones.Locacion;
import Usuarios.Administrador;
import Usuarios.Usuario;
import Utilidades.MenuSistema;

import java.util.ArrayList;

public class  GestionSistema {

    private ArrayList<Consultorio> consultorios;
    private ArrayList<Locacion> sedes;
    private ArrayList<Usuario> usuarios;
    private Usuario usuarioConectado;
    private MenuSistema menu;

    private Agenda<Turno> turnos;

    public ArrayList<Consultorio> getConsultorios() {
        return consultorios;
    }

    public ArrayList<Locacion> getSedes() {
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
    public void setSedes(ArrayList<Locacion> sedes) {
        this.sedes = sedes;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
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
        this.usuarioConectado = new Administrador();
        this.consultorios = new ArrayList<>();
        this.sedes = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.menu = new MenuSistema();
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

}
