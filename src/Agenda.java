import Citas.Cita;
import Locaciones.Consultorio;
import Usuarios.Profesional;

import java.sql.Array;
import java.util.ArrayList;

public class Agenda<T extends Cita> extends ArrayList<T> {

    public boolean franjaDisponible(T turno) {
        Agenda<T> agendaFiltrada = this.filtrarPorProfesional(turno.getProfesional()).filtrarPorConsultorio(turno.getConsultorio());
        //completar
        return agendaFiltrada.stream().anyMatch(t -> t.colisiona(turno));
    }

    private Agenda<T> filtrarPorConsultorio(Consultorio consultorio) {
        //completar
        return this;
    }

    private Agenda<T> filtrarPorProfesional(Profesional profesional) {
        //completar
        return this;
    }
}
