package Locaciones;

import Citas.Turno;
import Interfaces.I_CompatibilidadHorarios;
import Usuarios.Usuario;
import UtilidadesFechaYHora.FranjaHoraria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class Locacion implements I_CompatibilidadHorarios {
    private Set<FranjaHoraria> horarios;
    private Usuario responsable;
    private ArrayList<Consultorio> consultorios;
    private String direccion;

    public Set<FranjaHoraria> getHorarios() {
        return horarios;
    }

    public void setHorarios(Set<FranjaHoraria> horarios) {
        this.horarios = horarios;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    public <T extends Turno> boolean citaCompatibleConFranjaHoraria(T turno) {
        Iterator<FranjaHoraria> iter_horarios =  horarios.iterator();
        boolean compatible = false;
        while (iter_horarios.hasNext() && ! compatible){
            FranjaHoraria franja = iter_horarios.next();

            compatible = franja.citaCompatibleConFranjaHoraria(turno);
        }
        return compatible;
    }
}