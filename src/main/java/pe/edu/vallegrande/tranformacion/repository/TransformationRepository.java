package pe.edu.vallegrande.tranformacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.vallegrande.tranformacion.model.Transformation;

public interface TransformationRepository extends JpaRepository<Transformation, Long> {
}
