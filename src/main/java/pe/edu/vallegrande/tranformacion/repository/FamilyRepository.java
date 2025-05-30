package pe.edu.vallegrande.tranformacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.vallegrande.tranformacion.model.Family;

import java.util.List;

public interface FamilyRepository extends JpaRepository<Family, Long> {
    List<Family> findAll();
}
