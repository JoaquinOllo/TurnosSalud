package Usuarios;

import Enumeradores.Especialidad;
import Excepciones.UsuarioInexistenteException;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ListaUsuarios<T extends Usuario> extends ArrayList<T> {

    public ListaUsuarios<Profesional> filtrarPorEspecialidad(Especialidad especialidad){
        return this.stream().filter(us -> us instanceof Profesional)
                .map(e-> (Profesional) e)
                .filter(us -> ((Profesional) us).getEspecialidad().equals(especialidad))
                .collect(Collectors.toCollection(ListaUsuarios::new));
    }

    public ArrayList<String> versionResumida(){
        return this.stream().map(Usuario::getNombreCompleto).collect(Collectors.toCollection(ArrayList::new));
    }

    public T get(String nombreUsuario) throws UsuarioInexistenteException {
        for (T usuario : this){
            if (usuario.getNombreUsuario().equals(nombreUsuario)){
                return usuario;
            }
        }
        throw new UsuarioInexistenteException(nombreUsuario);    }

    public void set(String nombreUsuario, T usuario) throws UsuarioInexistenteException {
        this.set(this.getIndice(nombreUsuario), usuario);
    }

    public int getIndice(String nombreUsuario) throws UsuarioInexistenteException{
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getNombreUsuario().equals(nombreUsuario)){
                return i;
            }
        }
        throw new UsuarioInexistenteException(nombreUsuario);
    }
}
