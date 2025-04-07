package dev.cliniq.cliniq.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.cliniq.cliniq.Model.Cita;
import dev.cliniq.cliniq.Model.Consultorio;
import dev.cliniq.cliniq.Model.Medico;
import dev.cliniq.cliniq.Model.Paciente;
import dev.cliniq.cliniq.Service.citaService;
import dev.cliniq.cliniq.Service.pacienteService;
import dev.cliniq.cliniq.Service.medicoService;
import dev.cliniq.cliniq.Service.consultorioService;



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
    private JTextField txtFecha;
    private JTextField txtConsultorio;
    private JRadioButton radioBtnConfirmado;
    private JRadioButton radioBtnPendiente;
    private JButton btnGuardar;
    private JButton btnLimpiar;

    @Autowired
    public CitasPanel(citaService citaService) {
        this.citaService = citaService;
        initComponents();
        btnGuardar.addActionListener(e -> agregarCita()); 
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
        String[] columnas = {"ID", "ID Paciente", "ID Médico", "Fecha/Hora", "ID Consultorio", "Estado"};
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
        JPanel pacientePanel = createFormField("ID Paciente", txtPaciente = new JTextField());
        JPanel medicoPanel = createFormField("ID Médico", txtMedico = new JTextField());
        JPanel fechaPanel = createFormField("Fecha", txtFecha = new JTextField());
        JPanel consultorioPanel = createFormField("ID Consultorio", txtConsultorio = new JTextField());
        
        // Panel de estado (radio buttons)
        JPanel estadoPanel = new JPanel();
        estadoPanel.setLayout(new BoxLayout(estadoPanel, BoxLayout.X_AXIS));
        estadoPanel.setBackground(COLOR_BACKGROUND);
        
        JLabel lblEstado = new JLabel("Estado");
        lblEstado.setForeground(COLOR_TEXT);
        lblEstado.setPreferredSize(new Dimension(150, 25));
        lblEstado.setMaximumSize(new Dimension(150, 25));
        
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.setBackground(COLOR_BACKGROUND);
        
        radioBtnConfirmado = new JRadioButton("confirmado");
        radioBtnConfirmado.setBackground(COLOR_PRIMARY);
        radioBtnConfirmado.setForeground(COLOR_BACKGROUND);
        radioBtnConfirmado.setFocusPainted(false);
        radioBtnConfirmado.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        radioBtnPendiente = new JRadioButton("pendiente");
        radioBtnPendiente.setBackground(COLOR_BUTTON);
        radioBtnPendiente.setForeground(COLOR_BACKGROUND);
        radioBtnPendiente.setFocusPainted(false);
        radioBtnPendiente.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        ButtonGroup estadoGroup = new ButtonGroup();
        estadoGroup.add(radioBtnConfirmado);
        estadoGroup.add(radioBtnPendiente);
        
        radioPanel.add(radioBtnConfirmado);
        radioPanel.add(radioBtnPendiente);
        
        estadoPanel.add(lblEstado);
        estadoPanel.add(radioPanel);
        
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
        txtIdCita.setText("");
        txtPaciente.setText("");
        txtMedico.setText("");
        txtFecha.setText("");
        txtConsultorio.setText("");
        radioBtnConfirmado.setSelected(false);
        radioBtnPendiente.setSelected(false);
    }

    private void agregarCita() {
        //Leer los datos del formulario
        if(txtPaciente.getText().isBlank() || txtMedico.getText().isBlank()) {
            mostrarMensaje("El paciente y medico es obligatorio");  
            txtPaciente.requestFocusInWindow();
            return;
        }

        try {
            var idPaciente = Long.parseLong(txtPaciente.getText());
            var idMedico = Long.parseLong(txtMedico.getText());
            var idConsultorio = Long.parseLong(txtConsultorio.getText());

            Paciente paciente = pacienteService.buscarPacientePorId(idPaciente);
            Medico medico = medicoService.buscarMedicoPorId(idMedico);
            Consultorio consultorio = consultorioService.buscarConsultorioPorId(idConsultorio);

            if (paciente == null) {
                mostrarMensaje("El paciente no existe");
                return;
            }
            if (medico == null) {
                mostrarMensaje("El médico no existe");
                return;
            }
            if (consultorio == null) {
                mostrarMensaje("El consultorio no existe");
                return;
            }

            String estado;

            if (estado.isEmpty()) {
                mostrarMensaje("Seleccione un estado para la cita.");
                return;
            }

            if(radioBtnConfirmado.isSelected()) {
                estado = radioBtnConfirmado.getText();
            } else if (radioBtnPendiente.isSelected()) {
                estado = radioBtnPendiente.getText();
            } else {
                estado = "";
            }

            //Validar formato de fecha hora 
            if(!validarFormatoFechaHora(txtFecha.getText())) {
                mostrarMensaje("Formato de fecha inválido. Use dd/MM/yyyy");
                txtFecha.requestFocusInWindow();
                return;
            }

            DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaHora = LocalDate.parse(txtFecha.getText(), fechaFormatter);

            //Guardar Cita
            var cita = new Cita();
            cita.setFechaHora(fechaHora);
            cita.setEstado(estado);
            cita.setPaciente(pacienteService.buscarPacientePorId(idPaciente));
            cita.setMedico(medicoService.buscarMedicoPorId(idMedico));
            cita.setConsultorio(consultorioService.buscarConsultorioPorId(idConsultorio));
            this.citaService.guardarCita(cita);

            mostrarMensaje("Cita guardada con éxito");
            limpiarFormulario();
            listarCitas();
        } catch (NumberFormatException e) {
            mostrarMensaje("Los ID's deben ser números validos");
        } catch (Exception e) { //Captura cualquier excepción
            e.printStackTrace();
            mostrarMensaje("Error: " + e.getMessage());
        }

    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private boolean validarFormatoFechaHora(String fecha) {
        try {
        // Ejemplo: Validar formato "dd/MM/yyyy" y "HH:mm"
        DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate.parse(fecha, fechaFormatter);      
        return true;
    } catch (DateTimeParseException e) {
        return false;
    }
    }

    private void listarCitas() {
        tableModelCitas.setRowCount(0);

        var citas = citaService.listarCitas();
        citas.forEach((cita) -> {
            Object[] renglonCita = {
                cita.getIdCita(),
                cita.getPaciente().getIdPaciente(),
                cita.getMedico().getIdMedico(),
                cita.getFechaHora(),
                cita.getConsultorio().getIdConsultorio(),
                cita.getEstado()
            };

            this.tableModelCitas.addRow(renglonCita);
        });
    }
}
