package Excepciones;

import java.time.LocalDateTime;

public class HorarioNoDisponibleException extends Exception {

    public HorarioNoDisponibleException(LocalDateTime fechaHora, String causa) {

    }
}
