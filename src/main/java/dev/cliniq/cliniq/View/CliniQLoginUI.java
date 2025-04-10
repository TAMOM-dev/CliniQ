package dev.cliniq.cliniq.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Float;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import dev.cliniq.cliniq.Model.Usuario;
import dev.cliniq.cliniq.Service.AuthService;


@org.springframework.stereotype.Component
public class CliniQLoginUI extends JFrame {

    @Autowired
    private AuthService authService;

	private CliniQInterfaz dashboard;

	@Autowired
	private ApplicationContext applicationContext;

	private static final long serialVersionUID = 1L;

    public CliniQLoginUI() {
        initComponents();
    }

	 public void initComponents() {
	        setTitle("CliniQ Login");
	        setSize(800, 450);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setLayout(new BorderLayout());
	        setUndecorated(false);

	        // Panel principal dividido
	        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

	        // Panel izquierdo: Login
	        JPanel loginPanel = new JPanel() {
	            /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                Graphics2D g2d = (Graphics2D) g;
	                GradientPaint gp = new GradientPaint(0, 0, new Color(230, 240, 255), 0, getHeight(), new Color(200, 220, 255));
	                g2d.setPaint(gp);
	                g2d.fillRect(0, 0, getWidth(), getHeight());
	            }
	        };
	        loginPanel.setLayout(null);

	        JLabel signInLabel = new JLabel("Sign In");
	        signInLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
	        signInLabel.setBounds(50, 30, 200, 30);
	        loginPanel.add(signInLabel);

	        JTextField usernameField = new JTextField();
	        usernameField.setBounds(50, 90, 250, 40);
	        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
	        loginPanel.add(usernameField);

	        JPasswordField passwordField = new JPasswordField();
	        passwordField.setBounds(50, 150, 250, 40);
	        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
	        loginPanel.add(passwordField);

	        JCheckBox rememberCheck = new JCheckBox("Remember Me");
	        rememberCheck.setBounds(50, 200, 150, 30);
	        rememberCheck.setOpaque(false);
	        loginPanel.add(rememberCheck);

	        JButton loginButton = new RoundedButton("Sign In");
	        loginButton.setBounds(50, 240, 250, 40);
	        loginButton.setBackground(new Color(0, 120, 215));
	        loginButton.setForeground(Color.white);
	        loginButton.setFocusPainted(false);
	        loginButton.setFont(new Font("SansSerif", Font.BOLD, 16));
	        loginPanel.add(loginButton);

            loginButton.addActionListener(e -> {
				try {
					Usuario usuario = authService.autenticar(
						usernameField.getText(), 
						new String(passwordField.getPassword())
					);
					
					this.setVisible(false);
					
					// Obtén el bean del contexto
					CliniQInterfaz dashboard = applicationContext.getBean(CliniQInterfaz.class);
					dashboard.mostrarVistaSegunRol(usuario);
					dashboard.setVisible(true);
				} catch (RuntimeException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage());
				}
			});

	        JLabel forgotLabel = new JLabel("Forgot Password");
	        forgotLabel.setBounds(200, 280, 150, 30);
	        forgotLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
	        forgotLabel.setForeground(Color.BLUE);
	        loginPanel.add(forgotLabel);

	        // Panel derecho: Mensaje de bienvenida
	        JPanel welcomePanel = new JPanel() {
	            /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                Graphics2D g2d = (Graphics2D) g;
	                GradientPaint gp = new GradientPaint(0, 0, new Color(100, 149, 237), getWidth(), getHeight(), new Color(0, 102, 204));
	                g2d.setPaint(gp);
	                g2d.fillRect(0, 0, getWidth(), getHeight());
	            }
	        };
	        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));

	        JLabel welcomeLabel = new JLabel("Bienvenido a CliniQ");
	        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
	        welcomeLabel.setForeground(Color.white);
	        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 10, 0));

	        JLabel noAccountLabel = new JLabel("¿No tienes una cuenta?");
	        noAccountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	        noAccountLabel.setForeground(Color.white);

	        JButton signUpButton = new RoundedButton("Sign Up");
	        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	        signUpButton.setBackground(Color.white);
	        signUpButton.setForeground(new Color(0, 102, 204));
	        signUpButton.setFocusPainted(false);
	        signUpButton.setFont(new Font("SansSerif", Font.BOLD, 14));

	        welcomePanel.add(welcomeLabel);
	        welcomePanel.add(noAccountLabel);
	        welcomePanel.add(Box.createRigidArea(new Dimension(0, 10)));
	        welcomePanel.add(signUpButton);

	        mainPanel.add(loginPanel);
	        mainPanel.add(welcomePanel);
	        add(mainPanel);
	    }

	    
	}

	// Clase para botones redondeados
	class RoundedButton extends JButton {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public RoundedButton(String text) {
	        super(text);
	        setContentAreaFilled(false);
	        setFocusPainted(false);
	        setBorderPainted(false);
	    }

	    protected void paintComponent(Graphics g) {
	        Graphics2D g2 = (Graphics2D) g.create();
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setColor(getBackground());
	        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
	        super.paintComponent(g);
	    }

	    protected void paintBorder(Graphics g) {
	        // Sin borde
	    }

	    public boolean contains(int x, int y) {
	        Float shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30);
	        return shape.contains(x, y);
	    }

}