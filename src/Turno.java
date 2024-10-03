import javax.xml.datatype.DatatypeConfigurationException;
import java.sql.Time;

public class Turno extends Cita{
    public Turno(int duracion, Time horario) throws DatatypeConfigurationException {
        super(duracion, horario);
    }
}
