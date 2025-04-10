package dev.cliniq.cliniq.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.cliniq.cliniq.Model.Cita;
import dev.cliniq.cliniq.Service.citaService;
import dev.cliniq.cliniq.Service.consultorioService;
import dev.cliniq.cliniq.Service.medicoService;
import dev.cliniq.cliniq.Service.pacienteService;


@Component
public class CitasPanel extends JPanel {
    @Autowired
    private citaService citaService;

    @Autowired
    private pacienteService pacienteService;

    @Autowired
    private medicoService medicoService;

    @Autowired
    private consultorioService consultorioService;

    // Colores de la aplicación
    private final Color COLOR_PRIMARY = new Color(0, 158, 188); // Cyan
    private final Color COLOR_SECONDARY = new Color(220, 249, 255); // Cyan muy claro
    private final Color COLOR_BACKGROUND = Color.WHITE;
    private final Color COLOR_TEXT = new Color(30, 30, 30);
    private final Color COLOR_BUTTON = new Color(0, 188, 212); // Cyan más claro
    
    private JTextField txtBuscar;
    private JTable tableCitas;
    private DefaultTableModel tableModelCitas;
    private JTextField txtIdCita;
    private JTextField txtPaciente;
    private JTextField txtMedico;
    private JSpinner spinnerFecha;
    private JComboBox<String> comboEstado;
    private JTextField txtConsultorio;
    private JButton btnGuardar;
    private JButton btnLimpiar;

    @Autowired
    public CitasPanel(citaService citaServicio, pacienteService pacienteService, medicoService medicoService, consultorioService consultorioService) {
        this.citaService = citaServicio;
        this.pacienteService = pacienteService;
        this.medicoService = medicoService;
        this.consultorioService = consultorioService;
        initComponents();
        configurarEventos();
        listarCitas();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(COLOR_BACKGROUND); // Fondo blanco

        // Panel superior (búsqueda)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(COLOR_BACKGROUND);
        JLabel lblBuscar = new JLabel("Citas");
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
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModelCitas);
                tableCitas.setRowSorter(sorter);
                
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
        
        // Tabla de citas (lado izquierdo)
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(COLOR_BACKGROUND);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
        
        // Crear modelo de tabla
        this.tableModelCitas = new DefaultTableModel(0, 6);
        String[] columnas = {"ID", "Paciente", "Médico", "Fecha", "Consultorio", "Estado"};
        tableModelCitas.setColumnIdentifiers(columnas);

        //Inicializar la tabla
        this.tableCitas = new JTable(tableModelCitas);
        // DefaultTableModel tableModel = new DefaultTableModel();
        // tableModel.addColumn("ID");
        // tableModel.addColumn("Paciente");
        // tableModel.addColumn("Médico");
        // tableModel.addColumn("Fecha/Hora");
        // tableModel.addColumn("Consultorio");
        // tableModel.addColumn("Estado");
        
        tableCitas = new JTable(tableModelCitas);
        tableCitas.setRowHeight(25);
        tableCitas.getTableHeader().setBackground(COLOR_PRIMARY);
        tableCitas.getTableHeader().setForeground(COLOR_BACKGROUND);
        tableCitas.setSelectionBackground(COLOR_SECONDARY);
        tableCitas.setSelectionForeground(COLOR_TEXT);
        tableCitas.setGridColor(new Color(240, 240, 240));
        tableCitas.setBorder(BorderFactory.createLineBorder(COLOR_SECONDARY));
        
        JScrollPane scrollPane = new JScrollPane(tableCitas);
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
        JPanel idCitaPanel = createFormField("ID Citas", txtIdCita = new JTextField());
        txtIdCita.setEditable(false);
        JPanel pacientePanel = createFormField("Paciente", txtPaciente = new JTextField());
        JPanel medicoPanel = createFormField("Médico", txtMedico = new JTextField());
       
        spinnerFecha = new JSpinner(new SpinnerDateModel());
        spinnerFecha.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy");
        spinnerFecha.setEditor(dateEditor);
        JPanel fechaPanel = createFormField("Fecha", spinnerFecha);

        JPanel consultorioPanel = createFormField("Consultorio", txtConsultorio = new JTextField());
        
        // Panel de estado (radio buttons)
        JPanel estadoPanel = new JPanel();
        estadoPanel.setLayout(new BoxLayout(estadoPanel, BoxLayout.X_AXIS));
        estadoPanel.setBackground(COLOR_BACKGROUND);
        
        JLabel lblEstado = new JLabel("Estado");
        lblEstado.setForeground(COLOR_TEXT);
        lblEstado.setPreferredSize(new Dimension(150, 25));
        lblEstado.setMaximumSize(new Dimension(150, 25));
        
