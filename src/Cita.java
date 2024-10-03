import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.Duration;
import javax.xml.datatype.DatatypeFactory;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public abstract class Cita {
    private LocalDate dia;
    private LocalTime horario;


    public Cita(int duracionsegundos, LocalDateTime horarioInicio) throws DatatypeConfigurationException {
        duracion = df.newDuration(duracionsegundos);
        horario = horarioInicio.toLocalTime();
    }
}