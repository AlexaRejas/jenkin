package pe.edu.vallegrande.tranformacion.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.tranformacion.model.Family;
import pe.edu.vallegrande.tranformacion.repository.FamilyRepository;
import pe.edu.vallegrande.tranformacion.service.FamilyService;

import java.util.List;
import java.util.Optional;

@Service
public class FamilyServiceImpl implements FamilyService {

    private final FamilyRepository familyRepository;

    @Autowired
    public FamilyServiceImpl(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    @Override
    public List<Family> obtenerTodasLasFamilies() {
        return familyRepository.findAll();
    }

    @Override
    public Family obtenerFamilyPorId(Long id) {
        Optional<Family> family = familyRepository.findById(id);
        return family.orElse(null);
    }

    @Override
    public Family guardarFamily(Family family) {
        return familyRepository.save(family);
    }

    @Override
    public Family restoreFamily(Long id) {
        Optional<Family> family = familyRepository.findById(id);
        if (family.isPresent()) {
            Family restoredFamily = family.get();
            // Aquí puedes cambiar el estado si es necesario
            return familyRepository.save(restoredFamily);
        }
        return null;
    }

    @Override
    public void eliminarFamily(Long id) {
        familyRepository.deleteById(id);
    }
}
