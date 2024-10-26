package Usuarios;

import Citas.Cita;
import Interfaces.I_CompatibilidadHorarios;
import Interfaces.I_GestionHC;
import Interfaces.I_GestionTurnos;
import UtilidadesFechaYHora.FranjaHoraria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Profesional extends Usuario implements I_GestionTurnos, I_GestionHC, I_CompatibilidadHorarios {
    @Override
    public boolean agendaTurnos() {
        return false;
    }

    @Override
    public boolean confirmaTurnos() {
        return false;
    }

    @Override
    public boolean aceptaSobreturnos() {
        return false;
    }

    @Override
    public boolean cancelaTurnos() {
        return false;
    }

    @Override
    public boolean reprogramaTurnos() {
        return false;
    }

    @Override
    public boolean lecturaHCPropia() {
        return false;
    }

    @Override
    public boolean editaHCPropia() {
        return false;
    }

    @Override
    public boolean lecturaHC() {
        return false;
    }

    @Override
    public boolean editaHC() {
        return false;
    }

    Set<FranjaHoraria> horarioDeTrabajo = new HashSet<>();


    public <T extends Cita> boolean citaCompatible(T turno) {
        Iterator<FranjaHoraria> iter_horarios =  horarioDeTrabajo.iterator();
        boolean compatible = false;
        while (iter_horarios.hasNext() && ! compatible){
            FranjaHoraria franja = iter_horarios.next();

            compatible = franja.citaCompatible(turno);
        }
        return compatible;
    }
}
