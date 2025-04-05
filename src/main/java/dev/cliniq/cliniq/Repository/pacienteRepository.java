package dev.cliniq.cliniq.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cliniq.cliniq.Model.Paciente;

public interface pacienteRepository extends JpaRepository<Paciente, Long> {
    
}
