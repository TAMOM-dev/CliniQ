package dev.cliniq.cliniq.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "factura")
@NoArgsConstructor
@AllArgsConstructor
public class Factura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private long idFactura;

    @OneToOne
    @JoinColumn(name = "id_cita", nullable = false)
    private Cita cita;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    private BigDecimal total;

    @Column(name = "metodo_pago")
    private String metodoPago;

    private String estado;
}
