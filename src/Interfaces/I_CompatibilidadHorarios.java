package Interfaces;

import Citas.Turno;
import Utilidades.Agenda;
import Utilidades.FranjaHoraria;

import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;

public interface I_CompatibilidadHorarios {

    public <T extends Turno> boolean citaCompatibleConFranjaHoraria(T turno);

}
