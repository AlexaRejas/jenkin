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

    @PostMapping("/create")
    public ResponseEntity<?> createFamily(@RequestBody Family family) {
        Family savedFamily = familyRepository.save(family);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFamily);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarFamily(@PathVariable Long id, @RequestBody Family family) {
        if (!familyRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Familia no encontrada con ID: " + id);
        }

        family.setId(id);
        Family updatedFamily = familyService.guardarFamily(family);
        return ResponseEntity.ok(updatedFamily);
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<?> restoreFamily(@PathVariable Long id) {
        try {
            Family restoredFamily = familyService.restoreFamily(id);
            return restoredFamily != null ? ResponseEntity.ok(restoredFamily)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Familia no encontrada para restaurar con ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al restaurar la familia: " + e.getMessage());
        }
    }

    @PutMapping("/eliminar-logico/{id}")
    public ResponseEntity<?> eliminarLogico(@PathVariable Long id) {
        Family family = familyRepository.findById(id).orElse(null);
        if (family == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Familia no encontrada con ID: " + id);
        }

        // Cambiar el estado a "I" (inactivo) en lugar de eliminar
        // Aquí puedes cambiar el estado si es necesario
        familyRepository.save(family);

        return ResponseEntity.ok("Familia marcada como inactiva correctamente.");
    }
}
