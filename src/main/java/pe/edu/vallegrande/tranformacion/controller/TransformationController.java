package pe.edu.vallegrande.tranformacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.tranformacion.model.Transformation;
import pe.edu.vallegrande.tranformacion.service.TransformationService;

import java.sql.Timestamp;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Transformation")
public class TransformationController {

    private final TransformationService transformationService;

    @Autowired
    public TransformationController(TransformationService transformationService) {
        this.transformationService = transformationService;
    }

    @GetMapping("/")
    public List<Transformation> obtenerTodasLasTransformaciones() {
        return transformationService.getAllTransformations();
    }

    @GetMapping("/listar/{id}")
    public Transformation obtenerTransformacionPorId(@PathVariable Long id) {
        return transformationService.getTransformationById(id);
    }

    @PostMapping("/crear")
    public Transformation guardarTransformacion(@RequestBody Transformation transformation) {
        return transformationService.createTransformation(transformation);
    }

    @PutMapping("/actualizar/{id}")
    public Transformation actualizarTransformacion(@PathVariable Long id, @RequestBody Transformation transformation) {
        transformation.setId(id);
        return transformationService.updateTransformation(transformation);
    }

    @PutMapping("/eliminar-logico/{id}")
    public ResponseEntity<?> eliminarLogico(@PathVariable Long id) {
        Transformation transformation = transformationService.getTransformationById(id);
        if (transformation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transformación no encontrada con ID: " + id);
        }

        // Cambiar el estado a "No logrado"
        transformation.setStatus("No logrado");
        transformation.setLastUpdateDate(new Timestamp(System.currentTimeMillis())); // Actualizar la fecha de modificación
        transformationService.updateTransformation(transformation); // Guardar cambios

        return ResponseEntity.ok("Transformación marcada como 'No logrado' correctamente.");
    }

}