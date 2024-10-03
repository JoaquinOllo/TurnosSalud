package Citas;

import Locaciones.Consultorio;
import Usuarios.Consultante;
import Usuarios.Profesional;
import Usuarios.Usuario;

import javax.xml.datatype.DatatypeConfigurationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Cita {
    private LocalDate dia;
    private LocalTime horario;
    private EstadoCita estado;
    private Consultante consultante;
    private Profesional profesional;
    private Usuario agendadoPor;
    private Consultorio consultorio;


    public Cita(int duracionsegundos, LocalDateTime horarioInicio) throws DatatypeConfigurationException {
        dia = horarioInicio.toLocalDate();
        horario = horarioInicio.toLocalTime();
    }




}