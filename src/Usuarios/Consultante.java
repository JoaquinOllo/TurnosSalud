package Usuarios;

import Enumeradores.TipoDeVisualizacion;
import Excepciones.NombreInvalidoException;
import Interfaces.I_GestionTurnos;

public class Consultante extends Usuario implements I_GestionTurnos {

    public Consultante(String nombreUsuario, String contrasenha) throws NombreInvalidoException {
        super(nombreUsuario, contrasenha);
    }

    public Consultante()  {
        super();
    }

    public Consultante(String nombre, String apellido, int edad, String correo, String telefono, String nombreUsuario, String contrasena, String direccion) throws NombreInvalidoException {
        super(nombre, apellido, edad, correo, telefono, nombreUsuario, contrasena, direccion);
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
        return true;
    }

    @Override
    public TipoDeVisualizacion modalidadVisualizacionDeTurnos() {
        return TipoDeVisualizacion.PERSONAL;
    }

    @Override
    public String toString() {
        return this.getNombreCompleto();
    }

}
