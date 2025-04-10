package dev.cliniq.cliniq.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "usuarios")
@DiscriminatorColumn(name = "tipo_usuario")
@AllArgsConstructor
@NoArgsConstructor
public abstract class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private long idUsuario;

    @Column(name = "user_name")
    private String userName;

    @Column(nullable = false, name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", insertable = false, updatable = false)
    private TipoUsuario TipoUsuario;

}
