package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class FacturacionInterfaz extends JFrame {

	private static final long serialVersionUID = 1L;
	public FacturacionInterfaz() {
        setTitle("CliniQ - Facturacion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        int frameWidth = (int) (screenWidth * 0.7);
        int frameHeight = (int) (screenHeight * 0.7);

        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Panel izquierdo (barra de navegación)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(240, 240, 240));
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
        facturacionButton.setBackground(new Color(220, 220, 220)); // Indica la selección
        leftPanel.add(facturacionButton);

        add(leftPanel, BorderLayout.WEST);

        // Panel central (barra de búsqueda y tabla)
        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel centerTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        centerTopPanel.setBackground(new Color(245, 245, 245));
        centerTopPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel facturacionLabel = new JLabel("Facturacion");
        facturacionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        centerTopPanel.add(facturacionLabel);

        JTextField searchTextField = new JTextField(20);
        centerTopPanel.add(searchTextField);

        JButton buscarButton = new JButton("Buscar");
        centerTopPanel.add(buscarButton);

        centerPanel.add(centerTopPanel, BorderLayout.NORTH);

        // Tabla de facturación (sin datos)
        String[] columnNames = {"Date", "Description", "Amount", "Estado"};
        Object[][] data = {}; // Tabla vacía

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable facturacionTable = new JTable(model);
        JScrollPane tableScrollPane = new JScrollPane(facturacionTable);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Panel derecho (método de pago)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(new Color(255, 230, 230));
        rightPanel.setPreferredSize(new Dimension(200, getHeight()));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel metodoPagoLabel = new JLabel("Metodo de Pago");
        metodoPagoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        rightPanel.add(metodoPagoLabel);
        rightPanel.add(Box.createVerticalStrut(10));

        JButton efectivoButton = new JButton("Efectivo");
        rightPanel.add(efectivoButton);
        rightPanel.add(Box.createVerticalStrut(5));

        JButton seguroButton = new JButton("Seguro");
        rightPanel.add(seguroButton);
        rightPanel.add(Box.createVerticalStrut(5));

        JButton aseguradoraButton = new JButton("Aseguradora");
        rightPanel.add(aseguradoraButton);
        rightPanel.add(Box.createVerticalStrut(5));

        JLabel numeroPolizaLabel = new JLabel("Numero de poliza");
        rightPanel.add(numeroPolizaLabel);
        JTextField numeroPolizaTextField = new JTextField(15);
        rightPanel.add(numeroPolizaTextField);

        add(rightPanel, BorderLayout.EAST);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FacturacionInterfaz::new);
    }

}
