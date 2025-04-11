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

import dev.cliniq.cliniq.Model.Consultorio;
import dev.cliniq.cliniq.Service.consultorioService;

@Component
public class ConsultoriosPanel extends JPanel {
    @Autowired
    private consultorioService consultorioService;

    // Colores de la aplicación
    private final Color COLOR_PRIMARY = new Color(0, 158, 188);
    private final Color COLOR_SECONDARY = new Color(220, 249, 255);
    private final Color COLOR_BACKGROUND = Color.WHITE;
    private final Color COLOR_TEXT = new Color(30, 30, 30);
    private final Color COLOR_BUTTON = new Color(0, 188, 212);
    
    private JTextField txtBuscar;
    private JTable tableConsultorios;
    private DefaultTableModel tableModelConsultorios;
    private JTextField txtIdConsultorio;
    private JTextField txtNumero;
    private JTextField txtUbicacion;
    private JButton btnGuardar;
    private JButton btnLimpiar;

    @Autowired
    public ConsultoriosPanel(consultorioService consultorioService) {
        this.consultorioService = consultorioService;
        initComponents();
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtIdConsultorio.getText().trim().isEmpty()) {
                    agregarConsultorio();
                } else {
                    actualizarConsultorio();
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(COLOR_BACKGROUND);

        // Panel superior (búsqueda)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(COLOR_BACKGROUND);
        JLabel lblBuscar = new JLabel("Consultorios");
        lblBuscar.setForeground(COLOR_TEXT);
        txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(COLOR_BUTTON);
        btnBuscar.setForeground(COLOR_BACKGROUND);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnBuscar.addActionListener(e -> {
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModelConsultorios);
            tableConsultorios.setRowSorter(sorter);
            if (txtBuscar.getText().trim().isEmpty()) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + txtBuscar.getText().trim()));
            }
        });
        
        searchPanel.add(lblBuscar);
        searchPanel.add(txtBuscar);
        searchPanel.add(btnBuscar);
        
        add(searchPanel, BorderLayout.NORTH);

        // Panel central (tabla y formulario)
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setBackground(COLOR_BACKGROUND);
        
        // Tabla de consultorios
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(COLOR_BACKGROUND);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 5),
            BorderFactory.createLineBorder(COLOR_PRIMARY, 1)  // Borde cyan
        ));
        
        String[] columnas = {"ID", "Número", "Ubicación"};
        tableModelConsultorios = new DefaultTableModel(0, 3);
        tableModelConsultorios.setColumnIdentifiers(columnas);
        
        tableConsultorios = new JTable(tableModelConsultorios);
        tableConsultorios.setRowHeight(25);
        tableConsultorios.getTableHeader().setBackground(COLOR_PRIMARY);
        tableConsultorios.getTableHeader().setForeground(COLOR_BACKGROUND);
        tableConsultorios.setSelectionBackground(COLOR_SECONDARY);
        tableConsultorios.setSelectionForeground(COLOR_TEXT);
        tableConsultorios.setGridColor(new Color(240, 240, 240));
        tableConsultorios.setBorder(BorderFactory.createLineBorder(COLOR_SECONDARY));
        
        JScrollPane scrollPane = new JScrollPane(tableConsultorios);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY, 1));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Menú contextual para eliminar (actualizado)
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Eliminar");
        popupMenu.add(deleteItem);

        tableConsultorios.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = tableConsultorios.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < tableConsultorios.getRowCount()) {
                        tableConsultorios.setRowSelectionInterval(row, row);
                        popupMenu.show(tableConsultorios, e.getX(), e.getY());
                    }
                }
            }
        });

        deleteItem.addActionListener(e -> eliminarConsultorio());

            

        // Configuración del formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(COLOR_BACKGROUND);  // Fondo blanco
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 5, 10, 10),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARY, 1),  // Borde cyan
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
            )
        ));
        
        // Campos del formulario
        JPanel idPanel = createFormField("ID Consultorio", txtIdConsultorio = new JTextField());
        txtIdConsultorio.setEditable(false);
        JPanel numeroPanel = createFormField("Número*", txtNumero = new JTextField());
        JPanel ubicacionPanel = createFormField("Ubicación*", txtUbicacion = new JTextField());
        
        // Panel de botones
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBackground(COLOR_BACKGROUND);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(COLOR_PRIMARY);
        btnGuardar.setForeground(COLOR_BACKGROUND);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBackground(COLOR_BUTTON);
        btnLimpiar.setForeground(COLOR_BACKGROUND);
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efectos hover
        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnGuardar.setBackground(new Color(0, 140, 168));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btnGuardar.setBackground(COLOR_PRIMARY);
            }
        });

        btnLimpiar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLimpiar.setBackground(new Color(0, 163, 187));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btnLimpiar.setBackground(COLOR_BUTTON);
            }
        });

        buttonsPanel.add(btnGuardar);
        buttonsPanel.add(btnLimpiar);
        
        formPanel.add(idPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(numeroPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(ubicacionPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(buttonsPanel);
        
        centerPanel.add(tablePanel);
        centerPanel.add(formPanel);
        
        add(centerPanel, BorderLayout.CENTER);
        
        listarConsultorios();
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

    private void listarConsultorios() {
        tableModelConsultorios.setRowCount(0);
        consultorioService.listarConsultorios().forEach(consultorio -> {
            tableModelConsultorios.addRow(new Object[]{
                consultorio.getIdConsultorio(),
                consultorio.getNumero(),
                consultorio.getUbicacion()
            });
        });
    }

    private void agregarConsultorio() {
        String numero = txtNumero.getText().trim();
        String ubicacion = txtUbicacion.getText().trim();
        
        if (numero.isEmpty() || ubicacion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos marcados con (*) son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Consultorio nuevo = new Consultorio();
            nuevo.setNumero(Integer.parseInt(numero));
            nuevo.setUbicacion(ubicacion);
            
            consultorioService.guardarConsultorio(nuevo);
            listarConsultorios();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Consultorio agregado con éxito");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El número debe ser un valor entero válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarConsultorio() {
        String id = txtIdConsultorio.getText().trim();
        String numero = txtNumero.getText().trim();
        String ubicacion = txtUbicacion.getText().trim();
        
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un consultorio de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (numero.isEmpty() || ubicacion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos marcados con (*) son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Consultorio actualizado = new Consultorio();
            actualizado.setIdConsultorio(Long.parseLong(id));
            actualizado.setNumero(Integer.parseInt(numero));
            actualizado.setUbicacion(ubicacion);
            
            consultorioService.guardarConsultorio(actualizado);
            listarConsultorios();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Consultorio actualizado con éxito");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El número debe ser un valor entero válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarConsultorio() {
        int selectedRow = tableConsultorios.getSelectedRow();
    if (selectedRow >= 0) {
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro de eliminar este consultorio?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            int modelRow = tableConsultorios.convertRowIndexToModel(selectedRow);
            Long id = (Long) tableModelConsultorios.getValueAt(modelRow, 0);
            
            Consultorio consultorio = consultorioService.buscarConsultorioPorId(id);
            if (consultorio != null) {
                consultorioService.eliminarConsultorio(consultorio);
                listarConsultorios();
                JOptionPane.showMessageDialog(this, "Consultorio eliminado con éxito");
            } else {
                JOptionPane.showMessageDialog(this, "El consultorio no existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione un consultorio de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void cargarConsultorioSeleccionado() {
        int selectedRow = tableConsultorios.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = tableConsultorios.convertRowIndexToModel(selectedRow);
            txtIdConsultorio.setText(tableModelConsultorios.getValueAt(modelRow, 0).toString());
            txtNumero.setText(tableModelConsultorios.getValueAt(modelRow, 1).toString());
            txtUbicacion.setText(tableModelConsultorios.getValueAt(modelRow, 2).toString());
        }
    }

    private void limpiarFormulario() {
        txtIdConsultorio.setText("");
        txtNumero.setText("");
        txtUbicacion.setText("");
    }

    
}