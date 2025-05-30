package pe.edu.vallegrande.tranformacion.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.tranformacion.model.Transformation;
import pe.edu.vallegrande.tranformacion.repository.TransformationRepository;
import pe.edu.vallegrande.tranformacion.service.TransformationService;

import java.util.Date;
import java.util.List;

@Service
public class TransformationServiceImpl implements TransformationService {
    private final TransformationRepository transformationRepository;

    @Autowired
    public TransformationServiceImpl(TransformationRepository transformationRepository) {
        this.transformationRepository = transformationRepository;
    }

    @Override
    public List<Transformation> getAllTransformations() {
        return transformationRepository.findAll();
    }

    @Override
    public Transformation getTransformationById(Long id) {
        return transformationRepository.findById(id).orElse(null);
    }

    @Override
    public Transformation createTransformation(Transformation transformation) {
        return transformationRepository.save(transformation);
    }

    @Override
    public Transformation updateTransformation(Transformation transformation) {
        return transformationRepository.save(transformation);
    }

    @Override
    public Transformation logicalDelete(Long id) {
        Transformation transformation = getTransformationById(id);
        if (transformation == null) {
            throw new RuntimeException("Transformation not found");
        }
        // Cambiar el estado a "No logrado"
        transformation.setStatus("No logrado");
        transformation.setLastUpdateDate(new Date()); // Actualizar la fecha de modificación
        return transformationRepository.save(transformation);
    }

    @Override
    public Transformation changeStatusToInactive(Long id) {
        // Reutilizamos la funcionalidad de logicalDelete
        return logicalDelete(id);
    }

    // Este método debe ser privado o eliminado para asegurar que no se realice eliminación física
    // desde la interfaz pública del servicio
    private void physicalDelete(Long id) {
        Transformation transformation = getTransformationById(id);
        if (transformation == null) {
            throw new RuntimeException("Transformation not found");
        }
        transformationRepository.delete(transformation);
    }
}