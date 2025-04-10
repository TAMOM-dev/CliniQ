package dev.cliniq.cliniq.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CAJERA")
public class Cajera extends Usuario{
    
}
