package dev.cliniq.cliniq.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dev.cliniq.cliniq.Model.Cita;
import dev.cliniq.cliniq.Model.Factura;
import dev.cliniq.cliniq.Service.FacturaService;

@Component
public class FacturacionPanel extends JPanel {
    private JComboBox<String> comboEstado;
    private JSpinner spinnerFechaEmision;
    private JButton btnBuscar;
    private JTextField txtBuscar;
    private JTable tableFacturas;
    private DefaultTableModel tableModelFacturas;
    private JTextField txtIdFactura;
    private JTextField txtCita;
    private JTextField txtTotal;
    private JButton btnGuardar;
    private JButton btnLimpiar;

    @Autowired
    private FacturaService facturaService;

    // Colores de la aplicación
    private final Color COLOR_PRIMARY = new Color(0, 158, 188); // Cyan
    private final Color COLOR_SECONDARY = new Color(220, 249, 255); // Cyan muy claro
    private final Color COLOR_BACKGROUND = Color.WHITE;
    private final Color COLOR_TEXT = new Color(30, 30, 30);
    private final Color COLOR_BUTTON = new Color(0, 188, 212); // Cyan más claro

    @Autowired
    public FacturacionPanel(FacturaService facturaService) {
        this.facturaService = facturaService;
        initComponents();
        configurarEventos();
        listarFacturas(); // Cargar facturas al iniciar
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(COLOR_BACKGROUND);

        // Panel superior (búsqueda)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(COLOR_BACKGROUND);
        JLabel lblBuscar = new JLabel("Buscar Factura:");
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

        // Tabla de facturas (lado izquierdo)
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(COLOR_BACKGROUND);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));

        tableModelFacturas = new DefaultTableModel(0, 5);
        String[] columnas = {"ID", "Cita", "Fecha Emisión", "Total", "Estado"};
        tableModelFacturas.setColumnIdentifiers(columnas);
        tableFacturas = new JTable(tableModelFacturas);
        tableFacturas.setRowHeight(25);
        tableFacturas.getTableHeader().setBackground(COLOR_PRIMARY);
        tableFacturas.getTableHeader().setForeground(COLOR_BACKGROUND);
        tableFacturas.setSelectionBackground(COLOR_SECONDARY);
        tableFacturas.setSelectionForeground(COLOR_TEXT);
        tableFacturas.setGridColor(new Color(240, 240, 240));
        tableFacturas.setBorder(BorderFactory.createLineBorder(COLOR_SECONDARY));

        // PopupMenu para eliminar facturas
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Eliminar");
        popupMenu.add(deleteItem);

        tableFacturas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = tableFacturas.rowAtPoint(e.getPoint());
                    tableFacturas.setRowSelectionInterval(row, row);
                    popupMenu.show(tableFacturas, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mousePressed(e);
            }
        });

        deleteItem.addActionListener(e -> {
            int selectedRow = tableFacturas.getSelectedRow();
            if (selectedRow >= 0) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar esta factura?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    Long idFactura = (Long) tableModelFacturas.getValueAt(selectedRow, 0);
                    Factura factura = facturaService.buscarFacturaPorId(idFactura);
                    if (factura != null) {
                        facturaService.eliminarFactura(factura);
                        tableModelFacturas.removeRow(selectedRow);
                        limpiarFormulario();
                        mostrarMensaje("Factura eliminada con éxito.");
                    } else {
                        mostrarMensaje("No se encontró la factura.");
                    }
                }
            } else {
                mostrarMensaje("Seleccione una factura para eliminar.");
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableFacturas);
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
        JPanel idFacturaPanel = createFormField("ID Factura", txtIdFactura = new JTextField());
        txtIdFactura.setEditable(false); // ID no editable
        JPanel citaPanel = createFormField("Cita (ID)", txtCita = new JTextField());
        spinnerFechaEmision = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerFechaEmision, "dd/MM/yyyy");
        spinnerFechaEmision.setEditor(dateEditor);
        JPanel fechaEmisionPanel = createFormField("Fecha Emisión", spinnerFechaEmision);
        JPanel totalPanel = createFormField("Total", txtTotal = new JTextField());
        comboEstado = new JComboBox<>(new String[]{"Pagado", "Pendiente"});
        comboEstado.setBackground(COLOR_BACKGROUND);
        comboEstado.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JPanel estadoPanel = createFormField("Estado", comboEstado);

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
                btnGuardar.setBackground(new Color(0, 140, 168));
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
                btnLimpiar.setBackground(new Color(0, 163, 187));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnLimpiar.setBackground(COLOR_BUTTON);
            }
        });
        buttonsPanel.add(btnGuardar);
        buttonsPanel.add(btnLimpiar);

        // Añadir campos al formulario
        formPanel.add(idFacturaPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(citaPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(fechaEmisionPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(totalPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(estadoPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(buttonsPanel);
        formPanel.add(Box.createVerticalGlue());

        // Añadir tabla y formulario al panel central
        centerPanel.add(tablePanel);
        centerPanel.add(formPanel);
        add(centerPanel, BorderLayout.CENTER);
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

    private void configurarEventos() {
        btnGuardar.addActionListener(e -> {
            if (txtIdFactura.getText().isEmpty()) {
                agregarFactura();
            } else {
                actualizarFactura();
            }
        });

        btnLimpiar.addActionListener(e -> limpiarFormulario());

        tableFacturas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarFacturaSeleccionada();
            }
        });

        btnBuscar.addActionListener(e -> {
            String searchText = txtBuscar.getText().trim();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModelFacturas);
            tableFacturas.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        });
    }

    private void listarFacturas() {
        tableModelFacturas.setRowCount(0);
        facturaService.listarFacturas().forEach(factura -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Object[] fila = {
                factura.getIdFactura(),
                factura.getCita().getIdCita(),
                factura.getFechaEmision().format(formatter),
                factura.getTotal(),
                factura.getEstado()
            };
            tableModelFacturas.addRow(fila);
        });
    }

    private void agregarFactura() {
        try {
            if (txtCita.getText().isEmpty() || txtTotal.getText().isEmpty()) {
                mostrarMensaje("Por favor, complete todos los campos.");
                return;
            }
            Factura nuevaFactura = new Factura();
            Cita cita = new Cita();
            cita.setIdCita(Long.parseLong(txtCita.getText()));
            nuevaFactura.setCita(cita);
            Date date = (Date) spinnerFechaEmision.getValue();
            LocalDate fechaEmision = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            nuevaFactura.setFechaEmision(fechaEmision);
            nuevaFactura.setTotal(new BigDecimal(txtTotal.getText()));
            nuevaFactura.setEstado((String) comboEstado.getSelectedItem());
            facturaService.guardarFactura(nuevaFactura);
            listarFacturas();
            limpiarFormulario();
            mostrarMensaje("Factura agregada con éxito");
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: Verifique los datos numéricos.");
        } catch (Exception e) {
            mostrarMensaje("Error al agregar factura: " + e.getMessage());
        }
    }

    private void actualizarFactura() {
        try {
            if (txtIdFactura.getText().isEmpty() || txtCita.getText().isEmpty() || txtTotal.getText().isEmpty()) {
                mostrarMensaje("Por favor, complete todos los campos.");
                return;
            }
            Long idFactura = Long.parseLong(txtIdFactura.getText());
            Factura factura = facturaService.buscarFacturaPorId(idFactura);
            if (factura == null) {
                mostrarMensaje("Factura no encontrada.");
                return;
            }
            Cita cita = new Cita();
            cita.setIdCita(Long.parseLong(txtCita.getText()));
            factura.setCita(cita);
            Date date = (Date) spinnerFechaEmision.getValue();
            LocalDate fechaEmision = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            factura.setFechaEmision(fechaEmision);
            factura.setTotal(new BigDecimal(txtTotal.getText()));
            factura.setEstado((String) comboEstado.getSelectedItem());
            facturaService.guardarFactura(factura);
            listarFacturas();
            mostrarMensaje("Factura actualizada con éxito");
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: Verifique los datos numéricos.");
        } catch (Exception e) {
            mostrarMensaje("Error al actualizar factura: " + e.getMessage());
        }
    }

    private void cargarFacturaSeleccionada() {
        int fila = tableFacturas.getSelectedRow();
        if (fila >= 0) {
            Long idFactura = (Long) tableModelFacturas.getValueAt(fila, 0);
            Factura factura = facturaService.buscarFacturaPorId(idFactura);
            if (factura != null) {
                txtIdFactura.setText(String.valueOf(factura.getIdFactura()));
                txtCita.setText(String.valueOf(factura.getCita().getIdCita()));
                spinnerFechaEmision.setValue(Date.from(factura.getFechaEmision().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                txtTotal.setText(factura.getTotal().toString());
                comboEstado.setSelectedItem(factura.getEstado());
            }
        }
    }

    private void limpiarFormulario() {
        txtIdFactura.setText("");
        txtCita.setText("");
        spinnerFechaEmision.setValue(new Date());
        txtTotal.setText("");
        comboEstado.setSelectedIndex(0);
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}