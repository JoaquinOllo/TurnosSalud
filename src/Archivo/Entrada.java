package Archivo;

import Usuarios.Profesional;

import java.time.LocalDate;

public class Entrada {
    private String texto;
    private LocalDate fecha;
    private Profesional autor;

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
