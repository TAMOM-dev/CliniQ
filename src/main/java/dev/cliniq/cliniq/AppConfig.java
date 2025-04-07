package dev.cliniq.cliniq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

@Configuration
public class AppConfig {
    
    @Bean(name = "inicioPanel")
    public JPanel inicioPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Configura el contenido del panel de inicio aquí
        JLabel welcomeLabel = new JLabel("Bienvenido a CliniQ");
        // ... (resto de la configuración del panel)
        
        return panel;
    }
}
