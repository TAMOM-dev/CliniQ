package dev.cliniq.cliniq.Model;


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
@Table(name = "picture")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_picture")
    private Long idPicture;

    @Column(name = "accha_mission")
    private String acchaMission;

    private String recumulative;
    private String total;
    private String isolated;
    private String isolate;

    @Column(name = "isolate_page")
    private String isolatePage;

    @Column(name = "regime_modice")
    private String regimeModice;

    @Column(name = "id_page")
    private Long idPage;

    @Column(name = "regime_modice_page")
    private String regimeModicePage;

    @ManyToOne
    @JoinColumn(name = "id_cita", nullable = false)
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "id_chembiotic", nullable = false)
    private Chembiotic chembiotic;
}
