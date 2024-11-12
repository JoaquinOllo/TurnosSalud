package Utilidades;

import Citas.Turno;
import Interfaces.I_CompatibilidadHorarios;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalQuery;
import java.util.HashSet;
import java.util.stream.Collectors;

public class FranjaHoraria implements I_CompatibilidadHorarios {
    LocalTime horaInicio;
    LocalTime horaCierre;
    Duration duracion;

    private final TemporalQuery<Boolean> turnoHabilitado = inicio  -> {
        LocalTime ini = LocalTime.from(inicio);
        return (ini.isAfter(this.horaInicio) | ini.equals(this.horaInicio))
                && (ini.equals(this.horaCierre) | ini.isBefore(this.horaCierre));
    };

    public FranjaHoraria(LocalTime horaInicio, LocalTime horarioFin) {
        this.horaInicio = horaInicio;
        this.horaCierre = horarioFin;
        this.duracion = Duration.between(horaInicio, horarioFin);
    }
    public FranjaHoraria(int horaInicio, int minutosInicio, int horaFin, int minutosFin){
        this.horaInicio = LocalTime.of(horaInicio,minutosInicio);
        this.horaCierre = LocalTime.of(horaFin,minutosFin);
        this.duracion = Duration.between(this.horaInicio,this.horaCierre);
    }

    public FranjaHoraria(LocalTime horaInicio, Duration duracion) {
        this.horaInicio = horaInicio;
        this.duracion = duracion;
        this.horaCierre = horaInicio.plus(duracion);
    }

    public FranjaHoraria(int horaInicio, int duracionMinutos){
        this.horaInicio = LocalTime.of(horaInicio, 0);
        this.duracion = Duration.of(duracionMinutos, ChronoUnit.MINUTES);
        this.horaCierre = this.horaInicio.plus(this.duracion);
    }

    public FranjaHoraria(int horaInicio, int minutosInicio, int duracionMinutos){
        this.horaInicio = LocalTime.of(horaInicio, minutosInicio);
        this.duracion = Duration.of(duracionMinutos, ChronoUnit.MINUTES);
        this.horaCierre = this.horaInicio.plus(this.duracion);
    }


    public <T extends Turno> boolean citaCompatibleConFranjaHoraria(T turno) {
        LocalTime horaInicioTurno = turno.getHoraInicio();
        LocalTime horaFinTurno = turno.getHoraFin();


        return turnoHabilitado.queryFrom(horaInicioTurno)
                && turnoHabilitado.queryFrom(horaFinTurno);
    }

    public HashSet<LocalTime> getHorariosHabilitados(Agenda<Turno> turnos, int duracionTurnoEnMinutos) {
        HashSet<LocalTime> horariosDisponibles = this.dividirSegunDuracion(duracionTurnoEnMinutos).stream().map(e -> e.horaInicio).collect(Collectors.toCollection(HashSet::new));

        HashSet<LocalTime> horariosTomados = turnos.stream().map(Turno::getHoraInicio).collect(Collectors.toCollection(HashSet::new));

        horariosDisponibles.removeAll(horariosTomados);

        return horariosDisponibles;
    }

    public boolean quedanEspaciosEnFranja(HashSet<FranjaHoraria> turnosDelDia, int duracionTurnoMinutos) {
        HashSet<FranjaHoraria> horariosDisponibles = this.dividirSegunDuracion(duracionTurnoMinutos);
        horariosDisponibles.removeAll(turnosDelDia);
        return ! horariosDisponibles.isEmpty();
    }

    public <T extends Turno> boolean citaCompatibleConFranjaHoraria(FranjaHoraria franja) {
        LocalTime horaInicioTurno = franja.horaInicio;
        LocalTime horaFinTurno = franja.horaCierre;

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

    public HashSet<FranjaHoraria> dividirSegunDuracion (int duracionMinutos){
        HashSet<FranjaHoraria> listaHorarios = new HashSet<>();
        LocalTime tiempoInicio = this.horaInicio;
        LocalTime tiempoCierre = this.horaInicio.plus(duracionMinutos, ChronoUnit.MINUTES);
        while (!tiempoInicio.equals(this.horaCierre) && tiempoCierre.isBefore(this.horaCierre) || tiempoCierre.equals(this.horaCierre)){
            listaHorarios.add(new FranjaHoraria(tiempoInicio, tiempoCierre));
            tiempoInicio = tiempoCierre;
            tiempoCierre = tiempoInicio.plus(duracionMinutos, ChronoUnit.MINUTES);

        }
        return listaHorarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FranjaHoraria franja = (FranjaHoraria) o;
        return this.horaInicio == franja.horaInicio
                && this.horaCierre == franja.horaCierre;
    }

    @Override
    public String toString() {
        return "FranjaHoraria{" +
                "horaInicio=" + horaInicio +
                ", horaCierre=" + horaCierre +
                ", duracion=" + duracion +
                '}';
    }
}
