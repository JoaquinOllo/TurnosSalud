package Utilidades;
import Citas.Turno;

import Enumeradores.Especialidad;
import Enumeradores.EstadoCita;
import Enumeradores.TipoDeVisualizacion;
import Excepciones.*;
import Interfaces.I_GestionAdministrativa;
import Interfaces.I_GestionTurnos;
import Locaciones.Sede;
import Usuarios.Administrativo;
import Usuarios.Consultante;
import Usuarios.Profesional;
import Usuarios.Usuario;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;

public class Interfaz {
    private GestionSistema sistema;

    private Usuario getUsuarioConectado(){
        return this.sistema.getUsuarioConectado();
    }

    public Interfaz(GestionSistema sistema) {
        this.sistema = sistema;
    }

    public void menuConexion() {
        JFrame frame = new JFrame("Bienvenido a SuperDoctors - Conexión");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);

        JTextField campoUsuario;
        JPasswordField campoContrasena;
        JButton botonIniciarSesion;
        JLabel etiquetaMensaje;

        frame.setLocationRelativeTo(null); // Centrar ventana

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(4, 1, 10, 10));

        // Campo de usuario
        JLabel etiquetaUsuario = new JLabel("Usuario:");
        campoUsuario = new JTextField(15);
        JPanel panelUsuario = new JPanel();
        panelUsuario.add(etiquetaUsuario);
        panelUsuario.add(campoUsuario);

        // Campo de contraseña
        JLabel etiquetaContrasena = new JLabel("Contraseña:");
        campoContrasena = new JPasswordField(15);
        JPanel panelContrasena = new JPanel();
        panelContrasena.add(etiquetaContrasena);
        panelContrasena.add(campoContrasena);

        panelPrincipal.add(panelUsuario);
        panelPrincipal.add(panelContrasena);
        panelPrincipal.setVisible(true);

        // Botón de inicio de sesión
        botonIniciarSesion = new JButton("Iniciar Sesión");
        botonIniciarSesion.addActionListener(e -> {
            try {
                if (this.sistema.conectarse(campoUsuario.getText(), campoContrasena)) {
                    frame.dispose();
                    menuInicial();
                } else {
                    throw new UsuarioInexistenteException();
                }
            } catch (UsuarioInexistenteException ex) {
                JDialog modalError = new JDialog(frame, "Credenciales incorrectas", true);
                JLabel mensaje = new JLabel("El usuario o la contraseña ingresados " +
                        "son incorrectos. " +
                        "Intente nuevamente.");
                mensaje.setHorizontalAlignment(SwingConstants.CENTER);

                // Crear el botón de cerrar
                JButton botonCerrar = new JButton("Cerrar");
                botonCerrar.addActionListener(b -> modalError.dispose());

                // Crear el panel principal y añadir componentes
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(mensaje);
                panel.add(Box.createVerticalStrut(10));
                panel.add(botonCerrar);

                // Configurar el diálogo
                modalError.getContentPane().add(panel);
                modalError.setSize(300, 150);
                modalError.setLocationRelativeTo(modalError.getParent());
                modalError.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                modalError.setVisible(true);

            }
        });

        panelPrincipal.add(botonIniciarSesion);

        frame.add(panelPrincipal);
        frame.setVisible(true);

    }

    public void menuInicial() {
        JFrame frame = new JFrame("SuperDoctors - " + sistema.getUsuarioConectado().getNombreUsuario() + " - "
                + sistema.getUsuarioConectado().getClass().getSimpleName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        // Crear un panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.CYAN);
        frame.setLocationRelativeTo(null);

        // Crear un JLabel para el título
        JLabel titleLabel = new JLabel("Bienvenido a SuperDoctors", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);
        mainPanel.add(titleLabel, BorderLayout.NORTH); // Agregar el título en la parte superior del panel

        // Crear un panel para el menú de opciones
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.CYAN);

        if (this.getUsuarioConectado() instanceof I_GestionAdministrativa) {
            JButton agregarPaciente = new JButton("Agregar Paciente");
            menuPanel.add(agregarPaciente);
            agregarPaciente.addActionListener(e -> {
                Consultante consultante = new Consultante();
                consultante = menuAgregarUsuario(consultante);
            });
        }

        if (this.getUsuarioConectado() instanceof I_GestionAdministrativa) {
            JButton verPacientes = new JButton("Ver Pacientes");
            menuPanel.add(verPacientes);
            verPacientes.addActionListener(e -> {
                menuVerUsuarios(this.sistema.getPacientes(),"Lista de pacientes");
            });
        }

        if (this.getUsuarioConectado() instanceof I_GestionTurnos &&
                ((I_GestionTurnos) this.getUsuarioConectado()).agendaTurnos()) {
            JButton agregarCita = new JButton("Agregar Cita");
            menuPanel.add(agregarCita);
            agregarCita.addActionListener(e -> {
                Turno t1 = new Turno(this.sistema.getUsuarioConectado());
                menuCita(t1);
            });
        }

        if (this.getUsuarioConectado() instanceof I_GestionTurnos) {
            JButton verCitas = new JButton("Ver Turnos");
            menuPanel.add(verCitas);
            verCitas.addActionListener(e -> {

                if (((I_GestionTurnos) this.getUsuarioConectado()).modalidadVisualizacionDeTurnos().equals(TipoDeVisualizacion.TODOS)){
                    menuVerTurnos(this.sistema.getTurnos());
                } else if (((I_GestionTurnos) this.getUsuarioConectado()).modalidadVisualizacionDeTurnos().equals(TipoDeVisualizacion.POR_SEDE)){
                    Sede sede = ((Administrativo) this.getUsuarioConectado()).getSede();
                    menuVerTurnos(this.sistema.getTurnos().filtrarPorSede(sede));
                } else if (((I_GestionTurnos) this.getUsuarioConectado()).modalidadVisualizacionDeTurnos().equals(TipoDeVisualizacion.PERSONAL)
                && this.getUsuarioConectado() instanceof Profesional){
                    menuVerTurnos(this.sistema.getTurnos().filtrarPorProfesional((Profesional) this.getUsuarioConectado()));
                } else if (((I_GestionTurnos) this.getUsuarioConectado()).modalidadVisualizacionDeTurnos().equals(TipoDeVisualizacion.PERSONAL)
                       && this.getUsuarioConectado() instanceof Consultante) {
                    menuVerTurnos(this.sistema.getTurnos().filtrarPorConsultante((Consultante) this.getUsuarioConectado()));

                }
            });
        }

        if (this.getUsuarioConectado() instanceof I_GestionAdministrativa &&
                ((I_GestionAdministrativa)this.getUsuarioConectado()).administraUsuarios()) {
            JButton cargarProfesional = new JButton("Cargar Nuevo Profesional");
            menuPanel.add(cargarProfesional);
            cargarProfesional.addActionListener(e -> {
                Profesional profesional = new Profesional();
                menuNuevoProfesional(profesional);
            });

            JButton cargarAdministrativo = new JButton("Cargar Nuevo Administrativo");
            menuPanel.add(cargarAdministrativo);
            cargarAdministrativo.addActionListener(e -> {
                Administrativo administrativo = new Administrativo();
                administrativo = menuAgregarUsuario(administrativo);
            });
            JButton verAdministrativos = new JButton("Ver Administrativos");
            menuPanel.add(verAdministrativos);
            verAdministrativos.addActionListener(e -> {
                menuVerUsuarios(this.sistema.getAdministrativos(),"Lista de Administrativos");
            });
            JButton verProfesionales = new JButton("Ver Profesionales");
            menuPanel.add(verProfesionales);
            verProfesionales.addActionListener(e -> {
                menuVerUsuarios(this.sistema.getProfesionales(),"Lista de Profesionales");
            });
        }


        if (this.getUsuarioConectado() instanceof I_GestionTurnos) {
            if (((I_GestionTurnos) this.getUsuarioConectado()).confirmaTurnos()) {
                JButton confirmarTurnoBtn = new JButton("Confirmar turno");
                menuPanel.add(confirmarTurnoBtn);
                confirmarTurnoBtn.addActionListener(e -> {
                    Agenda<Turno> turnos = this.sistema.getTurnos().filtrarPorEstado(EstadoCita.PENDIENTE_CONFIRMACION);
                    switch (((I_GestionTurnos) this.getUsuarioConectado()).modalidadVisualizacionDeTurnos()) {
                        case PERSONAL:
                            if(this.getUsuarioConectado() instanceof Profesional){
                                turnos.filtrarPorProfesional((Profesional) this.getUsuarioConectado());
                            }else {
                                turnos.filtrarPorConsultante((Consultante) this.getUsuarioConectado());
                            }
                            break;
                        case TODOS:
                            break;
                        case POR_SEDE:
                            turnos.filtrarPorSede(((Administrativo)this.getUsuarioConectado()).getSede());
                            break;
                    }
                    menuCambiarEstadoTurno(turnos, EstadoCita.CONFIRMADO, false);
                });
            }
            if (((I_GestionTurnos) this.getUsuarioConectado()).cancelaTurnos()) {
                JButton cancelarTurnoBtn = new JButton("Cancelar turno");
                menuPanel.add(cancelarTurnoBtn);
                cancelarTurnoBtn.addActionListener(e -> {
                    Agenda<Turno> turnos = this.sistema.getTurnos().filtrarPorEstado(EstadoCita.PENDIENTE_CONFIRMACION);
                    switch (((I_GestionTurnos) this.getUsuarioConectado()).modalidadVisualizacionDeTurnos()) {
                        case PERSONAL:
                            if(this.getUsuarioConectado() instanceof Profesional){
                                turnos.filtrarPorProfesional((Profesional) this.getUsuarioConectado());
                            }else {
                                turnos.filtrarPorConsultante((Consultante) this.getUsuarioConectado());
                            }
                            break;
                            case TODOS:
                                break;
                            case POR_SEDE:
                                turnos.filtrarPorSede(((Administrativo)this.getUsuarioConectado()).getSede());
                                break;
                        }
                    menuCambiarEstadoTurno(turnos.filtrarPorTurnosNoCancelados(), EstadoCita.CANCELADO, false);
            });
        }

            if (((I_GestionTurnos) this.getUsuarioConectado()).reprogramaTurnos()) {

                JButton reprogramarTurnoBtn = new JButton("Reprogramar turno");
                menuPanel.add(reprogramarTurnoBtn);

                reprogramarTurnoBtn.addActionListener(e -> {
                    Agenda<Turno> turnos = this.sistema.getTurnos().filtrarPorEstado(EstadoCita.PENDIENTE_CONFIRMACION);

                    // Filtrar los turnos según la modalidad de visualización
                    switch (((I_GestionTurnos) this.getUsuarioConectado()).modalidadVisualizacionDeTurnos()) {
                        case PERSONAL:
                            if (this.getUsuarioConectado() instanceof Profesional) {
                                turnos.filtrarPorProfesional((Profesional) this.getUsuarioConectado());
                            } else {
                                turnos.filtrarPorConsultante((Consultante) this.getUsuarioConectado());
                            }
                            break;
                        case TODOS:
                            break;
                        case POR_SEDE:
                            turnos.filtrarPorSede(((Administrativo) this.getUsuarioConectado()).getSede());
                            break;
                    }

                    // Método para seleccionar el turno a posponer
                    menuCambiarEstadoTurno(turnos.filtrarPorTurnosNoCancelados(), EstadoCita.PENDIENTE_CONFIRMACION, true);

                });
            }
        }

        JButton salir = new JButton("Salir");
        menuPanel.add(salir);

        // Agregar el panel del menú al panel principal
        mainPanel.add(menuPanel, BorderLayout.CENTER); // Agregar el panel del menú en el centro del panel principal

        salir.addActionListener(e -> {
            System.exit(0);
        });

        // Agregar el panel principal al marco
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void menuCambiarEstadoTurno(ArrayList<Turno> turnos, EstadoCita estadoCita, boolean reprogramarFecha) {
        JFrame ventana = new JFrame("Seleccionar Turno");
        ventana.setLayout(new BoxLayout(ventana.getContentPane(), BoxLayout.Y_AXIS));

        ButtonGroup grupoTurnos = new ButtonGroup();
        JPanel panelTurnos = new JPanel();
        panelTurnos.setLayout(new BoxLayout(panelTurnos, BoxLayout.Y_AXIS));

        ArrayList<JRadioButton> botonesTurno = new ArrayList<>();

        // Crear radio buttons para cada turno
        for (Turno turno : turnos) {
            JRadioButton botonTurno = new JRadioButton(
                    "Paciente: " + turno.getConsultante() + ", Día: " + turno.getDia() + ", Hora: " + turno.getHoraInicio());
            botonesTurno.add(botonTurno);
            grupoTurnos.add(botonTurno);

            JPanel panelTurno = new JPanel();
            panelTurno.setLayout(new BoxLayout(panelTurno, BoxLayout.Y_AXIS));

            JPanel panelPrincipal = new JPanel();

            // Crear los JLabel con la información del turno
            JLabel pacienteLabel = new JLabel("Nombre del paciente: " + turno.getConsultante());
            JLabel diaLabel = new JLabel("Día: " + turno.getDia());
            JLabel horaLabel = new JLabel("Hora: " + turno.getHoraInicio());
            JLabel especialidadLabel = new JLabel("Especialidad: " + turno.getEspecialidad());
            JLabel profesionalLabel = new JLabel("Nombre Profesional: " + turno.getProfesional());
            JLabel consultorioLabel = new JLabel("Consultorio: " + turno.getConsultorio());
            JLabel estadoLabel = new JLabel("Estado actual: " + turno.getEstado());

            // Añadir los componentes al panel del turno
            panelTurno.add(botonTurno);
            panelTurno.add(pacienteLabel);
            panelTurno.add(diaLabel);
            panelTurno.add(horaLabel);
            panelTurno.add(especialidadLabel);
            panelTurno.add(profesionalLabel);
            panelTurno.add(consultorioLabel);
            panelTurno.add(estadoLabel);

            Border borde = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE);
            panelTurno.setBorder(borde);
            panelTurnos.add(panelTurno);
        }

        // Botón de confirmación
        JButton confirmarBtn = new JButton("Confirmar");
        confirmarBtn.addActionListener(e -> {
                // Verificar cuál botón está seleccionado
                for (int i = 0; i < botonesTurno.size(); i++) {
                    if (botonesTurno.get(i).isSelected()) {
                        // Actualizar el estado del turno seleccionado
                        Turno turnoSeleccionado = turnos.get(i);
                        turnoSeleccionado.setEstado(estadoCita);
                        if (! reprogramarFecha){
                            JOptionPane.showMessageDialog(ventana, "El estado del turno se ha actualizado a: " + estadoCita.toString().toLowerCase());
                            ventana.dispose();  // Cerrar la ventana
                        } else {
                            reprogramarFechaYHora(turnoSeleccionado, "Elija nueva fecha y horario para el turno.");
                        }
                        break;
                    }
                }
        });

//        ventana.add(panelTurnos);
//        ventana.add(confirmarBtn);
        panelTurnos.add(confirmarBtn);

        JScrollPane scrollPane = new JScrollPane(panelTurnos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Agregar el JScrollPane al frame
        ventana.add(scrollPane);

        // Configurar la ventana
        ventana.setSize(500, 700);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setVisible(true);
    }

    private void reprogramarFechaYHora(Turno turno, String textoTitulo) {
        Sede sede = turno.getSede();
        final boolean[] datosSeleccionados = new boolean[]{false, false};

        // Crear el marco de la ventana
        JFrame frame = new JFrame(textoTitulo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 400);  // Aumentamos el tamaño para los campos de texto

        // Crear un panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.CYAN); // Color de fondo del panel principal

        // Crear un JLabel para el título
        JLabel titleLabel = new JLabel(textoTitulo, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Cambiar la fuente y tamaño
        titleLabel.setForeground(Color.BLACK); // Color del texto del título
        mainPanel.add(titleLabel, BorderLayout.NORTH); // Agregar el título en la parte superior del panel

        JPanel panelTurno = new JPanel();
        panelTurno.setLayout(new BoxLayout(panelTurno, BoxLayout.Y_AXIS));

        // Crear los componentes para mostrar la información del turno
        JLabel pacienteLabel = new JLabel("Nombre del paciente: " + turno.getConsultante());
        JLabel diaLabel = new JLabel("Dia Previo a la Reprogramación: " + turno.getDia());
        JLabel horaLabel = new JLabel("Hora Previa a la Reprogramación: " + turno.getHoraInicio());
        JLabel especialidadLabel = new JLabel("Especialidad: " + Especialidad.CARDIOLOGIA);
        JLabel profesionalLabel = new JLabel("Nombre Profesional: " + turno.getProfesional());
        JLabel consultorioLabel = new JLabel("Consultorio: " + turno.getConsultorio());
        JLabel estadoLabel = new JLabel("Estado: " + turno.getEstado());

        // Agregar los JLabel al panel del turno
        panelTurno.add(pacienteLabel);
        panelTurno.add(diaLabel);
        panelTurno.add(horaLabel);
        panelTurno.add(especialidadLabel);
        panelTurno.add(profesionalLabel);
        panelTurno.add(consultorioLabel);
        panelTurno.add(estadoLabel);

        // Añadir un borde al panel del turno para separarlo de los demás
        panelTurno.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));

        JPanel panelNuevaFechaHora = new JPanel();
        ArrayList<LocalDate> fechasHabilitadas = this.sistema.getFechasHabilitadas(turno.getProfesional());

        // ComboBoxModel<LocalDate> fechasDisponiblesModel = new DefaultComboBoxModel<>();
        ComboBoxModel<LocalTime> horariosDisponiblesModel = new DefaultComboBoxModel<>();


        JComboBox<LocalDate> elegirDia = new JComboBox(fechasHabilitadas.toArray());
        JComboBox<LocalTime> elegirHorario = new JComboBox<>(horariosDisponiblesModel);
        JButton confirmar = new JButton("confirmar cambio");

        elegirDia.setEnabled(true);
        elegirHorario.setEnabled(false);

        panelNuevaFechaHora.add(elegirDia);
        panelNuevaFechaHora.add(elegirHorario);
        panelNuevaFechaHora.add(confirmar);

        elegirDia.addActionListener(e -> {
            turno.setDia((LocalDate) elegirDia.getSelectedItem());
            elegirHorario.removeAllItems();
            ArrayList<LocalTime> horariosHabilitados = this.sistema.getHorariosDisponibles(turno.getProfesional(), sede, turno.getDia());
            ComboBoxModel<LocalTime> horarioModelo = new DefaultComboBoxModel<>(
                    horariosHabilitados.toArray(new LocalTime[0])
            );
            elegirHorario.setModel(horarioModelo);
            elegirHorario.setEnabled(true);
            datosSeleccionados[0] = true;
        });

        elegirHorario.addActionListener(e -> {
            try {
                Duration duracionTurno = Duration.of(turno.getProfesional().getDuracionTurnoMinutos(), ChronoUnit.MINUTES);
                FranjaHoraria franja = new FranjaHoraria((LocalTime) Objects.requireNonNull(elegirHorario.getSelectedItem()), duracionTurno);
                turno.setHorario(franja);
                turno.setConsultorio(sede.buscarConsultorioDisponible(this.sistema.getTurnos(), turno.getDia(), turno.getFranjaHoraria()));
                System.out.println("Consultorio asignado: " + turno.getConsultorio());
                confirmar.setEnabled(true);
                datosSeleccionados[1] = true;
            } catch (LugarNoDisponibleException ex) {
                JOptionPane.showMessageDialog(frame, "No hay consultorios disponibles para la fecha y hora seleccionadas. Elija otra hora.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        confirmar.addActionListener(e -> {
            if (datosSeleccionados[0] && datosSeleccionados[1]){
                sistema.guardarDatosSedesYTurnos();
                JOptionPane.showMessageDialog(frame, "Turno reprogramado con éxito, para la hora " +
                        "" + turno.getDia() + " y hora " + turno.getHorarioCompleto(), "Error", JOptionPane.ERROR_MESSAGE);
                frame.dispose();
            }
        });


        // Agregar el panel del paciente al panel principal de la ventana
        mainPanel.add(panelTurno);
        mainPanel.add(panelNuevaFechaHora);

        frame.add(mainPanel);
        frame.setVisible(true);

    }

    //este metodo carga un nuevo profesional:
    public Profesional menuNuevoProfesional(Profesional profesional) {
        // Crear el marco de la ventana
        JFrame frame = new JFrame("Agregar Profesional");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 400);  // Aumentamos el tamaño para los campos de texto

        // Crear un panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.CYAN); // Color de fondo del panel principal

        // Crear un JLabel para el título
        JLabel titleLabel = new JLabel("Nuevo Profesional", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Cambiar la fuente y tamaño
        titleLabel.setForeground(Color.BLACK); // Color del texto del título
        mainPanel.add(titleLabel, BorderLayout.NORTH); // Agregar el título en la parte superior del panel

        // Crear un panel para los campos de entrada y los botones
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(11, 2, 10, 10)); // Organiza los campos y botones en una cuadrícula

        NumberFormat integerFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(integerFormat);
        numberFormatter.setValueClass(Integer.class); // Establece la clase de valor como Integer
        numberFormatter.setAllowsInvalid(false); // No permite valores inválidos
        numberFormatter.setMinimum(0); // Límite inferior (opcional)
        numberFormatter.setMaximum(Integer.MAX_VALUE); // Límite superior (opcional)
        NumberFormat hourFormat = NumberFormat.getIntegerInstance();
        NumberFormatter hourFormatter = new NumberFormatter(hourFormat);
        hourFormatter.setValueClass(Integer.class); // Establece la clase de valor como Integer
        hourFormatter.setAllowsInvalid(false); // No permite valores inválidos
        hourFormatter.setMinimum(0); // Límite inferior (opcional)
        hourFormatter.setMaximum(24); // Límite superior (opcional)

        // Campos de texto para ingresar datos del profesional
        JTextField nombreField = new JTextField();
        JTextField apellidoField = new JTextField();
        JTextField edadField = new JTextField();
        JTextField direccionField = new JTextField();
        JFormattedTextField telefonoField = new JFormattedTextField(numberFormatter);
        JTextField correoField = new JTextField();
        ComboBoxModel<Especialidad> especialidadModel = new DefaultComboBoxModel<>(Especialidad.values());
        JComboBox<Especialidad> especialidadField = new JComboBox<>(especialidadModel);
        // Campos para los horarios de inicio y fin
        JFormattedTextField inicioHorarioField = new JFormattedTextField(hourFormatter);
        JFormattedTextField finHorarioField = new JFormattedTextField(hourFormatter);


        // Crear los botones para ingresar información
        JButton nombreProfesional = new JButton("Nombre");
        JButton apellidoProfesional = new JButton("Apellido");
        JButton edadProfesional = new JButton("Edad");
        JButton direccionProfesional = new JButton("Direccion");
        JButton telefonoProfesional = new JButton("Teléfono");
        JButton correoProfesional = new JButton("Correo");
        JButton especialidadProfesional = new JButton("Especialidad");
        JButton horarioInicioLabel = new JButton("Horario Inicio");
        JButton horarioFinLabel = new JButton("Horario Fin");
        JButton guardarProfesional = new JButton("Guardar Profesional");
        JButton volverAlInicio = new JButton("Volver al inicio");

        // Agregar los botones y campos de texto al panel de entrada
        inputPanel.add(nombreProfesional);
        inputPanel.add(nombreField);
        inputPanel.add(apellidoProfesional);
        inputPanel.add(apellidoField);
        inputPanel.add(edadProfesional);
        inputPanel.add(edadField);
        inputPanel.add(direccionProfesional);
        inputPanel.add(direccionField);
        inputPanel.add(telefonoProfesional);
        inputPanel.add(telefonoField);
        inputPanel.add(correoProfesional);
        inputPanel.add(correoField);
        inputPanel.add(especialidadProfesional);
        inputPanel.add(especialidadField);
        inputPanel.add(horarioInicioLabel);
        inputPanel.add(inicioHorarioField);
        inputPanel.add(horarioFinLabel);
        inputPanel.add(finHorarioField);
        inputPanel.add(guardarProfesional);
        inputPanel.add(volverAlInicio);

        // Agregar el panel de entrada al panel principal
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Evento para el botón "Guardar Profesional"
        guardarProfesional.addActionListener(e -> {
            // Validación de los campos antes de asignar los valores
            if (nombreField.getText().isEmpty() || apellidoField.getText().isEmpty() || telefonoField.getText().isEmpty() ||
                    correoField.getText().isEmpty()  ||
                    inicioHorarioField.getText().isEmpty() || finHorarioField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    profesional.setNombre(nombreField.getText());
                    profesional.setApellido(apellidoField.getText());
                    profesional.setEdad(Integer.parseInt(edadField.getText()));
                    profesional.setEspecialidad((Especialidad) especialidadField.getSelectedItem());
                    profesional.setDireccion(direccionField.getText());
                    profesional.setNroTelefono(telefonoField.getText());
                    profesional.setCorreo(correoField.getText());
                    Set<FranjaHoraria> horarios = new HashSet<>();
                    horarios.add(new FranjaHoraria(Integer.parseInt(inicioHorarioField.getText()),0,
                            Integer.parseInt(finHorarioField.getText()),0));
                    profesional.setHorarioDeTrabajo(horarios);
                    profesional.setContrasenha(profesional.getNombre() + "." + profesional.getApellido());

                    profesional.setNombreUsuario(profesional.getNombre() + "." + profesional.getApellido());
                    JOptionPane.showMessageDialog(frame, "Usuario " + profesional.getNombreUsuario() + " guardado correctamente. " +
                            "Su contrasenha es " + profesional.getContrasenha());

                    this.sistema.agendarUsuario(profesional);
                } catch (NombreInvalidoException ex) {
                    JOptionPane.showMessageDialog(frame, "Este usuario ya existe, ingrese otro nuevo usuario");
                }
            }
            // Confirmación de que se guardó el profesional
        });

        // Evento para el botón "Volver al inicio"
        volverAlInicio.addActionListener(e -> {
            // Cerrar la ventana actual
            frame.dispose();
        });

        // Agregar el panel principal al marco
        frame.add(mainPanel);
        frame.setVisible(true);


        return profesional;  // Retorna el objeto profesional con los datos ingresados
    }

    private <T extends Usuario> void menuVerUsuarios(ArrayList<T> usuarios, String titulo) {
        //armar la ventana y recorrer la lista de pacientes y mostrarlos
        //armar un panel para que me muestre los datos de c/paciente por separado

        // Crear la ventana principal
        JFrame ventana = new JFrame(titulo);
        ventana.setLayout(new BoxLayout(ventana.getContentPane(), BoxLayout.Y_AXIS));

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        usuarios.sort(Comparator.comparing(Usuario::getApellido).thenComparing(Usuario::getNombre));

        // Recorremos la lista de pacientes y creamos un panel para cada uno
        for (T usuario : usuarios) {
            // Crear un panel para cada usuario
            JPanel panelPaciente = new JPanel();
            panelPaciente.setLayout(new BoxLayout(panelPaciente, BoxLayout.Y_AXIS));

            // Crear los componentes para mostrar la información del usuario
            JLabel nombreLabel = new JLabel("Nombre: " + usuario.getNombre());
            JLabel apellidoLabel = new JLabel("Apellido: " + usuario.getApellido());
            JLabel edadLabel = new JLabel("Edad: " + usuario.getEdad());
            JLabel correoLabel = new JLabel("Correo: " + usuario.getCorreo());
            JLabel telefonoLabel = new JLabel("Teléfono: " + usuario.getNroTelefono());
            JLabel direccionLabel = new JLabel("Direccion: " + usuario.getDireccion());

            // Agregar los JLabel al panel del usuario
            panelPaciente.add(nombreLabel);
            panelPaciente.add(apellidoLabel);
            panelPaciente.add(edadLabel);
            panelPaciente.add(correoLabel);
            panelPaciente.add(telefonoLabel);
            panelPaciente.add(direccionLabel);

            if(usuario instanceof Administrativo){
                JLabel sedeLabel = new JLabel("Sede laboral: " + ((Administrativo)usuario).getSede());
                panelPaciente.add(sedeLabel);
            } else if (usuario instanceof Profesional) {
                JLabel especialidadLabel = new JLabel("Especialidad: " + ((Profesional)usuario).getEspecialidad().toString().toLowerCase());
                panelPaciente.add(especialidadLabel);

                StringBuilder textoEtiqueta = new StringBuilder("Horarios:");
                for (FranjaHoraria franjaHoraria: ((Profesional) usuario).getHorarioDeTrabajo()){
                    textoEtiqueta.append(" " + franjaHoraria);
                }
                JLabel horariosLabel = new JLabel(textoEtiqueta.toString());
                panelPaciente.add(horariosLabel);
            }

            // Añadir un borde al panel del usuario para separarlo de los demás
            panelPaciente.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));  // Borde inferior

            // Agregar el panel del usuario al panel principal de la ventana
            panelPrincipal.add(panelPaciente);
        }

        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Agregar el JScrollPane a la ventana
        ventana.add(scrollPane);

        // Configurar la ventana
        ventana.setSize(400, 600);  // Ajusta el tamaño de la ventana
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setVisible(true);
    }


    public Turno menuCita(Turno turnoNuevo) {
        // DATOS A RECOPILAR
        final Especialidad[] especialidadElegida = {null};
        final Profesional[] profesionalElegido = {null};
        final Sede[] sedeElegida = {null};
        final Date[] fechaElegida = {null}; // Para almacenar la fecha seleccionada

        JFrame frame = new JFrame("Turnos");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 250);

        // Crear un panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.CYAN); // Color de fondo del panel principal

        frame.setLocationRelativeTo(null); // Centrar ventana

        // Crear un JLabel para el título
        JLabel titleLabel = new JLabel("Turnos", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Cambiar la fuente y tamaño
        titleLabel.setForeground(Color.BLACK); // Color del texto del título
        mainPanel.add(titleLabel, BorderLayout.NORTH); // Agregar el título en la parte superior del panel

        // Crear un panel para el menú de opciones
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.CYAN); // Color de fondo del panel del menú


        JLabel lblPaciente;
        JComboBox<Consultante> elegirPaciente = new JComboBox<>();
        if (this.sistema.getUsuarioConectado() instanceof Consultante) {
            lblPaciente = new JLabel("Creatine de turno para usuario conectado.");
            turnoNuevo.setConsultante((Consultante) this.sistema.getUsuarioConectado());
        } else {
            lblPaciente = new JLabel("Seleccione un paciente: ");
            ComboBoxModel<Consultante> pacientesModel = new DefaultComboBoxModel<>(new ArrayList<>(this.sistema.getPacientes()).toArray(new Consultante[0]));
            elegirPaciente.setModel(pacientesModel);

            elegirPaciente.addActionListener(e -> {
                turnoNuevo.setConsultante((Consultante) elegirPaciente.getSelectedItem());
            });
        }

        // Etiquetas informativas
        JLabel lblSeleccionarEspecialidad = new JLabel("Selecciona una especialidad:");
        JLabel lblSeleccionarProfesional = new JLabel("Selecciona un profesional:");
        JLabel lblSeleccionarSede = new JLabel("Selecciona una sede:");
        JLabel lblSeleccionarDia = new JLabel("Selecciona el día:");
        JLabel lblSeleccionarHorario = new JLabel("Selecciona el horario:");

        // Crear los elementos del menú de opciones
        ComboBoxModel<Especialidad> especialidadesModel = new DefaultComboBoxModel<>(new ArrayList<>(this.sistema.getEspecialidadesDisponibles()).toArray(new Especialidad[0]));
        ComboBoxModel<Sede> sedesModel = new DefaultComboBoxModel<>(new ArrayList<>(this.sistema.getSedesDisponibles()).toArray(new Sede[0]));
        JComboBox<Especialidad> elegirEspecialidad = new JComboBox<>(especialidadesModel);
        JComboBox<Profesional> elegirProfesional = new JComboBox<>();
        JComboBox<Sede> elegirSede = new JComboBox<>(sedesModel);
        ComboBoxModel<LocalDate> fechasDisponiblesModel = new DefaultComboBoxModel<>();
        ComboBoxModel<LocalTime> horariosDisponiblesModel = new DefaultComboBoxModel<>();

        JComboBox<LocalDate> elegirDia = new JComboBox(fechasDisponiblesModel);
        JComboBox<LocalTime> elegirHorario = new JComboBox<>(horariosDisponiblesModel);

        JButton confirmar = new JButton("Confirmar");
        JButton volverAlInicio = new JButton("Volver al inicio");

        // Inicialmente deshabilitar los botones de las opciones posteriores
        elegirProfesional.setEnabled(false);
        elegirDia.setEnabled(false);
        elegirHorario.setEnabled(false);
        confirmar.setEnabled(false);
        elegirSede.setEnabled(false);

        // Agregar botones al panel del menú
        menuPanel.add(lblPaciente);
        if (!(this.sistema.getUsuarioConectado() instanceof Consultante)) {
            menuPanel.add(elegirPaciente);
        }
        menuPanel.add(lblSeleccionarEspecialidad);
        menuPanel.add(elegirEspecialidad);
        menuPanel.add(lblSeleccionarSede);
        menuPanel.add(elegirSede);
        menuPanel.add(lblSeleccionarProfesional);
        menuPanel.add(elegirProfesional);
        menuPanel.add(lblSeleccionarDia);
        menuPanel.add(elegirDia);
        menuPanel.add(lblSeleccionarHorario);
        menuPanel.add(elegirHorario);
        menuPanel.add(confirmar);
        menuPanel.add(volverAlInicio);

        // Agregar el panel del menú al panel principal
        mainPanel.add(menuPanel, BorderLayout.CENTER); // Agregar el panel del menú en el centro del panel principal

        // Eventos para los botones
        elegirEspecialidad.addActionListener(e -> {
            // Habilitar el botón "Elegir Profesional" después de seleccionar la especialidad
            elegirProfesional.removeAllItems();
            especialidadElegida[0] = (Especialidad) elegirEspecialidad.getSelectedItem();
            ComboBoxModel<Profesional> profesionalesModel = new DefaultComboBoxModel<>(new ArrayList<>(this.sistema.getProfesionales()
                    .filtrarPorEspecialidad(especialidadElegida[0])).toArray(new Profesional[0]));
            elegirProfesional.setModel(profesionalesModel);
            elegirSede.setEnabled(true);
        });

        elegirSede.addActionListener(e -> {
            sedeElegida[0] = (Sede) elegirSede.getSelectedItem();
            elegirProfesional.setEnabled(true);
        });

        elegirProfesional.addActionListener(e -> {
            try {
                Profesional profesional = this.sistema.getProfesionales().get(((Profesional) elegirProfesional.getSelectedItem()).getNombreUsuario());
                turnoNuevo.setProfesional(profesional);
            } catch (UsuarioInexistenteException ex) {
                throw new RuntimeException(ex);
            }
            ArrayList<LocalDate> fechasHabilitadas = this.sistema.getFechasHabilitadas(turnoNuevo.getProfesional());
            ComboBoxModel<LocalDate> fechasHabilitadasModel = new DefaultComboBoxModel<>(fechasHabilitadas.toArray(new LocalDate[0]));
            elegirDia.setModel(fechasHabilitadasModel);
            elegirDia.setEnabled(true);
        });

        elegirDia.addActionListener(e -> {
            turnoNuevo.setDia((LocalDate) elegirDia.getSelectedItem());
            elegirHorario.removeAllItems();
            ArrayList<LocalTime> horariosHabilitados = this.sistema.getHorariosDisponibles(turnoNuevo.getProfesional(), sedeElegida[0], turnoNuevo.getDia());
            ComboBoxModel<LocalTime> horarioModelo = new DefaultComboBoxModel<>(
                    horariosHabilitados.toArray(new LocalTime[0])
            );
            elegirHorario.setModel(horarioModelo);
            elegirHorario.setEnabled(true);
        });

        elegirHorario.addActionListener(e -> {
            try {
                Duration duracionTurno = Duration.of(turnoNuevo.getProfesional().getDuracionTurnoMinutos(), ChronoUnit.MINUTES);
                FranjaHoraria franja = new FranjaHoraria((LocalTime) elegirHorario.getSelectedItem(), duracionTurno);
                turnoNuevo.setHorario(franja);
                turnoNuevo.setConsultorio(sedeElegida[0].buscarConsultorioDisponible(this.sistema.getTurnos(), turnoNuevo.getDia(), turnoNuevo.getFranjaHoraria()));
                System.out.println("Consultorio asignado: " + turnoNuevo.getConsultorio());
                confirmar.setEnabled(true);
            } catch (LugarNoDisponibleException ex) {
                JOptionPane.showMessageDialog(frame, "No hay consultorios disponibles para la fecha y hora seleccionadas. Elija otra hora.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        confirmar.addActionListener(e -> {
            try {
                System.out.println(turnoNuevo);
                // Aquí setearíamos los valores en turnoNuevo
                if (turnoNuevo.getDia() != null & turnoNuevo.getFranjaHoraria() != null &
                        turnoNuevo.getProfesional() != null & turnoNuevo.getConsultorio() != null
                        & turnoNuevo.getConsultante() != null) {
                    this.sistema.agendarTurno(turnoNuevo);
                    JOptionPane.showMessageDialog(frame, "Turno agendado con éxito!");
                    frame.dispose();  // Cerrar la ventana
                } else {
                    JOptionPane.showMessageDialog(frame, "Por favor, complete todos los campos.");
                }
            } catch (HorarioNoDisponibleException ex) {
                JOptionPane.showMessageDialog(frame, "El turno no puede ser cargado: " + ex.getMessage());
            } catch (OperacionNoPermitidaException ex) {
                JOptionPane.showMessageDialog(frame, "Operación no permitida para este perfil de usuario: " + ex.getMessage());
            }
        });


        volverAlInicio.addActionListener(e -> {
            //System.exit(0);
            frame.setVisible(false); // Solo oculta la ventana
        });

        // Agregar el panel principal al marco
        frame.add(mainPanel);
        frame.setVisible(true);
        return turnoNuevo;
    }

    public void menuVerTurnos(ArrayList<Turno> turnos) {
        System.out.println(turnos);
        turnos.sort(Comparator.comparing(Turno::getDia)
                .thenComparing(Turno::getHoraInicio));

        JFrame ventana = new JFrame("Lista de Turnos");
        ventana.setLayout(new BoxLayout(ventana.getContentPane(), BoxLayout.Y_AXIS));

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        for (Turno turno : turnos) {
            JPanel panelTurno = new JPanel();
            panelTurno.setLayout(new BoxLayout(panelTurno, BoxLayout.Y_AXIS));

            // Crear los componentes para mostrar la información del turno
            JLabel pacienteLabel = new JLabel("Nombre del paciente: " + turno.getConsultante());
            JLabel diaLabel = new JLabel("Dia: " + turno.getDia());
            JLabel horaLabel = new JLabel("Hora: " + turno.getHoraInicio());
            JLabel especialidadLabel = new JLabel("Especialidad: " + Especialidad.CARDIOLOGIA);
            JLabel profesionalLabel = new JLabel("Nombre Profesional: " + turno.getProfesional());
            JLabel consultorioLabel = new JLabel("Consultorio: " + turno.getConsultorio());
            JLabel estadoLabel = new JLabel("Estado: " + turno.getEstado());

            // Agregar los JLabel al panel del turno
            panelTurno.add(pacienteLabel);
            panelTurno.add(diaLabel);
            panelTurno.add(horaLabel);
            panelTurno.add(especialidadLabel);
            panelTurno.add(profesionalLabel);
            panelTurno.add(consultorioLabel);
            panelTurno.add(estadoLabel);

            // Añadir un borde al panel del turno para separarlo de los demás
            panelTurno.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));

            // Agregar el panel del paciente al panel principal de la ventana
            panelPrincipal.add(panelTurno);
        }

        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Agregar el JScrollPane al frame
        ventana.add(scrollPane);

        // Configurar la ventana
        ventana.setSize(400, 600);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setVisible(true);
    }


    public <T extends Usuario> T menuAgregarUsuario(T usuario) {

        // Método para mostrar la ventana y recoger los datos del paciente

        // Crear el marco de la ventana
        JFrame frame = new JFrame("Agregar Usuario");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 500);  // Aumentamos el tamaño para los campos de texto

        // Crear un panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.CYAN);

        // Crear un JLabel para el título
        JLabel titleLabel = new JLabel("Nuevo " + usuario.getClass().getSimpleName(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);
        mainPanel.add(titleLabel, BorderLayout.NORTH); // Agregar el título en la parte superior del panel

        // Crear un panel para los campos de entrada y los botones
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 2, 10, 10)); // Organiza los campos y botones en una cuadrícula

        NumberFormat integerFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(integerFormat);
        numberFormatter.setValueClass(Integer.class); // Establece la clase de valor como Integer
        numberFormatter.setAllowsInvalid(false); // No permite valores inválidos
        numberFormatter.setMinimum(0); // Límite inferior (opcional)
        numberFormatter.setMaximum(Integer.MAX_VALUE); // Límite superior (opcional)

        // Crear los campos de texto para ingresar datos del paciente
        JTextField nombreField = new JTextField();
        JTextField apellidoField = new JTextField();
        JFormattedTextField edadField = new JFormattedTextField(numberFormatter);
        JTextField direccionField = new JTextField();
        JFormattedTextField telefonoField = new JFormattedTextField(numberFormatter);
        JTextField correoField = new JTextField();

        // Crear los botones para ingresar información
        JLabel nombreUsuario = new JLabel("Nombre");
        JLabel apellidoUsuario = new JLabel("Apellido");
        JLabel edadUsuario = new JLabel("Edad");
        JLabel direccionUsuario = new JLabel("Direccion");
        JLabel telefonoUsuario = new JLabel("Telefono");
        JLabel correoUsuario = new JLabel("Correo");
        JButton guardarUsuario = new JButton("Guardar Usuario");
        JButton volverAlInicio = new JButton("Volver al inicio");

        // Agregar los botones y campos de texto al panel de entrada
        inputPanel.add(nombreUsuario);
        inputPanel.add(nombreField);
        inputPanel.add(apellidoUsuario);
        inputPanel.add(apellidoField);
        inputPanel.add(edadUsuario);
        inputPanel.add(edadField);
        inputPanel.add(direccionUsuario);
        inputPanel.add(direccionField);
        inputPanel.add(telefonoUsuario);
        inputPanel.add(telefonoField);
        inputPanel.add(correoUsuario);
        inputPanel.add(correoField);

        final JComboBox<Sede> sedeJComboBox = new JComboBox<>();
        if (usuario instanceof Administrativo){
            ComboBoxModel<Sede> sedeComboBoxModel = new DefaultComboBoxModel<>(sistema.getSedes().toArray(new Sede[0]));
            JLabel sedeJLabel = new JLabel("Sede");
            sedeJComboBox.setModel(sedeComboBoxModel);
            inputPanel.add(sedeJLabel);
            inputPanel.add(sedeJComboBox);
        }

        inputPanel.add(guardarUsuario);
        inputPanel.add(volverAlInicio);

        // Agregar el panel de entrada al panel principal
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Evento para el botón "Guardar Paciente"
        guardarUsuario.addActionListener(e -> {
            usuario.setNombre(nombreField.getText());
            usuario.setApellido(apellidoField.getText());
            usuario.setEdad(Integer.parseInt(edadField.getText()));
            usuario.setDireccion(direccionField.getText());
            usuario.setNroTelefono(telefonoField.getText());
            usuario.setCorreo(correoField.getText());
            usuario.setContrasenha((usuario.getNombre() + "." + usuario.getApellido()).replace(' ', '_'));
            if (usuario instanceof Administrativo){
                ((Administrativo) usuario).setSede((Sede) sedeJComboBox.getSelectedItem());
            }

            try {
                usuario.setNombreUsuario(usuario.getNombre() + "." + usuario.getApellido());
                JOptionPane.showMessageDialog(frame, "Usuario " + usuario.getNombreUsuario() + " guardado correctamente. " +
                        "Su contrasenha es " + usuario.getContrasenha());

                this.sistema.agendarUsuario(usuario);
            } catch (NombreInvalidoException ex) {
                JOptionPane.showMessageDialog(frame, "Este usuario ya existe, ingrese otro nuevo usuario");
            }
        });

        volverAlInicio.addActionListener(e -> {
            // Cierra la ventana actual
            frame.dispose();
        });

        // Agregar el panel principal al marco
        frame.add(mainPanel);
        frame.setVisible(true);
        return usuario;  // Retorna el objeto consultante con los datos ingresados
    }

}