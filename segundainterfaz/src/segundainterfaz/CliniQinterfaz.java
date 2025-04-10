package segundainterfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class CliniQinterfaz extends JFrame {

	private static final long serialVersionUID = 1L;

	    private JPanel contentPanel;

	    public CliniQinterfaz() {
	        setTitle("CliniQ - Sistema de Gestión de Citas");
	        setSize(900, 500);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setLayout(new BorderLayout());

	        // Panel lateral (menú)
	        JPanel sidePanel = new JPanel();
	        // Se actualiza el layout a 7 filas para agregar el botón extra de "Consultorios"
	        sidePanel.setLayout(new GridLayout(7, 1));
	        sidePanel.setPreferredSize(new Dimension(150, getHeight()));
	        sidePanel.setBackground(Color.LIGHT_GRAY);

	        // Botones del menú
	        JButton btnInicio = new JButton("Inicio");
	        JButton btnPaciente = new JButton("Paciente");
	        JButton btnCitas = new JButton("Citas");
	        JButton btnMedicos = new JButton("Medicos");
	        JButton btnEstudios = new JButton("Estudios");
	        JButton btnFacturacion = new JButton("Facturación");
	        JButton btnConsultorios = new JButton("Consultorios");

	        sidePanel.add(btnInicio);
	        sidePanel.add(btnPaciente);
	        sidePanel.add(btnCitas);
	        sidePanel.add(btnMedicos);
	        sidePanel.add(btnEstudios);
	        sidePanel.add(btnFacturacion);
	        sidePanel.add(btnConsultorios);

	        // Panel superior (header)
	        JPanel topPanel = new JPanel();
	        topPanel.setPreferredSize(new Dimension(getWidth(), 40));
	        topPanel.setBackground(Color.GRAY);
	        topPanel.add(new JLabel("Inicio"));

	        // Panel derecho (formulario)
	        JPanel rightPanel = new JPanel();
	        rightPanel.setPreferredSize(new Dimension(200, getHeight()));
	        // Se cambia el color de fondo de rosa a azul
	        rightPanel.setBackground(Color.BLUE);
	        rightPanel.setLayout(new GridLayout(8, 1, 5, 5));

	        // Campos del formulario
	        rightPanel.add(new JLabel("ID"));
	        rightPanel.add(new JTextField());
	        rightPanel.add(new JLabel("Nombre"));
	        rightPanel.add(new JTextField());
	        rightPanel.add(new JLabel("Apellido"));
	        rightPanel.add(new JTextField());
	        rightPanel.add(new JLabel("Dirección"));
	        rightPanel.add(new JTextField());

	        JButton btnGuardar = new JButton("Guardar");
	        JButton btnLimpiar = new JButton("Limpiar");
	        rightPanel.add(btnGuardar);
	        rightPanel.add(btnLimpiar);

	        // Panel central (contenido dinámico)
	        contentPanel = new JPanel();
	        contentPanel.setLayout(new BorderLayout());
	        // Se cambia el color de fondo de rosa a azul
	        contentPanel.setBackground(Color.BLUE);

	        // Agregar paneles a la ventana
	        add(sidePanel, BorderLayout.WEST);
	        add(topPanel, BorderLayout.NORTH);
	        add(rightPanel, BorderLayout.EAST);
	        add(contentPanel, BorderLayout.CENTER);

	        // Eventos de los botones para cambiar el contenido del panel central
	        btnInicio.addActionListener(_ -> showPanel(new InicioPanel()));
	        btnPaciente.addActionListener(_ -> showPanel(new PacientePanel()));
	        btnCitas.addActionListener(_ -> showPanel(new CitasPanel()));
	        btnMedicos.addActionListener(_ -> showPanel(new MedicosPanel()));
	        btnEstudios.addActionListener(_ -> showPanel(new EstudiosPanel()));
	        btnFacturacion.addActionListener(_ -> showPanel(new FacturacionPanel()));
	        btnConsultorios.addActionListener(_ -> showPanel(new ConsultoriosPanel()));
	    }

	    private void showPanel(JPanel panel) {
	        contentPanel.removeAll();
	        contentPanel.add(panel, BorderLayout.CENTER);
	        contentPanel.revalidate();
	        contentPanel.repaint();
	    }

	    // Panel de Inicio
	    class InicioPanel extends JPanel {
	        private static final long serialVersionUID = 1L;
	        public InicioPanel() {
	            setBackground(Color.BLUE);
	            add(new JLabel("Bienvenido a CliniQ"));
	        }
	    }

	    // Panel de Gestión de Pacientes
	    class PacientePanel extends JPanel {
	        private static final long serialVersionUID = 1L;
	        public PacientePanel() {
	            setBackground(Color.BLUE);
	            add(new JLabel("Gestión de Pacientes"));
	        }
	    }
	    
	    // Panel de Gestión de Citas
	    class CitasPanel extends JPanel {
	        private static final long serialVersionUID = 1L;
	        public CitasPanel() {
	            setBackground(Color.BLUE);
	            add(new JLabel("Gestión de Citas"));
	        }
	    }
	    
	    // Panel de Gestión de Médicos
	    class MedicosPanel extends JPanel {
	        private static final long serialVersionUID = 1L;
	        public MedicosPanel() {
	            setBackground(Color.BLUE);
	            add(new JLabel("Gestión de Médicos"));
	        }
	    }
	    
	    // Panel de Gestión de Estudios
	    class EstudiosPanel extends JPanel {
	        private static final long serialVersionUID = 1L;
	        public EstudiosPanel() {
	            setBackground(Color.BLUE);
	            add(new JLabel("Gestión de Estudios"));
	        }
	    }
	    
	    // Panel de Gestión de Facturación
	    class FacturacionPanel extends JPanel {
	        private static final long serialVersionUID = 1L;
	        public FacturacionPanel() {
	            setBackground(Color.BLUE);
	            add(new JLabel("Gestión de Facturación"));
	        }
	    }

	    // Nuevo Panel de Gestión de Consultorios
	    class ConsultoriosPanel extends JPanel {
	        private static final long serialVersionUID = 1L;
	        public ConsultoriosPanel() {
	            setBackground(Color.BLUE);
	            add(new JLabel("Gestión de Consultorios"));
	        }
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> new CliniQinterfaz().setVisible(true));
	    }
}
