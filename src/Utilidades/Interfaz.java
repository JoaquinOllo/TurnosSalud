package Utilidades;
import Citas.Turno;
import Enumeradores.Especialidad;
import Excepciones.UsuarioInvalidoException;
import Usuarios.Consultante;
import Usuarios.ListaUsuarios;
import Usuarios.Profesional;

import javax.swing.*;
import java.awt.*;
import java.sql.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.text.NumberFormatter;

public class Interfaz  {
    private GestionSistema sistema;

    public Interfaz(GestionSistema sistema) {
        this.sistema=sistema;
    }


    public void menuConexion(){
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
            if (this.sistema.conectarse(campoUsuario.getText(), campoContrasena)){
                frame.dispose(); // Destruye la ventana (la cierra completamente)
                menuInicial();
            } else {
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
                panel.add(Box.createVerticalStrut(10));  // Espacio entre el mensaje y el botón
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
        mainPanel.setBackground(Color.CYAN); // Color de fondo del panel principal
        frame.setLocationRelativeTo(null); // Centrar ventana

        // Crear un JLabel para el título
        JLabel titleLabel = new JLabel("Bienvenido a SuperDoctors", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Cambiar la fuente y tamaño
        titleLabel.setForeground(Color.BLACK); // Color del texto del título
        mainPanel.add(titleLabel, BorderLayout.NORTH); // Agregar el título en la parte superior del panel

        // Crear un panel para el menú de opciones
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.CYAN); // Color de fondo del panel del menú

        // Crear los elementos del menú de opciones
        JButton agregarPaciente = new JButton("Agregar Paciente");
        JButton verPacientes = new JButton("Ver Pacientes");
        JButton agregarCita = new JButton("Agregar Cita");
        JButton verCitas = new JButton("Ver Citas");
        JButton salir = new JButton("Salir");

        // Agregar botones al panel del menú
        menuPanel.add(agregarPaciente);
        menuPanel.add(verPacientes);
        menuPanel.add(agregarCita);
        menuPanel.add(verCitas);
        menuPanel.add(salir);

        // Agregar el panel del menú al panel principal
        mainPanel.add(menuPanel, BorderLayout.CENTER); // Agregar el panel del menú en el centro del panel principal

        // Eventos para los botones
        agregarPaciente.addActionListener(e -> {

            Consultante consultante=new Consultante();
            menuAgregarPaciente(consultante);
        });

        verPacientes.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Ver Pacientes...");
        });

        agregarCita.addActionListener(e -> {
            Turno t1=new Turno();
            menuCita(t1);

        });

