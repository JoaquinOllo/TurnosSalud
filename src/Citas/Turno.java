package Citas;

import Locaciones.Consultorio;
import Usuarios.Consultante;
import Usuarios.Profesional;
import Usuarios.Usuario;
import UtilidadesFechaYHora.FranjaHoraria;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

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
        return !admiteSimultaneidad();
    }
}
