package dev.cliniq.cliniq.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.cliniq.cliniq.Model.Medico;
import dev.cliniq.cliniq.Service.medicoService;

@Component
public class MedicosPanel extends JPanel {
    @Autowired
    private medicoService medicoService;

    // Colores de la aplicación
    private final Color COLOR_PRIMARY = new Color(0, 158, 188); // Cyan
    private final Color COLOR_SECONDARY = new Color(220, 249, 255); // Cyan muy claro
    private final Color COLOR_BACKGROUND = Color.WHITE;
    private final Color COLOR_TEXT = new Color(30, 30, 30);
    private final Color COLOR_BUTTON = new Color(0, 188, 212); // Cyan más claro
    
    private JTextField txtBuscar;
    private JTable tableMedicos;
    private DefaultTableModel tableModelMedicos;
    private JTextField txtIdMedico;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JTextField txtEspecialidad;
    private JButton btnGuardar;
    private JButton btnLimpiar;

    @Autowired
    public MedicosPanel(medicoService medicoServicio) {
        this.medicoService = medicoServicio;
        initComponents();
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idTexto = txtIdMedico.getText().trim();
                if (idTexto.isEmpty()) {
                    // Lógica para agregar un nuevo Medico
                    agregarMedico();
                } else {
                    // Lógica para actualizar un Medico existente
                    actualizarMedico();
                }
            }
        });
        tableMedicos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarMedicoSeleccionado();
            }
        });
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(COLOR_BACKGROUND); // Fondo blanco

        // Panel superior (búsqueda)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(COLOR_BACKGROUND);
        JLabel lblBuscar = new JLabel("Médicos");
        lblBuscar.setForeground(COLOR_TEXT);
        txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
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
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModelMedicos);
        tableMedicos.setRowSorter(sorter);
        
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
        
        // Tabla de médicos (lado izquierdo)
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(COLOR_BACKGROUND);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
        
        // Crear modelo de tabla
        this.tableModelMedicos = new DefaultTableModel(0, 6);
        String[] columnas = {"ID", "Nombre", "Apellido", "Email", "Teléfono", "Especialidad"};
        tableModelMedicos.setColumnIdentifiers(columnas);

        //Instanciar la tabla
        this.tableMedicos = new JTable(tableModelMedicos);

        // DefaultTableModel tableModel = new DefaultTableModel();

        // tableModel.addColumn("ID");
        // tableModel.addColumn("Nombre");
        // tableModel.addColumn("Apellido");
        // tableModel.addColumn("Email");
        // tableModel.addColumn("Teléfono");
        // tableModel.addColumn("Especialidad");
        
        tableMedicos = new JTable(tableModelMedicos);
        tableMedicos.setRowHeight(25);
        tableMedicos.getTableHeader().setBackground(COLOR_PRIMARY);
        tableMedicos.getTableHeader().setForeground(COLOR_BACKGROUND);
        tableMedicos.setSelectionBackground(COLOR_SECONDARY);
        tableMedicos.setSelectionForeground(COLOR_TEXT);
        tableMedicos.setGridColor(new Color(240, 240, 240));
        tableMedicos.setBorder(BorderFactory.createLineBorder(COLOR_SECONDARY));
        
        // Agregar evento de selección de fila en la tabla
        tableMedicos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tableMedicos.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    seleccionarMedico(filaSeleccionada);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableMedicos);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY, 1));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        //popupMenu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Eliminar");
        popupMenu.add(deleteItem);

        tableMedicos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = tableMedicos.rowAtPoint(e.getPoint());
                    tableMedicos.setRowSelectionInterval(row, row);
                    popupMenu.show(tableMedicos, e.getX(), e.getY());                 
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
                int selectedRow = tableMedicos.getSelectedRow();
                if (selectedRow >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar este Medico?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        Long idMedico = (Long) tableModelMedicos.getValueAt(selectedRow, 0);
                        Medico Medico = medicoService.buscarMedicoPorId(idMedico);
                        if (Medico != null) {
                            medicoService.eliminarMedico(Medico);
                            tableModelMedicos.removeRow(selectedRow);
                            JOptionPane.showMessageDialog(null, "Medico eliminado con éxito.");
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontró el Medico.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un Medico para eliminar.");
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
        JPanel idMedicoPanel = createFormField("ID Médico", txtIdMedico = new JTextField());
        txtIdMedico.setEditable(false);
        JPanel nombrePanel = createFormField("Nombre", txtNombre = new JTextField());
        JPanel apellidoPanel = createFormField("Apellido", txtApellido = new JTextField());
        JPanel emailPanel = createFormField("Email", txtEmail = new JTextField());
        JPanel telefonoPanel = createFormField("Teléfono", txtTelefono = new JTextField());
        JPanel especialidadPanel = createFormField("Especialidad", txtEspecialidad = new JTextField());
        
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
        formPanel.add(idMedicoPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(nombrePanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(apellidoPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(emailPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(telefonoPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(especialidadPanel);
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

        listarMedicos();
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

    private void seleccionarMedico(int filaSeleccionada) {
        Long idMedico = (Long) tableModelMedicos.getValueAt(filaSeleccionada, 0);
        
        // Buscar el Medico en la base de datos
        Medico medico = medicoService.buscarMedicoPorId(idMedico);
        
        if (medico != null) {
            // Llenar el formulario con los datos del Medico
            txtIdMedico.setText(medico.getIdMedico().toString());
            txtNombre.setText(medico.getNombre());
            txtApellido.setText(medico.getApellido());
            txtEmail.setText(medico.getEmail());
            txtTelefono.setText(medico.getTelefono());
            txtEspecialidad.setText(medico.getEspecialidad());
        }
    }
    
    private void limpiarFormulario() {
        txtIdMedico.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtEspecialidad.setText("");
    }

    private void listarMedicos() {
        limpiarFormulario();
        //Obtener Medicos de la base de datos
        var medicos = medicoService.listarMedicos();
        medicos.forEach((medico) -> {
            Object[] renglonMedico = {
                medico.getIdMedico(),
                medico.getNombre(),
                medico.getApellido(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getEspecialidad()
            };

            this.tableModelMedicos.addRow(renglonMedico);
        });

    }

    private void agregarMedico() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarMensaje("Debe completar el nombre del Medico");
            txtNombre.requestFocusInWindow();
            return;
        }


        // Obtener datos de los campos del formulario
        var nombreMedico = txtNombre.getText().trim();
        var apellidoMedico = txtApellido.getText().trim();
        var emailMedico = txtEmail.getText().trim();
        var telefonoMedico = txtTelefono.getText().trim();
        var especialidadMedico = txtEspecialidad.getText().trim();

        // Crear una nueva instancia de Medico
        var medico = new Medico();
        medico.setNombre(nombreMedico);
        medico.setApellido(apellidoMedico);
        medico.setEmail(emailMedico);
        medico.setTelefono(telefonoMedico);
        medico.setEspecialidad(especialidadMedico);


        // Guardar el Medico usando el servicio de Spring Boot
        medicoService.guardarMedico(medico);
        mostrarMensaje("Medico agregado con éxito");

        // Actualizar la tabla y limpiar el formulario
        listarMedicos();
        limpiarFormulario();
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void actualizarMedico() {
        if (this.txtIdMedico.getText().equals("")) {
            mostrarMensaje("Debe seleccionar un Medico");
        }
        else{
            //Verificamos que el nombre del Medico no sea nulo
            if (txtNombre.getText().equals("")) {
                mostrarMensaje("Debe completar el nombre del Medico...");
                txtNombre.requestFocusInWindow();
                return;
            }
            //Llenamos el objeto de libro a actualizar
            long idMedico = Long.parseLong(txtIdMedico.getText());
            var nombreMedico = txtNombre.getText();
            var apellidoMedico = txtApellido.getText();
            var emailMedico = txtEmail.getText();
            var telefonoMedico = txtTelefono.getText();
            var especialidadMedico = txtEspecialidad.getText();
            var Medico = new Medico();
            Medico.setIdMedico(idMedico);
            Medico.setNombre(nombreMedico);
            Medico.setApellido(apellidoMedico);
            Medico.setEmail(emailMedico);
            Medico.setTelefono(telefonoMedico);
            Medico.setEspecialidad(especialidadMedico);
            

            medicoService.guardarMedico(Medico);
            mostrarMensaje("Medico actualizado con éxito");
            limpiarFormulario();
            listarMedicos();

        }
    }
    
    private void cargarMedicoSeleccionado() {
        //Los indices inician en 0
        var renglon = tableMedicos.getSelectedRow();
        if (renglon != -1) { //Rregresa -1 si no hay seleccionado nada
            String idMedico = tableMedicos.getModel().getValueAt(renglon, 0).toString();
            txtIdMedico.setText(idMedico);
            String nombreMedico = tableMedicos.getModel().getValueAt(renglon, 1).toString();
            txtNombre.setText(nombreMedico);
            String apellidoMedico = tableMedicos.getModel().getValueAt(renglon, 2).toString();
            txtApellido.setText(apellidoMedico);
            String emailMedico = tableMedicos.getModel().getValueAt(renglon, 3).toString();
            txtEmail.setText(emailMedico);
            String telefonoMedico = tableMedicos.getModel().getValueAt(renglon, 4).toString();
            txtTelefono.setText(telefonoMedico);
            String especialidadMedico = tableMedicos.getModel().getValueAt(renglon, 5).toString();
            txtEspecialidad.setText(especialidadMedico);
        }
    }
}
