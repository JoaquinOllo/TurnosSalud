package Interfaces;

import Citas.Cita;

public interface I_CompatibilidadHorarios {

    public <T extends Cita> boolean citaCompatibleConFranjaHoraria(T turno);
}
