package Excepciones;

import Locaciones.Sede;

public class LugarNoDisponibleException extends Exception {

    public LugarNoDisponibleException(Sede sede, String causa){
        super("La sede " + sede + " no se encuentra disponible para la operación: " + causa);
    }

    public LugarNoDisponibleException(Sede sede){
        super("La sede " + sede + " no se encuentra disponible para la operación.");
    }

    public LugarNoDisponibleException(String nombreSede) {
        super("La sede indicada no se encuentra disponible o registrada: " + nombreSede);
    }
}
