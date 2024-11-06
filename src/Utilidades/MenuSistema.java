package Utilidades;
import Citas.Turno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuSistema {
    private GestionSistema sistema;

    public MenuSistema(GestionSistema sistema) {
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
            this.sistema.conectarse(campoUsuario.getText(), campoContrasena);
        });

        panelPrincipal.add(botonIniciarSesion);

        frame.add(panelPrincipal);
        frame.setVisible(true);

        }

    public void menuInicial() {
        JFrame frame = new JFrame("Bienvenido a SuperDoctors");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);

        // Crear un panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.CYAN); // Color de fondo del panel principal

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
            JOptionPane.showMessageDialog(frame, "Agregar Paciente...");
        });

        verPacientes.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Ver Pacientes...");
        });

        agregarCita.addActionListener(e -> {
            Turno t1=new Turno(this.sistema.getUsuarioConectado());

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
}
