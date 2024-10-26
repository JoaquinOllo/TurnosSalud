import Citas.Cita;
import Locaciones.Consultorio;
import Locaciones.Locacion;
import Usuarios.Usuario;

import java.util.ArrayList;

public class GestionSistema {

    private ArrayList<Consultorio> consultorios;
    private ArrayList<Locacion> sedes;
    private ArrayList<Usuario> usuarios;
    private Usuario usuarioConectado;

    private ArrayList<Cita> turnos;

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

    public ArrayList<Cita> getTurnos() {
        return turnos;
    }

    public void setConsultorios(ArrayList<Consultorio> consultorios) {
        this.consultorios = consultorios;
    }

    public void setSedes(ArrayList<Locacion> sedes) {
        this.sedes = sedes;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void setTurnos(ArrayList<Cita> turnos) {
        this.turnos = turnos;
    }

    public void setUsuarioConectado(Usuario usuarioConectado) {
        this.usuarioConectado = usuarioConectado;
    }

    public GestionSistema() {
    }
}
