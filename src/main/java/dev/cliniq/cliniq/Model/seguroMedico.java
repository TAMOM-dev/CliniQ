package dev.cliniq.cliniq.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "seguro")
@NoArgsConstructor
@AllArgsConstructor
public class seguroMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguro")
    private Long idSeguro;

    private String nombre;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;
}
