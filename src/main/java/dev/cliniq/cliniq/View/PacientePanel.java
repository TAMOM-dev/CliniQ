package dev.cliniq.cliniq.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.cliniq.cliniq.Model.Paciente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dev.cliniq.cliniq.Service.pacienteService;


@Component
public class PacientePanel extends JPanel {
    private pacienteService pacienteService;

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


    @Autowired
    public PacientePanel(pacienteService pacienteService) {
        this.pacienteService = pacienteService;
        initComponents();
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idTexto = txtIdPaciente.getText().trim();
                if (idTexto.isEmpty()) {
                    // Lógica para agregar un nuevo paciente
                    agregarPaciente();
                } else {
                    // Lógica para actualizar un paciente existente
                    actualizarPaciente();
                }
            }
        });
        tablePacientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarPacienteSeleccionado();
            }
        });

        // btnGuardar.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         guardarPaciente();
        //     }
        // });
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

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = txtBuscar.getText().trim();
                
                // Creamos el sorter para la tabla con el modelo correspondiente
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
                tablePacientes.setRowSorter(sorter);
                
                // Si el campo de búsqueda está vacío, se remueve el filtro
                if (searchText.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    // Se aplica un filtro que ignore mayúsculas y minúsculas
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
                }
            }
        });

        
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
        this.tableModel = new DefaultTableModel(0, 7);
        String[] columnas = {"ID", "Nombre", "Apellido", "Email", "Direccion", "Fecha Nacimiento", "Teléfono"};
        tableModel.setColumnIdentifiers(columnas);

        //Instanciar la tabla
        this.tablePacientes = new JTable(tableModel);
        
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

        //popupMenu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Eliminar");
        popupMenu.add(deleteItem);

        tablePacientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = tablePacientes.rowAtPoint(e.getPoint());
                    tablePacientes.setRowSelectionInterval(row, row);
                    popupMenu.show(tablePacientes, e.getX(), e.getY());                 
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mousePressed(e);
            }
        });

        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tablePacientes.getSelectedRow();
                if (selectedRow >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar este paciente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        Long idPaciente = (Long) tableModel.getValueAt(selectedRow, 0);
                        Paciente paciente = pacienteService.buscarPacientePorId(idPaciente);
                        if (paciente != null) {
                            pacienteService.eliminarPaciente(paciente);
                            tableModel.removeRow(selectedRow);
                            JOptionPane.showMessageDialog(null, "Paciente eliminado con éxito.");
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontró el paciente.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un paciente para eliminar.");
                }
            }
                });
        
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
        txtIdPaciente.setEditable(false);
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
            }
        });
        
        // Configurar evento para el botón buscar
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        listarPacientes();

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
     * Carga los pacientes desde la base de datos y los muestra en la tabla
     */
    private void listarPacientes() {
        //Limpiar la tabla antes de cargar los datos
        tableModel.setRowCount(0);

        // Limpiar el formulario
        limpiarFormulario();
        //Obtener Pacientes de la base de datos
        var pacientes = pacienteService.listarPacientes();
        pacientes.forEach((paciente) -> {
            Object[] renglonPaciente = {
                paciente.getIdPaciente(),
                paciente.getNombre(),
                paciente.getApellido(),
                paciente.getEmail(),
                paciente.getDireccion(),
                paciente.getFechaNacimiento(),
                paciente.getTelefono()
            };

            this.tableModel.addRow(renglonPaciente);
        });

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
    
    private void agregarPaciente() {
        // System.out.println("agregarPaciente method called");
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarMensaje("Debe completar el nombre del paciente");
            txtNombre.requestFocusInWindow();
            return;
        }


        // Obtener datos de los campos del formulario
        var nombrePaciente = txtNombre.getText().trim();
        var apellidoPaciente = txtApellido.getText().trim();
        var emailPaciente = txtEmail.getText().trim();
        var direccionPaciente = txtDireccion.getText().trim();
        var fechaNacimientoPaciente = txtFechaNacimiento.getText().trim();
        var telefonoPaciente = txtTelefono.getText().trim();

        // Crear una nueva instancia de Paciente
        var paciente = new Paciente();
        paciente.setNombre(nombrePaciente);
        paciente.setApellido(apellidoPaciente);
        paciente.setEmail(emailPaciente);
        paciente.setDireccion(direccionPaciente);
        paciente.setTelefono(telefonoPaciente);

        // Si se ingresó una fecha, convertirla de String a LocalDate
        if (!fechaNacimientoPaciente.isEmpty()) {
            try {
            // Suponiendo que el formato es "yyyy-MM-dd"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                var fechaNacimiento = LocalDate.parse(fechaNacimientoPaciente, formatter);
                paciente.setFechaNacimiento(fechaNacimiento);
            } catch (Exception e) {
                mostrarMensaje("Fecha de nacimiento inválida. Utilice el formato yyyy-MM-dd");
                txtFechaNacimiento.requestFocusInWindow();
                return;
            }
        }

        // Guardar el paciente usando el servicio de Spring Boot
        pacienteService.guardarPaciente(paciente);
        mostrarMensaje("Paciente agregado con éxito");

        // Actualizar la tabla y limpiar el formulario
        listarPacientes();
        limpiarFormulario();
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
  
    private void cargarPacienteSeleccionado() {
        //Los indices inician en 0
        var renglon = tablePacientes.getSelectedRow();
        if (renglon != -1) { //Rregresa -1 si no hay seleccionado nada
            String idPaciente = tablePacientes.getModel().getValueAt(renglon, 0).toString();
            txtIdPaciente.setText(idPaciente);
            String nombrePaciente = tablePacientes.getModel().getValueAt(renglon, 1).toString();
            txtNombre.setText(nombrePaciente);
            String apellidoPaciente = tablePacientes.getModel().getValueAt(renglon, 2).toString();
            txtApellido.setText(apellidoPaciente);
            String emailPaciente = tablePacientes.getModel().getValueAt(renglon, 3).toString();
            txtEmail.setText(emailPaciente);
            String direccionPaciente = tablePacientes.getModel().getValueAt(renglon, 4).toString();
            txtDireccion.setText(direccionPaciente);
            String fechaNacimientoPaciente = tablePacientes.getModel().getValueAt(renglon, 5).toString();
            txtFechaNacimiento.setText(fechaNacimientoPaciente);
            String telefonoPaciente = tablePacientes.getModel().getValueAt(renglon, 6).toString();
            txtTelefono.setText(telefonoPaciente);
        }
    }

    private void actualizarPaciente() {
        if (this.txtIdPaciente.getText().equals("")) {
            mostrarMensaje("Debe seleccionar un paciente");
        }
        else{
            //Verificamos que el nombre del paciente no sea nulo
            if (txtNombre.getText().equals("")) {
                mostrarMensaje("Debe completar el nombre del paciente...");
                txtNombre.requestFocusInWindow();
                return;
            }
            //Llenamos el objeto de libro a actualizar
            long idPaciente = Long.parseLong(txtIdPaciente.getText());
            var nombrePaciente = txtNombre.getText();
            var apellidoPaciente = txtApellido.getText();
            var emailPaciente = txtEmail.getText();
            var direccionPaciente = txtDireccion.getText();
            var telefonoPaciente = txtTelefono.getText();
            var fechaNacimientoPaciente = txtFechaNacimiento.getText();
            var paciente = new Paciente();
            paciente.setIdPaciente(idPaciente);
            paciente.setNombre(nombrePaciente);
            paciente.setApellido(apellidoPaciente);
            paciente.setEmail(emailPaciente);
            paciente.setDireccion(direccionPaciente);
            paciente.setTelefono(telefonoPaciente);
            
            // Si se ingresó una fecha, convertirla de String a LocalDate
            if (!fechaNacimientoPaciente.isEmpty()) {
             try {
                // Suponiendo que el formato es "yyyy-MM-dd"
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    var fechaNacimiento = LocalDate.parse(fechaNacimientoPaciente, formatter);
                    paciente.setFechaNacimiento(fechaNacimiento);
                } catch (Exception e) {
                    mostrarMensaje("Fecha de nacimiento inválida. Utilice el formato yyyy-MM-dd");
                    txtFechaNacimiento.requestFocusInWindow();
                    return;
                }
            }

            pacienteService.guardarPaciente(paciente);
            mostrarMensaje("Paciente actualizado con éxito");
            limpiarFormulario();
            listarPacientes();

        }
    }
}

