import Citas.Cita;
import Locaciones.Consultorio;
import Usuarios.Profesional;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Agenda<T extends Cita> extends ArrayList<T> {

    public boolean franjaDisponible(T turno) {
        Agenda<T> agendaFiltrada = this.filtrarPorProfesional(turno.getProfesional()).filtrarPorConsultorio(turno.getConsultorio());
        //completar
        return agendaFiltrada.stream().anyMatch(t -> t.colisiona(turno));//recorre el array de agenda filtrada
    }

    private Agenda<T> filtrarPorConsultorio(Consultorio consultorio) {
        Agenda <T> turnosPorConsultorio=new Agenda<>();
        for (T turno:this){
            if(turno.getConsultorio().equals(consultorio)){
                turnosPorConsultorio.add(turno);
            }
        }
        return turnosPorConsultorio;
    }

    private Agenda<T> filtrarPorProfesional(Profesional profesional) {
        Agenda<T> turnosPorProfesional=new Agenda<>();
        for(T turno:this){
            if(turno.getProfesional().equals(profesional)){
                turnosPorProfesional.add(turno);
            }
        }
        return turnosPorProfesional;
    }
}
