package UtilidadesJSON;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONUtiles {

    public static void grabar(JSONObject array, String nombreArchivo) {
        try {
            FileWriter file = new FileWriter(nombreArchivo);
            file.write(array.toString());
            file.flush();
            file.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    public static JSONTokener leer(String archivo) {
        JSONTokener tokener = null;

        try {
            FileReader archivoLectura = new FileReader(archivo);
            tokener = new JSONTokener(archivoLectura);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tokener;
    }
}