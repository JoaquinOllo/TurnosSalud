package Interfaces;

import Citas.Turno;

public interface I_CompatibilidadHorarios {

    public <T extends Turno> boolean citaCompatibleConFranjaHoraria(T turno);
}
