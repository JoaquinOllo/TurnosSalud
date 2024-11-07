package Usuarios;

import Excepciones.UsuarioInvalidoException;

import java.util.HashSet;
import java.util.Set;

public abstract class Usuario {
    private String nombre;
    private String apellido;
    private int edad;
    private String correo;
    private String nroTelefono;
    private static Set<String> usuarios = new HashSet<>();

    private String nombreUsuario;
    private String contrasenha;

    private String direccion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNroTelefono() {
        return nroTelefono;
    }

    public void setNroTelefono(String nroTelefono) {
        this.nroTelefono = nroTelefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) throws UsuarioInvalidoException {
        if (validarNombreUsuario(nombreUsuario)){
            this.nombreUsuario = nombreUsuario;
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", correo='" + correo + '\'' +
                ", nroTelefono='" + nroTelefono + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasenha='" + contrasenha + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    private boolean validarNombreUsuario(String nombreUsuario) throws UsuarioInvalidoException {
        boolean usuarioValido = usuarios.stream().noneMatch(e -> e.equals(nombreUsuario));

        if (!usuarioValido){
            throw new UsuarioInvalidoException(nombreUsuario);
        } else {
            usuarios.add(nombreUsuario);
        }

        return true;
    }

    public Usuario(String nombreUsuario, String contrasenha) throws UsuarioInvalidoException {
        setNombreUsuario(nombreUsuario);
        this.contrasenha = contrasenha;
    }

    public Usuario() {
    }
}