        JPanel comboPanel = createFormField("Estado", comboEstado = new JComboBox<>());
        comboEstado.addItem("Confirmado");
        comboEstado.addItem("Pendiente");
        comboEstado.setBackground(COLOR_BACKGROUND);
        comboEstado.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        estadoPanel.add(comboPanel);
        
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
        formPanel.add(idCitaPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(pacientePanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(medicoPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(fechaPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(consultorioPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(estadoPanel);
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
    
    private JPanel createFormField(String labelText, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(COLOR_BACKGROUND);
        
        JLabel label = new JLabel(labelText);
        label.setForeground(COLOR_TEXT);
        label.setPreferredSize(new Dimension(150, 25));
        label.setMaximumSize(new Dimension(150, 25));
        
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
    
        if (component instanceof JTextField) {
            JTextField textField = (JTextField) component;
            textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARY),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
            ));
        } else if (component instanceof JSpinner) {
            JSpinner spinner = (JSpinner) component;
            spinner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARY),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
            ));
            JComponent editor = spinner.getEditor();
            if (editor instanceof JSpinner.DefaultEditor) {
                ((JSpinner.DefaultEditor) editor).getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 13));
            }
        } else if (component instanceof JComboBox) {
            component.setPreferredSize(new Dimension(component.getPreferredSize().width, 25));
            JComboBox<?> comboBox = (JComboBox<?>) component;
            comboBox.setBackground(COLOR_BACKGROUND);
            comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        } 
        
        panel.add(label);
        panel.add(component);
        
        return panel;
    }
    
    private void limpiarFormulario() {
        txtIdCita.setText("");
        txtPaciente.setText("");
        txtMedico.setText("");
        spinnerFecha.setValue(new Date());
        txtConsultorio.setText("");
        comboEstado.setSelectedIndex(0);
    }

    private void listarCitas() {
        tableModelCitas.setRowCount(0);
        citaService.listarCitas().forEach(cita -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Object[] fila = {
                cita.getIdCita(),
                cita.getPaciente().getNombre(),
                cita.getMedico().getNombre(),
                cita.getFecha().format(formatter),
                cita.getConsultorio().getNumero(),
                cita.getEstado()
            };
            tableModelCitas.addRow(fila);
        });
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(e -> {
            if (txtIdCita.getText().isEmpty()) {
                agregarCita();
            } else {
                actualizarCita();
            }
        });

        tableCitas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarCitasSeleccionadas();
            }
        });
    }   

    private void agregarCita() {
        try {
            Long idPaciente = Long.parseLong(txtPaciente.getText());
            Long idMedico = Long.parseLong(txtMedico.getText());
            Long idConsultorio = Long.parseLong(txtConsultorio.getText());
            Date fechaDate = (Date)spinnerFecha.getValue();
            LocalDate fecha = fechaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String estado = (String) comboEstado.getSelectedItem();

            Cita nuevaCita = citaService.guardarCita(
                idPaciente, 
                idMedico, 
                idConsultorio, 
                fecha, 
                estado
            );

            listarCitas();
            limpiarFormulario();
            mostrarMensaje("Cita agregada con éxito");
        } catch (Exception e) {
            mostrarMensaje("Error al agregar la cita " + e.getMessage());
        }
    }

    private void actualizarCita() {
        try {
            Long idCita = Long.parseLong(txtIdCita.getText());
            Cita citaExistente = citaService.buscarCitaPorId(idCita);

            //Actualizar campos
            Date fechaDate = (Date)spinnerFecha.getValue();
            citaExistente.setFecha(fechaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            citaExistente.setEstado((String) comboEstado.getSelectedItem());
            citaService.guardarCita(citaExistente);
            listarCitas();
            mostrarMensaje("Cita actualizada con éxito");
        } catch (Exception e) {
            mostrarMensaje("Error al actualizar la cita" + e.getMessage());
        }
    }

    private void cargarCitasSeleccionadas() {
        int fila = tableCitas.getSelectedRow();
        if (fila >= 0) {
            Long idCita = (Long) tableModelCitas.getValueAt(fila, 0);
            Cita cita = citaService.buscarCitaPorId(idCita);

            txtIdCita.setText(idCita.toString());
            txtPaciente.setText(cita.getPaciente().getNombre());
            txtMedico.setText(cita.getMedico().getNombre());
            spinnerFecha.setValue(cita.getFecha());
            comboEstado.setSelectedItem(cita.getEstado());

            Date fecha = Date.from(cita.getFecha().atStartOfDay(ZoneId.systemDefault()).toInstant());
            spinnerFecha.setValue(fecha);
            comboEstado.setSelectedItem(cita.getEstado());
        }
    }
}



