package dev.cliniq.cliniq.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.springframework.stereotype.Component;

import dev.cliniq.cliniq.Model.TipoUsuario;
import dev.cliniq.cliniq.Model.Usuario;
import dev.cliniq.cliniq.Service.ApplicationContextProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * Clase que representa la interfaz gráfica de la aplicación CliniQ.
 * 
 * @author CliniQ
 */
@Component
@Lazy
public class CliniQInterfaz extends JFrame {

    private JPanel inicioPanel;
    @Autowired
    private PacientePanel pacientePanel;
    @Autowired
    private CitasPanel citasPanel;
    @Autowired
    private MedicosPanel medicosPanel;
    @Autowired
    private FacturacionPanel facturacionPanel;
    @Autowired
    private ConsultoriosPanel consultoriosPanel;
    
    

    private static final long serialVersionUID = 1L;
    
    private JPanel contentPanel;
    private JPanel menuPanel;
    private JLabel headerLabel;
    
    // Ancho fijo del menú
    private int menuWidth = 150;
    
    // Botones del menú
    private JButton logoutButton;
    private JButton activeButton; // Botón actualmente seleccionado
    private JButton inicioButton;
    private JButton pacienteButton;
    private JButton citasButton;
    private JButton medicosButton;
    private JButton consultorioButton;
    private JButton facturacionButton;
    
    // Colores de la aplicación
    private final Color COLOR_PRIMARY = new Color(0, 158, 188); // Cyan
    private final Color COLOR_BACKGROUND = Color.WHITE;
    private final Color COLOR_TEXT = new Color(30, 30, 30);
    
    

