package Interfaces;

import Citas.Turno;
import Utilidades.FranjaHoraria;

import java.util.HashSet;

public interface I_CompatibilidadHorarios {

    public <T extends Turno> boolean citaCompatibleConFranjaHoraria(T turno);

}