        verCitas.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Ver Citas...");
        });

        salir.addActionListener(e -> {
            System.exit(0);
        });

        // Agregar el panel principal al marco
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public void menuCita(Turno turnoNuevo){
        // DATOS A RECOPILAR
        final Especialidad[] especialidadElegida = {null};

        JFrame frame = new JFrame("Turnos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        // Crear los elementos del menú de opciones
        ComboBoxModel<Especialidad> especialidadesModel = new DefaultComboBoxModel<>(new ArrayList<>(this.sistema.getEspecialidadesDisponibles()).toArray(new Especialidad[0]));
        JComboBox<Especialidad> elegirEspecialidad = new JComboBox<>(especialidadesModel);

        JComboBox<Profesional> elegirProfesional = new JComboBox<>();

        JButton elegirDiaYHorario = new JButton("4- Elegir dia y horario");
        JButton confirmar = new JButton("Confirmar");
        JButton volverAlInicio = new JButton("Volver al inicio");

        // Inicialmente deshabilitar los botones de las opciones posteriores
        elegirProfesional.setEnabled(false);
        elegirDiaYHorario.setEnabled(false);
        confirmar.setEnabled(false);

        // Agregar botones al panel del menú
        menuPanel.add(elegirEspecialidad);
        menuPanel.add(elegirProfesional);
        menuPanel.add(elegirDiaYHorario);
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
            elegirProfesional.setEnabled(true);

        });

        elegirProfesional.addActionListener(e -> {
            elegirDiaYHorario.setEnabled(true);

        });

        elegirDiaYHorario.addActionListener(e -> {
            menuElegirDia(frame, turnoNuevo);
            menuElegirHorario(frame, turnoNuevo);

        });

        volverAlInicio.addActionListener(e -> {
            //System.exit(0);
            frame.setVisible(false); // Solo oculta la ventana
        });

        // Agregar el panel principal al marco
        frame.add(mainPanel);
        frame.setVisible(true);

    }

    public Consultante menuAgregarPaciente(Consultante consultante){

            // Método para mostrar la ventana y recoger los datos del paciente

                // Crear el marco de la ventana
                JFrame frame = new JFrame("Agregar Paciente");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(300, 350);  // Aumentamos el tamaño para los campos de texto

                // Crear un panel principal
                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new BorderLayout());
                mainPanel.setBackground(Color.CYAN); // Color de fondo del panel principal

                // Crear un JLabel para el título
                JLabel titleLabel = new JLabel("Nuevo Paciente", SwingConstants.CENTER);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Cambiar la fuente y tamaño
                titleLabel.setForeground(Color.BLACK); // Color del texto del título
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

                // Campo de texto formateado
                /*JFormattedTextField integerField = new JFormattedTextField(numberFormatter);
                integerField.setColumns(10);*/
                // Crear los campos de texto para ingresar datos del paciente
                JTextField nombreField = new JTextField();
                JTextField apellidoField = new JTextField();
                JFormattedTextField edadField = new JFormattedTextField(numberFormatter);
                JTextField direccionField = new JTextField();
                JFormattedTextField telefonoField = new JFormattedTextField(numberFormatter);
                JTextField correoField = new JTextField();

                // Crear los botones para ingresar información
                JButton nombrePaciente = new JButton("Nombre");
                JButton apellidoPaciente = new JButton("Apellido");
                JButton edadPaciente = new JButton("Edad");
                JButton direccionPaciente = new JButton("Direccion");
                JButton telefonoPaciente = new JButton("Telefono");
                JButton correoPaciente = new JButton("Correo");
                JButton guardarPaciente = new JButton("Guardar Paciente");  // Nuevo botón para guardar el paciente
                JButton volverAlInicio = new JButton("Volver al inicio");

                // Agregar los botones y campos de texto al panel de entrada
                inputPanel.add(nombrePaciente);
                inputPanel.add(nombreField);
                inputPanel.add(apellidoPaciente);
                inputPanel.add(apellidoField);
                inputPanel.add(edadPaciente);
                inputPanel.add(edadField);
                inputPanel.add(direccionPaciente);
                inputPanel.add(direccionField);
                inputPanel.add(telefonoPaciente);
                inputPanel.add(telefonoField);
                inputPanel.add(correoPaciente);
                inputPanel.add(correoField);
                inputPanel.add(guardarPaciente);  // Agregar el botón para guardar el paciente
                inputPanel.add(volverAlInicio);

                // Agregar el panel de entrada al panel principal
                mainPanel.add(inputPanel, BorderLayout.CENTER);


                // Evento para el botón "Guardar Paciente"
                guardarPaciente.addActionListener(e -> {
                    consultante.setNombre(nombreField.getText());
                    consultante.setApellido(apellidoField.getText());
                    consultante.setEdad(Integer.parseInt(edadField.getText()));
                    consultante.setDireccion(direccionField.getText());
                    consultante.setNroTelefono(telefonoField.getText());
                    consultante.setCorreo(correoField.getText());
                    consultante.setContrasenha(consultante.getNombre()+"."+consultante.getApellido());
                    try {
                        consultante.setNombreUsuario(consultante.getNombre()+"."+consultante.getApellido());
                        JOptionPane.showMessageDialog(frame, "Usuario "+consultante.getNombreUsuario()+" guardado correctamente. " +
                                "Su contrasenha es "+consultante.getContrasenha());

                        this.sistema.getUsuarios().add(consultante);
                        System.out.println(this.sistema.getUsuarios().toString());
                    } catch (UsuarioInvalidoException ex) {
                        JOptionPane.showMessageDialog(frame, "Este usuario ya existe, ingrese otro nuevo usuario");
                    }
                    // Confirmación de que se guardó el paciente
                });

                volverAlInicio.addActionListener(e -> {
                    // Cierra la ventana actual
                    frame.dispose();
                });

                // Agregar el panel principal al marco
                frame.add(mainPanel);
                frame.setVisible(true);
                return consultante;  // Retorna el objeto consultante con los datos ingresados
            }




        /*JFrame frame = new JFrame("Agregar Paciente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);

        // Crear un panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.CYAN); // Color de fondo del panel principal

        // Crear un JLabel para el título
        JLabel titleLabel = new JLabel("Nuevo Paciente", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Cambiar la fuente y tamaño
        titleLabel.setForeground(Color.BLACK); // Color del texto del título
        mainPanel.add(titleLabel, BorderLayout.NORTH); // Agregar el título en la parte superior del panel

        // Crear un panel para los campos de entrada y los botones
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 2, 10, 10)); // Organiza los campos y botones en una cuadrícula

        // Crear los campos de texto para ingresar datos del paciente
        JTextField nombreField = new JTextField();
        JTextField apellidoField = new JTextField();
        JTextField edadField = new JTextField();
        JTextField direccionField = new JTextField();
        JTextField telefonoField = new JTextField();
        JTextField correoField = new JTextField();

        // Crear un panel para el menú de opciones
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.CYAN); // Color de fondo del panel del menú

        // Crear los elementos del menú de opciones
        JButton nombrePaciente = new JButton("Nombre");
        JButton apellidoPaciente = new JButton("Apellido");
        JButton edadPaciente = new JButton("Edad");
        JButton direccionPaciente = new JButton("Direccion");
        JButton telefonoPaciente = new JButton("Telefono");
        JButton correoPaciente = new JButton("Correo");
        JButton volverAlInicio = new JButton("Volver al inicio");



        // Agregar botones al panel del menú
        menuPanel.add(nombrePaciente);
        menuPanel.add(apellidoPaciente);
        menuPanel.add(edadPaciente);
        menuPanel.add(direccionPaciente);
        menuPanel.add(telefonoPaciente);
        menuPanel.add(correoPaciente);
        menuPanel.add(volverAlInicio);

        inputPanel.add(nombrePaciente);
        inputPanel.add(nombreField);
        inputPanel.add(apellidoPaciente);
        inputPanel.add(apellidoField);
        inputPanel.add(edadPaciente);
        inputPanel.add(edadField);
        inputPanel.add(direccionPaciente);
        inputPanel.add(direccionField);
        inputPanel.add(telefonoPaciente);
        inputPanel.add(telefonoField);
        inputPanel.add(correoPaciente);
        inputPanel.add(correoField);
        inputPanel.add(volverAlInicio);

        // Agregar el panel del menú al panel principal
        mainPanel.add(menuPanel, BorderLayout.CENTER); // Agregar el panel del menú en el centro del panel principal
        // Eventos para los botones
        nombrePaciente.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Elegir nombre...");
            consultante.setNombre(nombreField.getText());
            JOptionPane.showMessageDialog(frame, "Nombre ingresado: " + consultante.getNombre());
        });

        apellidoPaciente.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Elegir apellido...");
        });
        edadPaciente.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Elegir edad...");
        });

        direccionPaciente.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Elegir direccion...");
        });
        telefonoPaciente.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Elegir telefono...");
        });
        correoPaciente.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Elegir correo...");
        });

        volverAlInicio.addActionListener(e -> {
            //System.exit(0);
            frame.setVisible(false); // Solo oculta la ventana
        });

        // Agregar el panel principal al marco
        frame.add(mainPanel);
        frame.setVisible(true);
        return consultante;*/

    public void menuElegirProfesional(JFrame frame, Turno turno) {
        JDialog dialog = new JDialog(frame, "Elegir Profesional", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(frame);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Selecciona el profesional...");
        JButton confirmarButton = new JButton("Confirmar");

        confirmarButton.addActionListener(e -> {
            // Procesa la selección de profesional y actualiza el turno
            dialog.dispose();
        });

        panel.add(label);
        panel.add(confirmarButton);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }

    public void menuElegirConsultorio(JFrame frame, Turno turno) {
        JDialog dialog = new JDialog(frame, "Elegir Consultorio", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(frame);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Selecciona el consultorio...");
        JButton confirmarButton = new JButton("Confirmar");

        confirmarButton.addActionListener(e -> {
            // Procesa la selección de consultorio y actualiza el turno
            dialog.dispose();
        });

        panel.add(label);
        panel.add(confirmarButton);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }

    public void menuElegirDia(JFrame frame, Turno turno) {
        JDialog dialog = new JDialog(frame, "Elegir Día", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(frame);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Selecciona el día...");
        JButton confirmarButton = new JButton("Confirmar");

        confirmarButton.addActionListener(e -> {
            // Procesa la selección de día y actualiza el turno
            dialog.dispose();
        });

        panel.add(label);
        panel.add(confirmarButton);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }

    public void menuElegirHorario(JFrame frame, Turno turno) {
        JDialog dialog = new JDialog(frame, "Elegir Horario", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(frame);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Selecciona el horario...");
        JButton confirmarButton = new JButton("Confirmar");

        confirmarButton.addActionListener(e -> {
            // Procesa la selección de horario y actualiza el turno
            dialog.dispose();
        });

        panel.add(label);
        panel.add(confirmarButton);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }

}
