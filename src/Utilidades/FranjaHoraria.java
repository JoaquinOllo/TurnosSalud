package Utilidades;

import Citas.Turno;
import Interfaces.I_CompatibilidadHorarios;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalUnit;

public class FranjaHoraria implements I_CompatibilidadHorarios {
    LocalTime horaInicio;
    LocalTime horaCierre;
    Duration duracion;

    private final TemporalQuery<Boolean> turnoHabilitado = inicio  -> {
        LocalTime ini = LocalTime.from(inicio);
        return ini.isAfter(this.horaInicio)
                && ini.isBefore(this.horaCierre);
    };

    public FranjaHoraria(LocalTime horaInicio, LocalTime horarioFin) {
        this.horaInicio = horaInicio;
        this.horaCierre = horarioFin;
        this.duracion = Duration.between(horaInicio, horarioFin);
    }

    public FranjaHoraria(LocalTime horaInicio, Duration duracion) {
        this.horaInicio = horaInicio;
        this.duracion = duracion;
        this.horaCierre = horaInicio.plus(duracion);
    }

    public FranjaHoraria(int horaInicio, int duracionMinutos){
        this.horaInicio = LocalTime.of(horaInicio, 0);
        this.duracion = Duration.of(duracionMinutos, ChronoUnit.MINUTES);
    }

    public FranjaHoraria(int horaInicio, int minutosInicio, int duracionMinutos){
        this.horaInicio = LocalTime.of(horaInicio, minutosInicio);
        this.duracion = Duration.of(duracionMinutos, ChronoUnit.MINUTES);
    }


    public <T extends Turno> boolean citaCompatibleConFranjaHoraria(T turno) {
        LocalTime horaInicioTurno = turno.getHoraInicio();
        LocalTime horaFinTurno = turno.getHoraFin();


        return turnoHabilitado.queryFrom(horaInicioTurno)
                && turnoHabilitado.queryFrom(horaFinTurno);
    }

    public LocalTime getHoraInicio() {
        return this.horaInicio;
    }

    public LocalTime getHoraCierre() {
        return horaCierre;
    }

    public Duration getDuracion() {
        return duracion;
    }

    public boolean noColisiona(FranjaHoraria franja){
        if (this.getHoraInicio().isBefore(franja.getHoraInicio()))
            if (this.getHoraCierre().compareTo(franja.horaInicio) < 1) return true;
        return this.getHoraInicio().compareTo(franja.horaCierre) > -1;
    }
}
