package Usuarios;

import Enumeradores.TipoDeVisualizacion;
import Excepciones.NombreInvalidoException;
import Interfaces.I_GestionAdministrativa;
import Interfaces.I_GestionConsultorios;
import Interfaces.I_GestionTurnos;
import Locaciones.Sede;

public class Administrativo extends Usuario implements I_GestionTurnos, I_GestionAdministrativa, I_GestionConsultorios {
    private Sede sede;

    public Administrativo(String nombreUsuario, String contrasenha) throws NombreInvalidoException {
        super(nombreUsuario, contrasenha);
    }

    public Administrativo(String nombre, String apellido, int edad, String correo, String telefono, String nombreUsuario, String contrasena, String direccion) throws NombreInvalidoException {
        super(nombre, apellido, edad, correo, telefono, nombreUsuario, contrasena, direccion);
    }


    public Administrativo() {
    }

    public void setSede(Sede sede){
        this.sede = sede;
    }

    @Override
    public boolean agendaTurnos() {
        return true;
    }

    @Override
    public boolean confirmaTurnos() {
        return true;
    }

    @Override
    public boolean cancelaTurnos() {
        return true;
    }

    @Override
    public boolean reprogramaTurnos() {
        return true;
    }

    @Override
    public boolean soloAutoGestion() {
        return false;
    }

    @Override
    public TipoDeVisualizacion modalidadVisualizacionDeTurnos() {
        return TipoDeVisualizacion.POR_SEDE;
    }

    @Override
    public boolean administraUsuarios() {
        return true;
    }

    @Override
    public TipoDeVisualizacion modalidadVisualizacionUsuarios() {
        return TipoDeVisualizacion.TODOS;
    }

    @Override
    public boolean defineHorariosConsultorios() {
        return true;
    }

    @Override
    public boolean administraConsultorios() {
        return true;
    }

    public Sede getSede() {
        return this.sede;
    }


}
