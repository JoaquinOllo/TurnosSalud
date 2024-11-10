package Excepciones;

import Locaciones.Consultorio;
import Locaciones.Sede;

import java.time.LocalDateTime;

public class LugarNoDisponibleException extends Exception {

    public LugarNoDisponibleException(Consultorio consultorio, String causa) {
        super("El consultorio no se encuentra disponible para la operación: " + causa);
    }

    public LugarNoDisponibleException(Sede sede, String causa){
        super("La sede no se encuentra disponible para la operación" + causa);
    }

    public LugarNoDisponibleException(Sede sede){
        super("La sede no se encuentra disponible para la operación.");
    }

    public LugarNoDisponibleException(Consultorio consultorio){
        super("El consultorio no se encuentra disponible para la operación.");
    }

}
