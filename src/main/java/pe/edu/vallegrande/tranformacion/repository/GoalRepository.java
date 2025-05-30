package pe.edu.vallegrande.tranformacion.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.vallegrande.tranformacion.model.Goal;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    @EntityGraph(attributePaths = {"session"}) // Cambiado de "section" a "session"
    List<Goal> findAll();
}
