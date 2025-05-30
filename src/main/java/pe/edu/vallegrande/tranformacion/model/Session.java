package pe.edu.vallegrande.tranformacion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@Setter
@Getter
@CrossOrigin(origins = "*")
@Entity
@Table(name = "SESSION")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String status;

    @Column(name = "number_session")
    @JsonProperty("number_session")
    private String numberSession;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Goal> goals;

    public Session() {
    }

    public Session(String name, String description, String status, String numberSession) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.numberSession = numberSession;
    }
}