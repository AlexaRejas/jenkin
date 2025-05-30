package pe.edu.vallegrande.tranformacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.vallegrande.tranformacion.model.Session;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByStatus(String status);
    // Aquí puedes agregar más métodos personalizados si es necesario
}
