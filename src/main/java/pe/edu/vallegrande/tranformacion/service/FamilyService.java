package pe.edu.vallegrande.tranformacion.service;

import org.springframework.stereotype.Service;
import pe.edu.vallegrande.tranformacion.model.Family;

import java.util.List;

@Service
public interface FamilyService {
    List<Family> obtenerTodasLasFamilies();
    Family obtenerFamilyPorId(Long id);
}
