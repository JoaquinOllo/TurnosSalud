package Citas;

import Excepciones.OperacionNoPermitidaException;
import Locaciones.Consultorio;
import Usuarios.Consultante;
import Usuarios.Profesional;
import Usuarios.Usuario;
import UtilidadesFechaYHora.FranjaHoraria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Sobreturno extends Cita{
    public Sobreturno(Usuario gestor) {
        super(gestor);
    }

    @Override
    public void posponer(String motivo, LocalDateTime nuevaFechaYHora) throws OperacionNoPermitidaException {
        throw new OperacionNoPermitidaException(this.getClass().getSimpleName(), "posponer");
    }

    @Override
    public void setHorario(FranjaHoraria horario) {
        this.horario = horario;
    }

    @Override
    public boolean admiteSimultaneidad() {
        return true;
    }

    @Override
    public LocalTime getHoraFin() {
        return this.getHoraInicio();
    }

    @Override
    public boolean colisiona(Cita turno) {
        return false;
    }
}
