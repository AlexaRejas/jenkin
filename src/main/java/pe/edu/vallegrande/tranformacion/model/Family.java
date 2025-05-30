package pe.edu.vallegrande.tranformacion.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Setter
@Getter
@CrossOrigin(origins = "http://localhost:4200")
@Entity
@Table(name = "family")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastName;

    // Constructores
    public Family() {
    }

    public Family(String lastName) {
        this.lastName = lastName;
    }
}
