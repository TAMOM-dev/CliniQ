package dev.cliniq.cliniq.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PacientePanel extends JPanel {
    // Colores de la aplicación
    private final Color COLOR_PRIMARY = new Color(0, 158, 188); // Cyan
    private final Color COLOR_SECONDARY = new Color(220, 249, 255); // Cyan muy claro
    private final Color COLOR_BACKGROUND = Color.WHITE;
    private final Color COLOR_TEXT = new Color(30, 30, 30);
    private final Color COLOR_BUTTON = new Color(0, 188, 212); // Cyan más claro
    
    private JTextField txtBuscar;
    private JTable tablePacientes;
    private JTextField txtIdPaciente;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtDireccion;
    private JTextField txtFechaNacimiento;
    private JTextField txtTelefono;
    private JButton btnGuardar;
    private JButton btnLimpiar;

    public PacientePanel() {
        setLayout(new BorderLayout());
        setBackground(COLOR_BACKGROUND); // Fondo blanco

        // Panel superior (búsqueda)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(COLOR_BACKGROUND);
        JLabel lblBuscar = new JLabel("Pacientes");
        lblBuscar.setForeground(COLOR_TEXT);
        txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(COLOR_BUTTON);
        btnBuscar.setForeground(COLOR_BACKGROUND);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        searchPanel.add(lblBuscar);
        searchPanel.add(txtBuscar);
        searchPanel.add(btnBuscar);
        
        add(searchPanel, BorderLayout.NORTH);

        // Panel central (tabla y formulario)
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setBackground(COLOR_BACKGROUND);
        
        // Tabla de pacientes (lado izquierdo)
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(COLOR_BACKGROUND);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
        
        // Crear modelo de tabla
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
        tableModel.addColumn("Email");
        tableModel.addColumn("Teléfono");
        
        tablePacientes = new JTable(tableModel);
        tablePacientes.setRowHeight(25);
        tablePacientes.getTableHeader().setBackground(COLOR_PRIMARY);
        tablePacientes.getTableHeader().setForeground(COLOR_BACKGROUND);
        tablePacientes.setSelectionBackground(COLOR_SECONDARY);
        tablePacientes.setSelectionForeground(COLOR_TEXT);
        tablePacientes.setGridColor(new Color(240, 240, 240));
        tablePacientes.setBorder(BorderFactory.createLineBorder(COLOR_SECONDARY));

        
        JScrollPane scrollPane = new JScrollPane(tablePacientes);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY, 1));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de formulario (lado derecho)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(COLOR_BACKGROUND);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 5, 10, 10),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARY, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
            )
        ));
        
        // Campos de formulario
        JPanel idPanel = createFormField("ID Paciente", txtIdPaciente = new JTextField());
        JPanel nombrePanel = createFormField("Nombre", txtNombre = new JTextField());
        JPanel apellidoPanel = createFormField("Apellido", txtApellido = new JTextField());
        JPanel emailPanel = createFormField("Email", txtEmail = new JTextField());
        JPanel direccionPanel = createFormField("Dirección", txtDireccion = new JTextField());
        JPanel fechaNacimientoPanel = createFormField("Fecha Nacimiento", txtFechaNacimiento = new JTextField());
        JPanel telefonoPanel = createFormField("Teléfono", txtTelefono = new JTextField());
        
        // Panel de botones
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBackground(COLOR_BACKGROUND);
        
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(COLOR_PRIMARY);
        btnGuardar.setForeground(COLOR_BACKGROUND);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnGuardar.setBackground(new Color(0, 140, 168)); // Cyan más oscuro
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btnGuardar.setBackground(COLOR_PRIMARY);
            }
        });
        
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBackground(COLOR_BUTTON);
        btnLimpiar.setForeground(COLOR_BACKGROUND);
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLimpiar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLimpiar.setBackground(new Color(0, 163, 187)); // Cyan intermedio
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btnLimpiar.setBackground(COLOR_BUTTON);
            }
        });
        
        buttonsPanel.add(btnGuardar);
        buttonsPanel.add(btnLimpiar);
        
        // Añadir campos al formulario
        formPanel.add(idPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(nombrePanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(apellidoPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(emailPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(direccionPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(fechaNacimientoPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(telefonoPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(buttonsPanel);
        formPanel.add(Box.createVerticalGlue());
        
        // Añadir tabla y formulario al panel central
        centerPanel.add(tablePanel);
        centerPanel.add(formPanel);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Configurar eventos
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
    }
    
    private JPanel createFormField(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(COLOR_BACKGROUND);
        
        JLabel label = new JLabel(labelText);
        label.setForeground(COLOR_TEXT);
        label.setPreferredSize(new Dimension(150, 25));
        label.setMaximumSize(new Dimension(150, 25));
        
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_PRIMARY),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        
        panel.add(label);
        panel.add(textField);
        
        return panel;
    }
    
    private void limpiarFormulario() {
        txtIdPaciente.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
        txtFechaNacimiento.setText("");
        txtTelefono.setText("");
    }


}
