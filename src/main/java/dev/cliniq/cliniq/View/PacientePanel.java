package dev.cliniq.cliniq.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.cliniq.cliniq.Model.Paciente;
import dev.cliniq.cliniq.Service.IPacienteService;
import jakarta.annotation.PostConstruct;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
public class PacientePanel extends JPanel {
    @Autowired
    private IPacienteService pacienteService;

    // Colores de la aplicación
    private final Color COLOR_PRIMARY = new Color(0, 158, 188); // Cyan
    private final Color COLOR_SECONDARY = new Color(220, 249, 255); // Cyan muy claro
    private final Color COLOR_BACKGROUND = Color.WHITE;
    private final Color COLOR_TEXT = new Color(30, 30, 30);
    private final Color COLOR_BUTTON = new Color(0, 188, 212); // Cyan más claro
    
    private JTextField txtBuscar;
    private JTable tablePacientes;
    private DefaultTableModel tableModel;
    private JTextField txtIdPaciente;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtDireccion;
    private JTextField txtFechaNacimiento;
    private JTextField txtTelefono;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnBuscar;


    // Constructor sin argumentos requerido para Spring
    public PacientePanel() {
        initComponents();
    }

    @PostConstruct
    public void initAfterConstruct() {
        cargarPacientes();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(COLOR_BACKGROUND); // Fondo blanco

        // Panel superior (búsqueda)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(COLOR_BACKGROUND);
        JLabel lblBuscar = new JLabel("Pacientes");
        lblBuscar.setForeground(COLOR_TEXT);
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");
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
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
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
        
        // Agregar evento de selección de fila en la tabla
        tablePacientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablePacientes.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    seleccionarPaciente(filaSeleccionada);
                }
            }
        });

        
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
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPaciente();
            }
        });
        
        // Configurar evento para el botón buscar
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPacientes();
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
        tablePacientes.clearSelection();
    }
    
    /**
     * Este método se llamará después de que Spring haya inyectado todas las dependencias
     */
    @Autowired
    public void init() {
        // Solo cargamos los datos cuando el servicio ha sido inyectado
        if (pacienteService != null) {
            cargarPacientes();
        }
    }
    
    /**
     * Carga los pacientes desde la base de datos y los muestra en la tabla
     */
    public void cargarPacientes() {
        // Limpiar la tabla antes de cargar los datos
        limpiarTabla();
        
        try {
            // Obtener la lista de pacientes desde el servicio
            List<Paciente> pacientes = pacienteService.listarPacientes();
            
            // Agregar cada paciente al modelo de tabla
            for (Paciente paciente : pacientes) {
                Object[] fila = new Object[5];
                fila[0] = paciente.getIdPaciente();
                fila[1] = paciente.getNombre();
                fila[2] = paciente.getApellido();
                fila[3] = paciente.getEmail();
                fila[4] = paciente.getTelefono();
                tableModel.addRow(fila);
            }
            
            // Mostrar mensaje de éxito o manejo según sea necesario
            if (pacientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron pacientes en la base de datos", 
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los pacientes: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Limpia todas las filas de la tabla
     */
    private void limpiarTabla() {
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
    }
    
    /**
     * Llena el formulario con los datos del paciente seleccionado
     */
    private void seleccionarPaciente(int filaSeleccionada) {
        Long idPaciente = (Long) tableModel.getValueAt(filaSeleccionada, 0);
        
        // Buscar el paciente en la base de datos
        Paciente paciente = pacienteService.buscarPacientePorId(idPaciente);
        
        if (paciente != null) {
            // Llenar el formulario con los datos del paciente
            txtIdPaciente.setText(paciente.getIdPaciente().toString());
            txtNombre.setText(paciente.getNombre());
            txtApellido.setText(paciente.getApellido());
            txtEmail.setText(paciente.getEmail());
            txtDireccion.setText(paciente.getDireccion());
            
            // Formatear la fecha si no es nula
            if (paciente.getFechaNacimiento() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                txtFechaNacimiento.setText(paciente.getFechaNacimiento().format(formatter));
            } else {
                txtFechaNacimiento.setText("");
            }
            
            txtTelefono.setText(paciente.getTelefono());
        }
    }
    
    /**
     * Guarda o actualiza un paciente en la base de datos
     */
    private void guardarPaciente() {
        try {
            // Crear un nuevo objeto paciente
            Paciente paciente = new Paciente();
            
            // Si hay un ID, significa que estamos actualizando un paciente existente
            if (!txtIdPaciente.getText().isEmpty()) {
                paciente.setIdPaciente(Long.parseLong(txtIdPaciente.getText()));
            }
            
            // Validar y asignar los campos del formulario
            if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Los campos Nombre y Apellido son obligatorios", 
                    "Error de validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            paciente.setNombre(txtNombre.getText());
            paciente.setApellido(txtApellido.getText());
            paciente.setEmail(txtEmail.getText());
            paciente.setDireccion(txtDireccion.getText());
            
            // Parsear y validar la fecha de nacimiento
            if (!txtFechaNacimiento.getText().isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate fechaNacimiento = LocalDate.parse(txtFechaNacimiento.getText(), formatter);
                    paciente.setFechaNacimiento(fechaNacimiento);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(this, 
                        "Formato de fecha incorrecto. Use yyyy-MM-dd (ej: 1990-01-31)", 
                        "Error de validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            
            paciente.setTelefono(txtTelefono.getText());
            
            // Guardar el paciente en la base de datos
            pacienteService.guardarPaciente(paciente);
            
            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, "Paciente guardado exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
            // Recargar la tabla y limpiar el formulario
            cargarPacientes();
            limpiarFormulario();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el paciente: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Busca pacientes según el criterio en el campo de búsqueda
     */
    private void buscarPacientes() {
        // Por ahora simplemente recargamos todos los pacientes
        // En una implementación futura podría filtrar por nombre, apellido, etc.
        cargarPacientes();
        
        // Nota: Podrías implementar un método de búsqueda en el servicio y llamarlo aquí
        // según el criterio ingresado en txtBuscar.getText()
    }
}
