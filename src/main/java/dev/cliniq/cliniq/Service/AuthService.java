package dev.cliniq.cliniq.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import dev.cliniq.cliniq.Model.Usuario;
import dev.cliniq.cliniq.Repository.UsuarioRepository;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // Añade esto

    @Autowired
    public AuthService(
        UsuarioRepository usuarioRepository, 
        PasswordEncoder passwordEncoder // Inyecta el encoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario autenticar(String username, String password) {
        Usuario usuario = usuarioRepository.findByUserName(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Compara la contraseña usando el encoder
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        
        return usuario;
    }
}