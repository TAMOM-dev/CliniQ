package dev.cliniq.cliniq.Service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import dev.cliniq.cliniq.Model.Registradora;
import dev.cliniq.cliniq.Model.TipoUsuario;
import dev.cliniq.cliniq.Repository.UsuarioRepository;

@Configuration
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initUsers(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                Registradora admin = new Registradora();
                admin.setUserName("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setTipoUsuario(TipoUsuario.REGISTRADORA);
                usuarioRepository.save(admin);
            }
        };
    }
}
