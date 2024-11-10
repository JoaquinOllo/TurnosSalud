package Usuarios;

import Enumeradores.Especialidad;

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

}
