package pe.edu.vallegrande.tranformacion.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@CrossOrigin(origins = "http://localhost:4200")
@Entity
@Table(name = "GOAL")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String indicator;
    private String objective;
    private String currentSituation;
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id", nullable = false)
    @JsonIgnoreProperties({"goals"})
    private Session session;

    // Relación con Transformation

    @JsonBackReference // Indica que esta es la parte inversa de la relación
    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transformation> transformations = new ArrayList<>();

    public Goal() {
    }

    public Goal(String name, String indicator, String objective, String currentSituation, String status, Session session) {
        this.name = name;
        this.indicator = indicator;
        this.objective = objective;
        this.currentSituation = currentSituation;
        this.status = status;
        this.session = session;
    }
}
