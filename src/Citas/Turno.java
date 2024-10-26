package Citas;

import Locaciones.Consultorio;
import Usuarios.Consultante;
import Usuarios.Profesional;
import Usuarios.Usuario;

import javax.xml.datatype.DatatypeConfigurationException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Turno extends Cita {
    private Duration duracion = Duration.of(30, ChronoUnit.MINUTES);

    public Turno(Profesional profesional, Consultante consultante, Consultorio consultorio, Usuario gestor, LocalDateTime horarioInicio) {
        super(profesional, consultante, consultorio, gestor, horarioInicio);
    }

    public Turno(Profesional profesional, Consultante consultante, Consultorio consultorio, Usuario gestor, LocalDateTime horarioInicio, long duracionMinutos) {
        super(profesional, consultante, consultorio, gestor, horarioInicio);
        duracion = Duration.of(duracionMinutos, ChronoUnit.MINUTES);
    }



    @Override
    public void posponer(String motivo, LocalDateTime nuevaFechaYHora) {

    }

    @Override
    public void setDia(LocalDate dia) {

    }

    @Override
    public void setHorario(LocalTime horario) {

    }
}
