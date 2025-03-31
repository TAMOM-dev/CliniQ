package dev.cliniq.cliniq.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "consultorio")
@NoArgsConstructor
@AllArgsConstructor
public class Consultorio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consultorio")
    private long idConsultorio;

    private Integer numero;
    private String ubicacion;

    @OneToMany(mappedBy = "consultorio", cascade = CascadeType.ALL)
    private List<Cita> citas = new ArrayList<>();
}
