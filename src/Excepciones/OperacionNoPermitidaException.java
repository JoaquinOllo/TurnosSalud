package Excepciones;

import java.io.IOException;

public class OperacionNoPermitidaException extends IOException {
    public OperacionNoPermitidaException(String message, String posponer) {
        super(message);
    }
}
