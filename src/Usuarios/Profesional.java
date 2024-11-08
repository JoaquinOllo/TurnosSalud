package Usuarios;

import Citas.Turno;
import Enumeradores.Especialidad;
import Excepciones.UsuarioInvalidoException;
import Interfaces.I_CompatibilidadHorarios;
import Interfaces.I_GestionTurnos;
import Utilidades.FranjaHoraria;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Profesional extends Usuario implements I_GestionTurnos, I_CompatibilidadHorarios {
    private Especialidad especialidad;
    Set<FranjaHoraria> horarioDeTrabajo = new HashSet<>();

    public Profesional(String nombreUsuario, String contrasenha) throws UsuarioInvalidoException {
        super(nombreUsuario, contrasenha);
    }

    public Profesional() {
        super();
    }

    @Override
    public boolean agendaTurnos() {
        return false;
    }

    @Override
    public boolean confirmaTurnos() {
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

    public <T extends Turno> boolean citaCompatibleConFranjaHoraria(T turno) {
        Iterator<FranjaHoraria> iter_horarios =  horarioDeTrabajo.iterator();
        boolean compatible = false;
        while (iter_horarios.hasNext() && ! compatible){
            FranjaHoraria franja = iter_horarios.next();

            compatible = franja.citaCompatibleConFranjaHoraria(turno);
        }
        return compatible;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Set<FranjaHoraria> getHorarioDeTrabajo() {
        return horarioDeTrabajo;
    }

    public void setHorarioDeTrabajo(Set<FranjaHoraria> horarioDeTrabajo) {
        this.horarioDeTrabajo = horarioDeTrabajo;
    }
}
