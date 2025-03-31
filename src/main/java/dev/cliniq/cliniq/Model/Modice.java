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
@Table(name = "modice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Modice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modice")
    private Long idModice;

    private String modice;
    private String greece;

    @OneToMany(mappedBy = "modice", cascade = CascadeType.ALL)
    private List<Chembiotic> chembiotics = new ArrayList<>();
}
