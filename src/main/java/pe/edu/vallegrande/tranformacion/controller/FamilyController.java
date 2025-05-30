package pe.edu.vallegrande.tranformacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.tranformacion.model.Family;
import pe.edu.vallegrande.tranformacion.service.FamilyService;
import pe.edu.vallegrande.tranformacion.repository.FamilyRepository;

import java.util.List;

@RestController
@RequestMapping("/family")
@CrossOrigin(origins = "*")
public class FamilyController {

    private final FamilyService familyService;
    private final FamilyRepository familyRepository;

    @Autowired
    public FamilyController(FamilyService familyService, FamilyRepository familyRepository) {
        this.familyService = familyService;
        this.familyRepository = familyRepository;
    }

    @GetMapping("/")
    public List<Family> obtenerTodasLasFamilies() {
        return familyService.obtenerTodasLasFamilies();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> obtenerFamilyPorId(@PathVariable Long id) {
        Family family = familyService.obtenerFamilyPorId(id);
        return family != null ? ResponseEntity.ok(family)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Familia no encontrada con ID: " + id);
    }

    
}
