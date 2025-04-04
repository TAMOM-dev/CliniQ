package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class PacienteInterfaz extends JFrame {

	private static final long serialVersionUID = 1L;
	public PacienteInterfaz() {
		 setTitle("CliniQ - Pacientes");
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	     int screenWidth = (int) screenSize.getWidth();
	     int screenHeight = (int) screenSize.getHeight();

	     int frameWidth = (int) (screenWidth * 0.7);
	     int frameHeight = (int) (screenHeight * 0.7);

	     setSize(frameWidth, frameHeight);
	     setLocationRelativeTo(null);

	     setLayout(new BorderLayout());

	     // Left Panel
	     JPanel leftPanel = new JPanel();
	     leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
	     leftPanel.setBackground(new Color(240, 240, 240)); // Light gray background
	     leftPanel.setPreferredSize(new Dimension(150, getHeight()));
	     leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	     JLabel logoLabel = new JLabel("CliniQ");
	     logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
	     logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
	     leftPanel.add(logoLabel);
	     leftPanel.add(Box.createVerticalStrut(30));

	     JButton inicioButton = new JButton("Inicio");
	     inicioButton.setAlignmentX(Component.LEFT_ALIGNMENT);
	     leftPanel.add(inicioButton);
	     leftPanel.add(Box.createVerticalStrut(10));

	     JButton pacienteButton = new JButton("Paciente");
	     pacienteButton.setAlignmentX(Component.LEFT_ALIGNMENT);
	     pacienteButton.setBackground(new Color(220, 220, 220)); // Indicate selection
	     leftPanel.add(pacienteButton);
	     leftPanel.add(Box.createVerticalStrut(10));

	     JButton citasButton = new JButton("Citas");
	     citasButton.setAlignmentX(Component.LEFT_ALIGNMENT);
	     leftPanel.add(citasButton);
	     leftPanel.add(Box.createVerticalStrut(10));

	     JButton medicosButton = new JButton("Medicos");
	     medicosButton.setAlignmentX(Component.LEFT_ALIGNMENT);
	     leftPanel.add(medicosButton);
	     leftPanel.add(Box.createVerticalStrut(10));

	     JButton estudiosButton = new JButton("Estudios");
	     estudiosButton.setAlignmentX(Component.LEFT_ALIGNMENT);
	     leftPanel.add(estudiosButton);
	     leftPanel.add(Box.createVerticalStrut(10));

	     JButton facturacionButton = new JButton("Facturacion");
	     facturacionButton.setAlignmentX(Component.LEFT_ALIGNMENT);
	     leftPanel.add(facturacionButton);

	     add(leftPanel, BorderLayout.WEST);

	     // Center Panel (Top Section - Search)
	     JPanel centerTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	     centerTopPanel.setBackground(new Color(245, 245, 245));
	     centerTopPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

	     JLabel pacientesLabel = new JLabel("Pacientes");
	     pacientesLabel.setFont(new Font("Arial", Font.BOLD, 16));
	     centerTopPanel.add(pacientesLabel);

	     JTextField searchTextField = new JTextField(20);
	     centerTopPanel.add(searchTextField);

	     JButton buscarButton = new JButton("Buscar");
	     centerTopPanel.add(buscarButton);

	     JPanel centerPanel = new JPanel(new BorderLayout());
	     centerPanel.add(centerTopPanel, BorderLayout.NORTH);

	     // Center Panel (Pink Placeholder)
	     JPanel pinkPlaceholder = new JPanel();
	     pinkPlaceholder.setBackground(new Color(255, 204, 204));
	     pinkPlaceholder.setPreferredSize(new Dimension(400, 100));
	     pinkPlaceholder.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
	     centerPanel.add(pinkPlaceholder, BorderLayout.CENTER);

	     add(centerPanel, BorderLayout.CENTER);

	     // Right Panel
	     JPanel rightPanel = new JPanel();
	     rightPanel.setLayout(new GridBagLayout());
	     rightPanel.setBackground(new Color(255, 230, 230));
	     rightPanel.setPreferredSize(new Dimension(350, getHeight()));
	     rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	     GridBagConstraints gbc = new GridBagConstraints();
	     gbc.gridx = 0;
	     gbc.gridy = GridBagConstraints.RELATIVE;
	     gbc.fill = GridBagConstraints.HORIZONTAL;
	     gbc.insets = new Insets(5, 5, 5, 5);
	     gbc.weightx = 1.0;

	     JLabel idLabel = new JLabel("ID");
	     rightPanel.add(idLabel, gbc);
	     JTextField idTextField = new JTextField(10);
	     rightPanel.add(idTextField, gbc);

	     JLabel nombreLabel = new JLabel("Nombre");
	     rightPanel.add(nombreLabel, gbc);
	     JTextField nombreTextField = new JTextField(20);
	     rightPanel.add(nombreTextField, gbc);

	     JLabel apellidoLabel = new JLabel("Apellido");
	     rightPanel.add(apellidoLabel, gbc);
	     JTextField apellidoTextField = new JTextField(20);
	     rightPanel.add(apellidoTextField, gbc);

	     JLabel correoLabel = new JLabel("Correo Electronica");
	     rightPanel.add(correoLabel, gbc);
	     JTextField correoTextField = new JTextField(20);
	     rightPanel.add(correoTextField, gbc);

	     JLabel direccionLabel = new JLabel("Direccion");
	     rightPanel.add(direccionLabel, gbc);
	     JTextField direccionTextField = new JTextField(20);
	     rightPanel.add(direccionTextField, gbc);

	     JLabel fechaNacimientoLabel = new JLabel("Fecha de nacimiento");
	     rightPanel.add(fechaNacimientoLabel, gbc);
	     JTextField fechaNacimientoTextField = new JTextField(15);
	     rightPanel.add(fechaNacimientoTextField, gbc);

	     JLabel estudioLabel = new JLabel("Estudio");
	     rightPanel.add(estudioLabel, gbc);
	     JTextField estudioTextField = new JTextField(20);
	     rightPanel.add(estudioTextField, gbc);

	     JPanel horaTechDiaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	     JLabel horaLabel = new JLabel("hora / tech / dia");
	     JTextField horaTextField = new JTextField(15);
	     horaTechDiaPanel.add(horaLabel);
	     horaTechDiaPanel.add(horaTextField);
	     rightPanel.add(horaTechDiaPanel, gbc);

	     JLabel estudio2Label = new JLabel("estudio");
	     rightPanel.add(estudio2Label, gbc);
	     JTextField estudio2TextField = new JTextField(20);
	     rightPanel.add(estudio2TextField, gbc);

	     JPanel aseguradoEfectivoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	     JLabel aseguradoLabel = new JLabel("Asegurado");
	     JCheckBox aseguradoCheckBox = new JCheckBox();
	     JLabel efectivoLabel = new JLabel("Efectivo");
	     JCheckBox efectivoCheckBox = new JCheckBox();
	     aseguradoEfectivoPanel.add(aseguradoLabel);
	     aseguradoEfectivoPanel.add(aseguradoCheckBox);
	     aseguradoEfectivoPanel.add(Box.createHorizontalStrut(15));
	     aseguradoEfectivoPanel.add(efectivoLabel);
	     aseguradoEfectivoPanel.add(efectivoCheckBox);
	     rightPanel.add(aseguradoEfectivoPanel, gbc);

	     JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	     JButton guardarButton = new JButton("Guardar");
	     guardarButton.setBackground(new Color(255, 153, 153)); // Pink button
	     JButton limpiarButton = new JButton("Limpiar");
	     limpiarButton.setBackground(new Color(255, 153, 153)); // Pink button
	     buttonPanel.add(guardarButton);
	     buttonPanel.add(limpiarButton);
	     gbc.fill = GridBagConstraints.NONE;
	     gbc.anchor = GridBagConstraints.EAST;
	     rightPanel.add(buttonPanel, gbc);

	     add(rightPanel, BorderLayout.EAST);

	     setVisible(true);
	 }

	 public static void main(String[] args) {
	     SwingUtilities.invokeLater(PacienteInterfaz::new);
	 }

}
