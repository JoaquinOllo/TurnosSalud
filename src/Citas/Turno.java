package Citas;

import Usuarios.Usuario;
import UtilidadesFechaYHora.FranjaHoraria;

import java.time.LocalDateTime;

public class Turno extends Cita {

    public Turno(Usuario gestor) {
        super(gestor);
    }

    @Override
    public void posponer(String motivo, LocalDateTime nuevaFechaYHora) {

    }

    @Override
    public void setHorario(FranjaHoraria horario) {
        this.horario = horario;
    }


    @Override
    public boolean admiteSimultaneidad() {
        return false;
    }

    @Override
    public boolean colisiona(Cita turno) {
        // completar
        return !admiteSimultaneidad() &&
                this.horario.noColisiona(turno.horario);
    }
}
