package dev.cliniq.cliniq.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import dev.cliniq.cliniq.Service.consultorioService;

@Component
public class ConsultorioPanel extends JPanel {
    @Autowired
    private consultorioService consultorioService;
    
    // Colores de la aplicación
    private final Color COLOR_PRIMARY = new Color(0, 158, 188); // Cyan
    private final Color COLOR_SECONDARY = new Color(220, 249, 255); // Cyan muy claro
    private final Color COLOR_BACKGROUND = Color.WHITE;
    private final Color COLOR_TEXT = new Color(30, 30, 30);
    private final Color COLOR_BUTTON = new Color(0, 188, 212); // Cyan más claro
    
    private JTextField txtBuscar;
    private JTable tableConsultorios;
    private DefaultTableModel tableModel;
    private JTextField txtIdConsultorio;
    private JTextField txtNumero;
    private JTextField txtUbicacion;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnBuscar;

    // Constructor sin argumentos requerido para Spring
    @Autowired
    public ConsultorioPanel(consultorioService consultorioService) {
        this.consultorioService = consultorioService;
        initComponents();
        listarConsultorios();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(COLOR_BACKGROUND); // Fondo blanco

        // Panel superior (búsqueda)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(COLOR_BACKGROUND);
        JLabel lblBuscar = new JLabel("Consultorios");
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
        
        // Tabla de consultorios (lado izquierdo)
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(COLOR_BACKGROUND);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
        
        // Crear modelo de tabla
        this.tableModel = new DefaultTableModel(0, 3);
        String[] columnas = {"ID", "Número", "Ubicación"};
        tableModel.setColumnIdentifiers(columnas);

        //Instanciar la tabla
        this.tableConsultorios = new JTable(tableModel);
        
        tableConsultorios = new JTable(tableModel);
        tableConsultorios.setRowHeight(25);
        tableConsultorios.getTableHeader().setBackground(COLOR_PRIMARY);
        tableConsultorios.getTableHeader().setForeground(COLOR_BACKGROUND);
        tableConsultorios.setSelectionBackground(COLOR_SECONDARY);
        tableConsultorios.setSelectionForeground(COLOR_TEXT);
        tableConsultorios.setGridColor(new Color(240, 240, 240));
        tableConsultorios.setBorder(BorderFactory.createLineBorder(COLOR_SECONDARY));
        
        // Agregar evento de selección de fila en la tabla
        tableConsultorios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tableConsultorios.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    seleccionarConsultorio(filaSeleccionada);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tableConsultorios);
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
        JPanel idPanel = createFormField("ID Consultorio", txtIdConsultorio = new JTextField());
        JPanel numeroPanel = createFormField("Número", txtNumero = new JTextField());
        JPanel ubicacionPanel = createFormField("Ubicación", txtUbicacion = new JTextField());
        
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
        formPanel.add(numeroPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(ubicacionPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(buttonsPanel);
        formPanel.add(Box.createVerticalGlue());
        
        centerPanel.add(tablePanel);
        centerPanel.add(formPanel);
        
        add(centerPanel, BorderLayout.CENTER);
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
    
    private void seleccionarConsultorio(int fila) {
        if (fila >= 0 && fila < tableConsultorios.getRowCount()) {
            txtIdConsultorio.setText(tableConsultorios.getValueAt(fila, 0).toString());
            txtNumero.setText(tableConsultorios.getValueAt(fila, 1).toString());
            txtUbicacion.setText(tableConsultorios.getValueAt(fila, 2).toString());
        }
    }
    
    // Método para limpiar el formulario
    public void limpiarFormulario() {
        txtIdConsultorio.setText("");
        txtNumero.setText("");
        txtUbicacion.setText("");
    }

    private void listarConsultorios() {
        tableModel.setRowCount(0);

        var consultorios = consultorioService.listarConsultorios();
        consultorios.forEach((consultorio) -> {
            Object[] renglonConsultorio = {
                consultorio.getIdConsultorio(),
                consultorio.getNumero(),
                consultorio.getUbicacion()
            };

            this.tableModel.addRow(renglonConsultorio);
        });
    }
}
