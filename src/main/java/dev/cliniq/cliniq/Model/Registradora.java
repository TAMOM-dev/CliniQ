package dev.cliniq.cliniq.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("REGISTRADORA")
public class Registradora extends Usuario{
    
}
