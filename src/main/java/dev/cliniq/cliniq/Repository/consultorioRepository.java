package dev.cliniq.cliniq.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cliniq.cliniq.Model.Consultorio;

public interface consultorioRepository extends JpaRepository<Consultorio, Long> {
    
}
