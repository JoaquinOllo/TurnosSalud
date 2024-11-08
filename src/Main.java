
import Excepciones.UsuarioInvalidoException;

import Utilidades.GestionSistema;

public class Main {
    public static void main(String[] args) {

        GestionSistema sistema = new GestionSistema();
        try {
            sistema.arranqueParaPruebas();
        } catch (UsuarioInvalidoException e) {
            throw new RuntimeException(e);
        }
    }
}