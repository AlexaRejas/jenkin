package pe.edu.vallegrande.tranformacion.service;

import pe.edu.vallegrande.tranformacion.model.Transformation;

import java.util.List;

public interface TransformationService {
    List<Transformation> getAllTransformations();
    Transformation getTransformationById(Long id);
    Transformation createTransformation(Transformation transformation);
    Transformation updateTransformation(Transformation transformation);
    Transformation logicalDelete(Long id);  // Eliminado lógico - cambia estado a "Not achieved"
    Transformation changeStatusToInactive(Long id); // Alias para logicalDelete por compatibilidad
}