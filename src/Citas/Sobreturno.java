package Citas;

import Excepciones.OperacionNoPermitidaException;
import Locaciones.Consultorio;
import Usuarios.Consultante;
import Usuarios.Profesional;
import Usuarios.Usuario;

import javax.xml.datatype.DatatypeConfigurationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Sobreturno extends Cita{
    public Sobreturno(Profesional profesional, Consultante consultante, Consultorio consultorio, Usuario gestor, LocalDateTime horarioInicio) {
        super(profesional, consultante, consultorio, gestor, horarioInicio);
    }

    @Override
    public void posponer(String motivo, LocalDateTime nuevaFechaYHora) throws OperacionNoPermitidaException {
        throw new OperacionNoPermitidaException(this.getClass().getSimpleName(), "posponer");
    }

    @Override
    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    @Override
    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }
}
