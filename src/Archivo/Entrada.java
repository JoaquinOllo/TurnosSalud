package Archivo;

import java.time.LocalDate;

public class Entrada {
    private String texto;
    private LocalDate fecha;

    public String getTexto() {
        return texto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