    @Autowired
    public CliniQInterfaz(PacientePanel pacientePanel, CitasPanel citasPanel, MedicosPanel medicosPanel, FacturacionPanel facturacionPanel) {
        this.pacientePanel = pacientePanel;
        this.citasPanel = citasPanel;
        this.medicosPanel = medicosPanel;
        this.facturacionPanel = facturacionPanel;
        IniciarForm();
    }

    
    private void IniciarForm() {
        setTitle("CliniQ - Sistema de Gestión de Citas Médicas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800); // Tamaño por defecto
        setLocationRelativeTo(null); // Centrar en pantalla
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizar la ventana
        setUndecorated(true); // Ocultar la barra de título

        // Botón de logout
        logoutButton = createMenuButton("Log Out", "icons/logout.png");
        logoutButton.addActionListener(e -> {
            this.setVisible(false);
            ApplicationContextProvider.getApplicationContext().getBean(CliniQLoginUI.class).setVisible(true);
        });

        // Panel principal que contendrá todo
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(COLOR_BACKGROUND);

        // Panel izquierdo para el menú
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(COLOR_PRIMARY); // Color cyan
        menuPanel.setPreferredSize(new Dimension(menuWidth, getHeight())); // Ancho fijo
        
        // Crear botones del menú lateral con iconos
        inicioButton = createMenuButton("Inicio", "icons/inicio.png");
        pacienteButton = createMenuButton("Pacientes", "icons/paciente.png");
        citasButton = createMenuButton("Citas", "icons/cita.png");
        medicosButton = createMenuButton("Médicos", "icons/medico.png");
        consultorioButton = createMenuButton("Consultorios", "icons/consultorio.png");
        facturacionButton = createMenuButton("Facturación", "icons/factura.png");


        // Configurar los event listeners para la navegación
        inicioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActiveButton(inicioButton);
                mostrarPanel("Inicio");
            }
        });
        
        pacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActiveButton(pacienteButton);
                mostrarPanel("Pacientes");
            }
        });
        
        citasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActiveButton(citasButton);
                mostrarPanel("Citas");
            }
        });
        
        medicosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActiveButton(medicosButton);
                mostrarPanel("Médicos");
            }
        });

        consultorioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActiveButton(consultorioButton);
                mostrarPanel("Consultorios");
            }
        });
        
        facturacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActiveButton(facturacionButton);
                mostrarPanel("Facturación");
            }
        });

        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        menuPanel.add(inicioButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(pacienteButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(citasButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(medicosButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(consultorioButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(facturacionButton);
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio antes del logout
        menuPanel.add(logoutButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio inferior

        // Panel central para el contenido dinámico
        JPanel dynamicContentPanel = new JPanel(new BorderLayout());
        dynamicContentPanel.setBackground(COLOR_BACKGROUND); // Fondo blanco

        // Encabezado en el centro con botón de cierre
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_PRIMARY); // Color cyan para el encabezado
        
        // Panel para el título centrado
        JPanel headerTitlePanel = new JPanel();
        headerTitlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        headerTitlePanel.setBackground(COLOR_PRIMARY);
        headerLabel = new JLabel("Inicio");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setForeground(COLOR_BACKGROUND); // Texto blanco
        headerTitlePanel.add(headerLabel);
        
        // Panel para el botón de cierre (a la derecha)
        
        
        headerPanel.add(headerTitlePanel, BorderLayout.CENTER);
        dynamicContentPanel.add(headerPanel, BorderLayout.NORTH);

        
        // Crear panel simple para la página de inicio
        inicioPanel = new JPanel(new BorderLayout());
        inicioPanel.setBackground(COLOR_BACKGROUND);
        
        // Contenido de bienvenida en el panel de inicio centrado
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(COLOR_BACKGROUND);
        
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(COLOR_BACKGROUND);
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        
        JLabel welcomeLabel = new JLabel("Bienvenido a CliniQ");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(COLOR_TEXT);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel descriptionLabel = new JLabel("Sistema de Gestión de Citas Médicas");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        descriptionLabel.setForeground(COLOR_TEXT);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Paneles para centrar cada elemento
        JPanel welcomeTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        welcomeTitlePanel.setBackground(COLOR_BACKGROUND);
        welcomeTitlePanel.add(welcomeLabel);
        
        JPanel descPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        descPanel.setBackground(COLOR_BACKGROUND);
        descPanel.add(descriptionLabel);
        
        welcomePanel.add(Box.createVerticalGlue());
        welcomePanel.add(welcomeTitlePanel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        welcomePanel.add(descPanel);
        welcomePanel.add(Box.createVerticalGlue());
        
        centerPanel.add(welcomePanel);
        inicioPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Mostrar inicialmente el panel de inicio
        dynamicContentPanel.add(inicioPanel, BorderLayout.CENTER);
        headerLabel.setText("Inicio");
        setActiveButton(inicioButton); // Establecer el botón de inicio como activo inicialmente

        // Añadir los paneles principales
        contentPanel.add(menuPanel, BorderLayout.WEST);
        contentPanel.add(dynamicContentPanel, BorderLayout.CENTER);

        getContentPane().add(contentPanel);
    }
    
    // Método para cambiar entre paneles
    private void mostrarPanel(String panelName) {
        // Obtener el panel de contenido dinámico (está en el centro del contentPanel)
        JPanel dynamicContentPanel = (JPanel) contentPanel.getComponent(1);
        
        // Guardar referencia al panel del header
        JPanel headerPanel = (JPanel) headerLabel.getParent().getParent();
        
        // Limpiar el panel de contenido (excepto el header)
        dynamicContentPanel.removeAll();
        
        // Actualizar el encabezado
        headerLabel.setText(panelName);
        
        // Volver a añadir el header
        dynamicContentPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Mostrar el panel correspondiente
        switch (panelName) {
            case "Inicio":
                dynamicContentPanel.add(inicioPanel, BorderLayout.CENTER);
                break;
            case "Pacientes":
                dynamicContentPanel.add(pacientePanel, BorderLayout.CENTER);
                break;
            case "Citas":
                dynamicContentPanel.add(citasPanel, BorderLayout.CENTER);
                break;
            case "Médicos":
                dynamicContentPanel.add(medicosPanel, BorderLayout.CENTER);
                break;
            case "Consultorios": // Nuevo caso
                dynamicContentPanel.add(consultoriosPanel, BorderLayout.CENTER);
                break;
            case "Facturación":
                dynamicContentPanel.add(facturacionPanel, BorderLayout.CENTER);
                break;
        }
        
        // Refrescar el panel
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }

    /**
     * Método para establecer el botón activo y actualizar la interfaz visual
     * @param button Botón que debe marcarse como activo
     */
    private void setActiveButton(JButton button) {
        // Si ya hay un botón activo, restaurar su color original
        if (activeButton != null && activeButton != button) {
            activeButton.setBackground(COLOR_PRIMARY);
        }
        
        // Establecer el nuevo botón activo
        activeButton = button;
        if (activeButton != null) {
            // Aplicar color más oscuro al botón activo
            activeButton.setBackground(new Color(0, 120, 148)); // Color aún más oscuro para el botón activo
        }
    }
    
    private JButton createMenuButton(String text, String iconPath) {
        // Crear el botón con texto
        JButton button = new JButton(text);
        
        // Configurar el icono si existe
        if (iconPath != null) {
            try {
                ImageIcon icon = new ImageIcon(iconPath);
                if (icon.getIconWidth() > 0) { // Verifica si el icono se cargó correctamente
                    // Redimensionar el icono a un tamaño adecuado
                    Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(img));
                    button.setHorizontalAlignment(SwingConstants.LEFT);
                    button.setHorizontalTextPosition(SwingConstants.RIGHT);
                    button.setIconTextGap(10);
                } else {
                    System.err.println("No se pudo cargar el icono: " + iconPath);
                }
            } catch (Exception e) {
                System.err.println("Error al cargar el icono: " + e.getMessage());
            }
        }
        
        button.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(COLOR_PRIMARY); // Color cyan para los botones
        button.setForeground(COLOR_BACKGROUND); // Texto blanco
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(menuWidth - 10, 40));
        button.setMaximumSize(new Dimension(menuWidth - 10, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover (solo cuando el botón no está activo)
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Si no es el botón activo, aplicar efecto hover
                if (button != activeButton) {
                    button.setBackground(new Color(0, 140, 168)); // Cyan más oscuro al pasar el mouse
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                // Si no es el botón activo, restaurar color original
                if (button != activeButton) {
                    button.setBackground(COLOR_PRIMARY); // Color original al salir
                }
            }
        });
        
        return button;
    }

    public void mostrarVistaSegunRol(Usuario usuario) {
        pacienteButton.setEnabled(false);
        citasButton.setEnabled(false);
        medicosButton.setEnabled(false);
        consultorioButton.setEnabled(false); // Nuevo
        facturacionButton.setEnabled(false);

        if(usuario.getTipoUsuario() == TipoUsuario.REGISTRADORA) {
            pacienteButton.setEnabled(true);
            citasButton.setEnabled(true);
            medicosButton.setEnabled(true);
            consultorioButton.setEnabled(true); // Nuevo
            inicioButton.setEnabled(true);
            logoutButton.setEnabled(true);
            mostrarPanel("Pacientes");
        } 
        else if(usuario.getTipoUsuario() == TipoUsuario.CAJERA) {
            facturacionButton.setEnabled(true);
            inicioButton.setEnabled(true);
            logoutButton.setEnabled(true);
            mostrarPanel("Facturación");
        }

        
        
        this.setVisible(true);
        this.toFront();
    }
    
}
