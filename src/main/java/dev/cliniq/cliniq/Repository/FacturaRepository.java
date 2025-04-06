package dev.cliniq.cliniq.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cliniq.cliniq.Model.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    
}
