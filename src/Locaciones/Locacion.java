package Locaciones;

import Usuarios.Usuario;
import UtilidadesFechaYHora.FranjaHoraria;

import java.util.ArrayList;

public class Locacion {
    private ArrayList<FranjaHoraria> horarios;
    private Usuario responsable;
    private ArrayList<Consultorio> consultorios;
    private String direccion;

    public ArrayList<FranjaHoraria> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<FranjaHoraria> horarios) {
        this.horarios = horarios;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }
}
