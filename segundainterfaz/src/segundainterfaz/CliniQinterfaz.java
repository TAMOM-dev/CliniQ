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
	        sidePanel.setLayout(new GridLayout(6, 1));
	        sidePanel.setPreferredSize(new Dimension(150, getHeight()));
	        sidePanel.setBackground(Color.LIGHT_GRAY);
	        
	        // Botones del menú
	        JButton btnInicio = new JButton("Inicio");
	        JButton btnPaciente = new JButton("Paciente");
	        JButton btnCitas = new JButton("Citas");
	        JButton btnMedicos = new JButton("Medicos");
	        JButton btnEstudios = new JButton("Estudios");
	        JButton btnFacturacion = new JButton("Facturación");
	        
	        sidePanel.add(btnInicio);
	        sidePanel.add(btnPaciente);
	        sidePanel.add(btnCitas);
	        sidePanel.add(btnMedicos);
	        sidePanel.add(btnEstudios);
	        sidePanel.add(btnFacturacion);
	        
	        // Panel superior (header)
	        JPanel topPanel = new JPanel();
	        topPanel.setPreferredSize(new Dimension(getWidth(), 40));
	        topPanel.setBackground(Color.GRAY);
	        topPanel.add(new JLabel("Inicio"));
	        
	        // Panel derecho (formulario)
	        JPanel rightPanel = new JPanel();
	        rightPanel.setPreferredSize(new Dimension(200, getHeight()));
	        rightPanel.setBackground(new Color(255, 182, 193));
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
	        contentPanel.setBackground(Color.PINK);
	        
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
	    }

	    private void showPanel(JPanel panel) {
	        contentPanel.removeAll();
	        contentPanel.add(panel, BorderLayout.CENTER);
	        contentPanel.revalidate();
	        contentPanel.repaint();
	    }

	    // Clases de ejemplo para los paneles
	    class InicioPanel extends JPanel {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public InicioPanel() {
	            setBackground(Color.PINK);
	            add(new JLabel("Bienvenido a CliniQ"));
	        }
	    }

	    class PacientePanel extends JPanel {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public PacientePanel() {
	            setBackground(Color.PINK);
	            add(new JLabel("Gestión de Pacientes"));
	        }
	    }
	    
	    class CitasPanel extends JPanel {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public CitasPanel() {
	            setBackground(Color.PINK);
	            add(new JLabel("Gestión de Citas"));
	        }
	    }
	    
	    class MedicosPanel extends JPanel {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public MedicosPanel() {
	            setBackground(Color.PINK);
	            add(new JLabel("Gestión de Médicos"));
	        }
	    }
	    
	    class EstudiosPanel extends JPanel {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public EstudiosPanel() {
	            setBackground(Color.PINK);
	            add(new JLabel("Gestión de Estudios"));
	        }
	    }
	    
	    class FacturacionPanel extends JPanel {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public FacturacionPanel() {
	            setBackground(Color.PINK);
	            add(new JLabel("Gestión de Facturación"));
	        }
	    }
	    
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> new CliniQinterfaz().setVisible(true));
	    }
}
