package dev.cliniq.cliniq.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chembiotic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chembiotic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chembiotic")
    private Long idChembiotic;

    private String chembiotic;
    private String ubicction;
    private String tumor;
    private String legend;

    @Column(name = "l_w")
    private String lw;

    private String page;
    private String facts;
    private String sociology;
    private String ratio;

    @Column(name = "birth_children_pow")
    private String birthChildrenPow;

    @Column(name = "j_w")
    private String jw;

    private String hypothesis;

    @Column(name = "a_regime")
    private String aRegime;

    @ManyToOne
    @JoinColumn(name = "id_modice", nullable = false)
    private Modice modice;

    @OneToMany(mappedBy = "chembiotic", cascade = CascadeType.ALL)
    private List<Picture> pictures = new ArrayList<>();
}
