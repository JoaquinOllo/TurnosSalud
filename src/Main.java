
import Excepciones.UsuarioInvalidoException;

import Usuarios.Consultante;
import Utilidades.GestionSistema;
import Utilidades.Interfaz;

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