package dev.cliniq.cliniq.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cliniq.cliniq.Model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUserName(String userName);
}