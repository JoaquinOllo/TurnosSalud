package UtilidadesFechaYHora;

import Citas.Cita;
import Interfaces.I_CompatibilidadHorarios;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.TemporalQuery;

public class FranjaHoraria implements I_CompatibilidadHorarios {
    LocalTime horaInicio;
    LocalTime horaCierre;
    Duration duracion;
    boolean franjaAbierta;

    private final TemporalQuery<Boolean> turnoHabilitado = inicio  -> {
        LocalTime ini = LocalTime.from(inicio);
        return ini.isAfter(this.horaInicio)
                && ini.isBefore(this.horaCierre);
    };

    public FranjaHoraria(LocalTime horaInicio, LocalTime horarioFin) {
        this.horaInicio = horaInicio;
        this.horaCierre = horarioFin;
        this.duracion = Duration.between(horaInicio, horarioFin);
        this.franjaAbierta = false;
    }

    public FranjaHoraria(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
        this.franjaAbierta = true;
    }

    public <T extends Cita> boolean citaCompatible(T turno) {
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

}
