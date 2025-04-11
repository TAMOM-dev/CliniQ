package dev.cliniq.cliniq.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Float;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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

    @Autowired
    private ApplicationContext applicationContext;

    private static final long serialVersionUID = 1L;

    public CliniQLoginUI() {
        initComponents();
    }

    public void initComponents() {
        setTitle("CliniQ Login");
        setSize(400, 450); // Ajustamos el tamaño para que sea más compacto (solo login)
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setUndecorated(false);
        getContentPane().setBackground(Color.WHITE); // Fondo blanco para toda la ventana

        // Panel principal (solo login, centrado)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE); // Fondo blanco

        // Panel de login
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(Color.WHITE); // Fondo blanco
        loginPanel.setPreferredSize(new Dimension(350, 400)); // Tamaño ajustado

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

        JButton loginButton = new RoundedButton("Sign In");
        loginButton.setBounds(50, 210, 250, 40); // Ajustamos la posición ya que eliminamos "Remember Me" y "Forgot Password"
        loginButton.setBackground(new Color(0, 120, 215));
        loginButton.setForeground(Color.WHITE);
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

        // Añadir el panel de login al panel principal con centrado
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(loginPanel);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel, BorderLayout.CENTER);
    }
}

// Clase para botones redondeados
class RoundedButton extends JButton {
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