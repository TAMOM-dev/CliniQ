package dev.cliniq.cliniq.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cliniq.cliniq.Model.Cita;

public interface citaRepository extends JpaRepository<Cita, Long>{
    
}
