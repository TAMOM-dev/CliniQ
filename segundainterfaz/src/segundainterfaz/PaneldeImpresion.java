package segundainterfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class PaneldeImpresion extends JFrame {

	private static final long serialVersionUID = 1L;


	    public PaneldeImpresion() {
	
	        setTitle("CliniQ - Panel de Impresión");
	        setSize(800, 600);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);

	    
	        setLayout(new BorderLayout());
	        
	        JPanel barraLateral = new JPanel();
	        barraLateral.setPreferredSize(new Dimension(80, getHeight()));
	        barraLateral.setBackground(new Color(0, 102, 204));
	        add(barraLateral, BorderLayout.WEST);

	
	
	        JPanel barraBusqueda = new JPanel();
	        barraBusqueda.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
	        barraBusqueda.setBackground(new Color(153, 204, 255));

	
	        JTextField txtBusqueda = new JTextField(15);

	  
	        JButton btnBuscar = new JButton("Buscar");
	        btnBuscar.setBackground(new Color(51, 153, 255));
	        btnBuscar.setForeground(Color.WHITE);

	        barraBusqueda.add(txtBusqueda);
	        barraBusqueda.add(btnBuscar);

	        add(barraBusqueda, BorderLayout.NORTH);

	        JPanel panelCentral = new JPanel();
	        panelCentral.setLayout(new GridBagLayout());
	        panelCentral.setBackground(new Color(204, 229, 255));
	        add(panelCentral, BorderLayout.CENTER);


	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.fill = GridBagConstraints.BOTH; 
	        gbc.insets = new Insets(10, 10, 10, 10); 
	        gbc.weightx = 1.0;
	        gbc.weighty = 0.5; 

	  
	        JTable tablaPendientes = new JTable();
	
	        DefaultTableModel modeloPendientes = new DefaultTableModel(
	            new Object[][] {
	          
	                {"F001", "Juan Pérez", 150.00, "Pendiente"},
	                {"F002", "María Gómez", 300.00, "Pendiente"}
	            },
	            new String[] {"Factura", "Paciente", "Monto", "Estado"}
	        );
	        tablaPendientes.setModel(modeloPendientes);

	        
	        JScrollPane scrollPendientes = new JScrollPane(tablaPendientes);

	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        panelCentral.add(scrollPendientes, gbc);

	        JTable tablaPagadas = new JTable();
	    
	        DefaultTableModel modeloPagadas = new DefaultTableModel(
	            new Object[][] {
	        
	                {"F010", "Carlos Ortiz", 200.00, "Pagado"},
	                {"F011", "Ana Ruiz", 100.00, "Pagado"}
	            },
	            new String[] {"Factura", "Paciente", "Monto", "Estado"}
	        );
	        tablaPagadas.setModel(modeloPagadas);

	        JScrollPane scrollPagadas = new JScrollPane(tablaPagadas);

	        gbc.gridx = 0;
	        gbc.gridy = 1;
	        panelCentral.add(scrollPagadas, gbc);

	     
	        JPanel panelInferior = new JPanel();
	        panelInferior.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
	        panelInferior.setBackground(new Color(153, 204, 255)); 

	        JButton btnImprimir = new JButton("Imprimir");
	  
	        btnImprimir.setBackground(new Color(128, 0, 128));
	        btnImprimir.setForeground(Color.WHITE);
	        btnImprimir.setPreferredSize(new Dimension(100, 30));

	        panelInferior.add(btnImprimir);
	        add(panelInferior, BorderLayout.SOUTH);
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            new PaneldeImpresion().setVisible(true);
	        });
	    }

}
