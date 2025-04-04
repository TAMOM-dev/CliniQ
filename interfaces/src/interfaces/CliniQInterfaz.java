package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class CliniQInterfaz extends JFrame {

	private static final long serialVersionUID = 1L;
	public CliniQInterfaz() {
        setTitle("CliniQ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizar la ventana
        setUndecorated(true); // Ocultar la barra de título

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel izquierdo para el menú
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(240, 240, 240)); // Color gris claro
        menuPanel.setPreferredSize(new Dimension(150, getHeight())); // Ancho fijo

        JButton inicioButton = createMenuButton("Inicio");
        JButton pacienteButton = createMenuButton("Paciente");
        JButton citasButton = createMenuButton("Citas");
        JButton medicosButton = createMenuButton("Medicos");
        JButton estudiosButton = createMenuButton("Estudios");
        JButton facturacionButton = createMenuButton("Facturacion");

        menuPanel.add(Box.createVerticalGlue()); // Espacio superior
        menuPanel.add(inicioButton);
        menuPanel.add(pacienteButton);
        menuPanel.add(citasButton);
        menuPanel.add(medicosButton);
        menuPanel.add(estudiosButton);
        menuPanel.add(facturacionButton);
        menuPanel.add(Box.createVerticalGlue()); // Espacio inferior

        mainPanel.add(menuPanel, BorderLayout.WEST);

        // Panel central para el contenido principal
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(255, 240, 245)); // Color rosa claro (similar al fondo)

        // Encabezado "Inicio" en el centro
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(220, 220, 220)); // Gris más oscuro para el encabezado
        JLabel inicioLabel = new JLabel("Inicio");
        inicioLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(inicioLabel);
        contentPanel.add(headerPanel, BorderLayout.NORTH);

        // Contenido principal centrado
        JPanel centerContentPanel = new JPanel();
        centerContentPanel.setLayout(new BoxLayout(centerContentPanel, BoxLayout.Y_AXIS));
        centerContentPanel.setBackground(new Color(255, 240, 245));
        centerContentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Margenes

        JLabel cliniqLabel = new JLabel("CliniQ");
        cliniqLabel.setFont(new Font("Arial", Font.BOLD, 24));
        cliniqLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sistemaGestionLabel = new JLabel("Sistema Gestion de Citas");
        sistemaGestionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        sistemaGestionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerContentPanel.add(Box.createVerticalGlue());
        centerContentPanel.add(cliniqLabel);
        centerContentPanel.add(sistemaGestionLabel);
        centerContentPanel.add(Box.createVerticalGlue());

        contentPanel.add(centerContentPanel, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Panel derecho para el formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 5, 10)); // Filas dinámicas, 2 columnas, espacios
        formPanel.setBackground(new Color(255, 228, 225)); // Otro tono de rosa
        formPanel.setPreferredSize(new Dimension(300, getHeight()));
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20)); // Margenes

        JLabel idLabel = new JLabel("ID");
        JTextField idField = new JTextField();
        JLabel nombreLabel = new JLabel("Nombre");
        JTextField nombreField = new JTextField();
        JLabel apellidoLabel = new JLabel("Apellido");
        JTextField apellidoField = new JTextField();
        JLabel direccionLabel = new JLabel("Direccion");
        JTextField direccionField = new JTextField();
        JLabel fechaNacimientoLabel = new JLabel("Fecha de nacimiento");
        JTextField fechaNacimientoField = new JTextField();

        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(nombreLabel);
        formPanel.add(nombreField);
        formPanel.add(apellidoLabel);
        formPanel.add(apellidoField);
        formPanel.add(direccionLabel);
        formPanel.add(direccionField);
        formPanel.add(fechaNacimientoLabel);
        formPanel.add(fechaNacimientoField);

        // Espacio en blanco simulando los campos faltantes
        for (int i = 0; i < 6; i++) { // Ajustar según la imagen
            formPanel.add(new JLabel(""));
            formPanel.add(new JTextField());
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(255, 228, 225));
        JButton guardarButton = new JButton("Guardar");
        JButton limpiarButton = new JButton("Limpiar");
        buttonPanel.add(guardarButton);
        buttonPanel.add(limpiarButton);
        formPanel.add(buttonPanel);

        mainPanel.add(formPanel, BorderLayout.EAST);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(new Color(220, 220, 220)); // Gris claro para los botones del menú
        button.setForeground(Color.BLACK);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(140, 40));
        button.setMaximumSize(new Dimension(140, 40));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CliniQInterfaz());
    }

}
