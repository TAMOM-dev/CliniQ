package dev.cliniq.cliniq.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cliniq.cliniq.Model.Medico;

public interface medicoRepository extends JpaRepository<Medico, Long> {
    
}
