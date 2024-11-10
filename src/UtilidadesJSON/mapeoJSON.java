package UtilidadesJSON;

import Citas.Turno;
import Enumeradores.EstadoCita;
import Excepciones.UsuarioInexistenteException;
import Excepciones.UsuarioInvalidoException;
import Locaciones.Consultorio;
import Locaciones.Sede;
import Usuarios.*;
import Utilidades.FranjaHoraria;
import Utilidades.GestionSistema;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONArray;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;


public class mapeoJSON {
    public static void mapeoSistema(GestionSistema sistema){
        try {
            // Leer el archivo JSON
            JSONObject turnosSaludJson = new JSONObject(JSONUtiles.leer("./turnos.json"));

            // Procesar Usuarios
            ListaUsuarios<Usuario> usuarios = new ListaUsuarios<>();
            JSONArray usuariosArray = turnosSaludJson.getJSONArray("Usuarios");
            for (int i = 0; i < usuariosArray.length(); i++) {
                JSONObject usuarioJson = usuariosArray.getJSONObject(i);
                String rol = usuarioJson.getString("rol");
                Usuario usuario;

                try {
                    switch (rol) {
                        case "consultante":
                            usuario = new Consultante(
                                    usuarioJson.getString("nombre"),
                                    usuarioJson.getString("apellido"),
                                    usuarioJson.getInt("edad"),
                                    usuarioJson.getString("correo"),
                                    usuarioJson.getString("telefono"),
                                    usuarioJson.getString("nombreUsuario"),
                                    usuarioJson.getString("contrasena"),
                                    usuarioJson.getString("direccion")
                            );
                            break;
                        case "profesional":
                            HashSet<FranjaHoraria> horarios = new HashSet<>();
                            JSONArray horariosArray = usuarioJson.getJSONArray("horarios");
                            for (int j = 0; j < horariosArray.length(); j++) {
                                JSONObject horarioJson = horariosArray.getJSONObject(j);
                                horarios.add(new FranjaHoraria(
                                        LocalTime.parse(horarioJson.getString("inicio")),
                                        LocalTime.parse(horarioJson.getString("fin"))
                                ));
                            }
                            usuario = new Profesional(
                                    usuarioJson.getString("nombre"),
                                    usuarioJson.getString("apellido"),
                                    usuarioJson.getInt("edad"),
                                    usuarioJson.getString("correo"),
                                    usuarioJson.getString("telefono"),
                                    usuarioJson.getString("nombreUsuario"),
                                    usuarioJson.getString("contrasena"),
                                    usuarioJson.getString("direccion"),
                                    usuarioJson.getString("especialidad"),
                                    horarios
                            );
                            break;
                        case "administrador":
                            usuario = new Administrador(
                                    usuarioJson.getString("nombre"),
                                    usuarioJson.getString("apellido"),
                                    usuarioJson.getInt("edad"),
                                    usuarioJson.getString("correo"),
                                    usuarioJson.getString("telefono"),
                                    usuarioJson.getString("nombreUsuario"),
                                    usuarioJson.getString("contrasena"),
                                    usuarioJson.getString("direccion")
                            );
                            break;
                        case "administrativo":
                            usuario = new Administrativo(
                                    usuarioJson.getString("nombre"),
                                    usuarioJson.getString("apellido"),
                                    usuarioJson.getInt("edad"),
                                    usuarioJson.getString("correo"),
                                    usuarioJson.getString("telefono"),
                                    usuarioJson.getString("nombreUsuario"),
                                    usuarioJson.getString("contrasena"),
                                    usuarioJson.getString("direccion")
                            );
                            break;
                        default:
                            throw new IllegalArgumentException("Rol de usuario desconocido: " + rol);
                    }
                    usuarios.add(usuario);
                } catch (UsuarioInvalidoException e) {
                    throw new RuntimeException("No se pudo cargar el usuario: " + e.getMessage());
                }
            }
            sistema.setUsuarios(usuarios);

            // Procesar Sedes
            ArrayList<Sede> sedes = new ArrayList<>();
            JSONArray sedesArray = turnosSaludJson.getJSONArray("Sedes");
            for (int i = 0; i < sedesArray.length(); i++) {
                JSONObject sedeNode = sedesArray.getJSONObject(i);
                // Horarios de la sede
                ArrayList<FranjaHoraria> horariosSede = new ArrayList<>();
                JSONArray sedeHorariosArray = sedeNode.getJSONArray("horarios");
                for (int j = 0; j < sedeHorariosArray.length(); j++) {
                    JSONObject horarioJson = sedeHorariosArray.getJSONObject(j);
                    horariosSede.add(new FranjaHoraria(
                            LocalTime.parse(horarioJson.getString("inicio")),
                            LocalTime.parse(horarioJson.getString("fin"))
                    ));
                }

                // Crear la sede
                Sede sede = new Sede(
                        sedeNode.getString("nombre"),
                        sedeNode.getString("direccion"),
                        horariosSede
                );
                try {
                    sede.setResponsable(sistema.getUsuarioPorNombreUsuario(sedeNode.getString("responsable")));
                } catch (UsuarioInexistenteException e) {
                    throw new RuntimeException(e);
                }

                // Consultorios de la sede
                ArrayList<Consultorio> consultorios = new ArrayList<>();
                JSONArray consultoriosArray = sedeNode.getJSONArray("consultorios");
                for (int j = 0; j < consultoriosArray.length(); j++) {
                    JSONObject consultorioNode = consultoriosArray.getJSONObject(j);
                    Consultorio consultorio = new Consultorio(
                            consultorioNode.getInt("numero"),
                            sede
                    );

                    // Turnos del consultorio
                    DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    ArrayList<Turno> turnos = new ArrayList<>();
                    JSONArray turnosArray = consultorioNode.getJSONArray("turnos");
                    for (int k = 0; k < turnosArray.length(); k++) {
                        JSONObject turnoJson = turnosArray.getJSONObject(k);
                        try {
                            Turno turno = new Turno(
                                    LocalDate.parse(turnoJson.getString("dia"), formatoFecha),
                                    new FranjaHoraria(
                                            LocalTime.parse(turnoJson.getJSONObject("horario").getString("inicio")),
                                            LocalTime.parse(turnoJson.getJSONObject("horario").getString("fin"))
                                    ),
                                    sistema.getUsuarioPorNombreUsuario(turnoJson.getString("consultante")),
                                    sistema.getUsuarioPorNombreUsuario(turnoJson.getString("profesional")),
                                    sistema.getUsuarioPorNombreUsuario(turnoJson.getString("agendadoPor")),
                                    EstadoCita.valueOf(turnoJson.getString("estado")),
                                    turnoJson.getString("razon")
                            );
                            turno.setConsultorio(consultorio);
                            turnos.add(turno);
                            System.out.println(turno);
                        } catch (UsuarioInexistenteException e) {
                            System.out.println("error en carga de turno " + e.getMessage());
                            throw new RuntimeException(e.getMessage());
                        }
                    }
                    consultorio.setTurnos(turnos);
                    consultorios.add(consultorio);
                }
                sede.setConsultorios(consultorios);
                sedes.add(sede);
            }
            sistema.setSedes(sedes);

            // Imprimir los resultados (opcional)
            System.out.println("Usuarios: " + sistema.getUsuarios());
            System.out.println("Sedes: " + sistema.getSedes());
            System.out.println("Turnos: " + sistema.getTurnos());

            for (Sede sede: sistema.getSedes()){
                for (Consultorio cons: sede.getConsultorios()){
                    System.out.println(cons.getTurnos());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}