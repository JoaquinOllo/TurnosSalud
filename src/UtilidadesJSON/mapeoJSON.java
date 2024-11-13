package UtilidadesJSON;

import Citas.Turno;
import Enumeradores.EstadoCita;
import Excepciones.LugarNoDisponibleException;
import Excepciones.UsuarioInexistenteException;
import Excepciones.NombreInvalidoException;
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
    public static String nombreArchivo = "./turnos.json";

    public static void mapeoSistema(GestionSistema sistema){
        try {
            // Leer el archivo JSON
            JSONObject turnosSaludJson = new JSONObject(JSONUtiles.leer(nombreArchivo));

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
                } catch (NombreInvalidoException e) {
                    System.out.println("No se pudo cargar el usuario: " + e.getMessage());
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
                Sede sede = null;
                try {
                    sede = new Sede(
                            sedeNode.getString("nombre"),
                            sedeNode.getString("direccion"),
                            horariosSede
                    );
                    try {
                        sede.setResponsable(sistema.getUsuarioPorNombreUsuario(sedeNode.getString("responsable")));
                    } catch (UsuarioInexistenteException e) {
                        System.out.println("No se pudo definir responsable para la sede " + sede.getNombre());
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
                                        EstadoCita.valueOf(turnoJson.getString("estado"))
                                );
                                if(turnoJson.has("razon")){
                                    turno.setRazon(turnoJson.getString("razon"));
                                }
                                turno.setConsultorio(consultorio);
                                turnos.add(turno);
                                System.out.println(turno);
                            } catch (UsuarioInexistenteException e) {
                                System.out.println("error en carga de turno " + e.getMessage());
                            }
                        }
                        consultorio.setTurnos(turnos);
                        consultorios.add(consultorio);
                    }
                    sede.setConsultorios(consultorios);
                    sedes.add(sede);

                } catch (NombreInvalidoException e) {
                    throw new RuntimeException(e);
                }

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

            for (int i = 0; i < usuariosArray.length(); i++) {
                JSONObject usuarioJson = usuariosArray.getJSONObject(i);
                String rol = usuarioJson.getString("rol");
                String nombreUsuario = usuarioJson.getString("nombreUsuario");

                try {
                    if (rol.equals("administrativo")) {
                        String nombreSede = usuarioJson.getString("sede");
                        Sede sede = sistema.getSede(nombreSede);
                        Usuario usuario =  sistema.getUsuarioPorNombreUsuario(nombreUsuario);
                        ((Administrativo) usuario).setSede(sede);
                    }
                } catch (LugarNoDisponibleException | UsuarioInexistenteException e) {
                    throw new RuntimeException(e);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void guardadoUsuarios(ListaUsuarios<Usuario> usuarios){
        try {
            // Leer el archivo JSON
            JSONObject turnosSaludJson = new JSONObject(JSONUtiles.leer(nombreArchivo));

            // Guardar Usuarios
            JSONArray usuariosArray = new JSONArray();
            for (Usuario usuario : usuarios) {
                JSONObject usuarioJson = new JSONObject();

                try {
                    // Datos comunes a todos los usuarios
                    usuarioJson.put("nombre", usuario.getNombre());
                    usuarioJson.put("apellido", usuario.getApellido());
                    usuarioJson.put("edad", usuario.getEdad());
                    usuarioJson.put("correo", usuario.getCorreo());
                    usuarioJson.put("telefono", usuario.getNroTelefono());
                    usuarioJson.put("nombreUsuario", usuario.getNombreUsuario());
                    usuarioJson.put("contrasena", usuario.getContrasenha());
                    usuarioJson.put("direccion", usuario.getDireccion());

                    // Determinar el rol y datos específicos
                    if (usuario instanceof Profesional) {
                        usuarioJson.put("rol", "profesional");
                        Profesional profesional = (Profesional) usuario;
                        usuarioJson.put("especialidad", profesional.getEspecialidad());

                        // Guardar horarios del profesional
                        JSONArray horariosArray = new JSONArray();
                        for (FranjaHoraria franja : profesional.getHorarioDeTrabajo()) {
                            JSONObject horarioJson = new JSONObject();
                            horarioJson.put("inicio", franja.getHoraInicio().toString());
                            horarioJson.put("fin", franja.getHoraCierre().toString());
                            horariosArray.put(horarioJson);
                        }
                        usuarioJson.put("horarios", horariosArray);
                    } else if (usuario instanceof Consultante) {
                        usuarioJson.put("rol", "consultante");
                    } else if (usuario instanceof Administrador) {
                        usuarioJson.put("rol", "administrador");
                    } else if (usuario instanceof Administrativo) {
                        usuarioJson.put("rol", "administrativo");
                        usuarioJson.put("sede", ((Administrativo) usuario).getSede().getNombre());
                    }
                    usuariosArray.put(usuarioJson);
                } catch (JSONException e) {
                    System.out.println("Error en el guardado del usuario. ");
                }
            }
            if (usuariosArray.length() != 0){
                turnosSaludJson.put("Usuarios", usuariosArray);
            } else{
                throw new JSONException("No se guardan usuarios debido a que se borraría el listado completo.");
            }
            // Guardar en archivo usando JSONUtiles
            if(turnosSaludJson.has("Usuarios")){
                JSONUtiles.grabar(turnosSaludJson, nombreArchivo);
            } else {
                System.out.println("No se guarda el cambio en el registro de usuarios, debido a un error.");
            }
        } catch (JSONException e) {
            System.out.println("Error en el guardado de todos los usuarios. ");
            throw new RuntimeException(e);
        }

    }

    public static void guardadoSedesYTurnos(ArrayList<Sede> sedes) {
        try {
            // Leer el archivo JSON
            JSONObject turnosSaludJson = new JSONObject(JSONUtiles.leer("./turnos.json"));

            // Guardar Sedes
            JSONArray sedesArray = new JSONArray();
            for (Sede sede : sedes) {
                JSONObject sedeJson = new JSONObject();
                sedeJson.put("nombre", sede.getNombre());
                sedeJson.put("direccion", sede.getDireccion());
                sedeJson.put("responsable", sede.getResponsable().getNombreUsuario());

                // Guardar horarios de la sede
                JSONArray horariosSedeArray = new JSONArray();
                for (FranjaHoraria franja : sede.getHorarios()) {
                    JSONObject horarioJson = new JSONObject();
                    horarioJson.put("inicio", franja.getHoraInicio().toString());
                    horarioJson.put("fin", franja.getHoraCierre().toString());
                    horariosSedeArray.put(horarioJson);
                }
                sedeJson.put("horarios", horariosSedeArray);

                // Guardar consultorios
                JSONArray consultoriosArray = new JSONArray();
                for (Consultorio consultorio : sede.getConsultorios()) {
                    JSONObject consultorioJson = new JSONObject();
                    consultorioJson.put("numero", consultorio.getNumero());

                    // Guardar turnos del consultorio
                    JSONArray turnosArray = new JSONArray();
                    for (Turno turno : consultorio.getTurnos()) {
                        JSONObject turnoJson = new JSONObject();

                        // Formatear la fecha al formato requerido (dd-MM-yyyy)
                        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        turnoJson.put("dia", turno.getDia().format(formatoFecha));

                        // Guardar horario del turno
                        JSONObject horarioTurnoJson = new JSONObject();
                        horarioTurnoJson.put("inicio", turno.getHoraInicio().toString());
                        horarioTurnoJson.put("fin", turno.getHoraInicio().toString());
                        turnoJson.put("horario", horarioTurnoJson);

                        // Guardar referencias a usuarios por nombreUsuario
                        turnoJson.put("consultante", turno.getConsultante().getNombreUsuario());
                        turnoJson.put("profesional", turno.getProfesional().getNombreUsuario());
                        turnoJson.put("agendadoPor", turno.getAgendadoPor().getNombreUsuario());

                        turnoJson.put("estado", turno.getEstado().toString());
                        turnoJson.put("razon", turno.getRazon());

                        turnosArray.put(turnoJson);
                    }
                    consultorioJson.put("turnos", turnosArray);
                    consultoriosArray.put(consultorioJson);
                }
                sedeJson.put("consultorios", consultoriosArray);
                sedesArray.put(sedeJson);
            }
            turnosSaludJson.put("Sedes", sedesArray);

            // Guardar en archivo usando JSONUtiles
            JSONUtiles.grabar(turnosSaludJson, nombreArchivo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}