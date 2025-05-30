package pe.edu.vallegrande.tranformacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.tranformacion.model.Goal;
import pe.edu.vallegrande.tranformacion.model.Session;
import pe.edu.vallegrande.tranformacion.service.GoalService;
import pe.edu.vallegrande.tranformacion.repository.GoalRepository;
import pe.edu.vallegrande.tranformacion.repository.SessionRepository;

import java.util.List;

@RestController
@RequestMapping("/goal")
@CrossOrigin(origins = "*")
public class GoalController {

    private final GoalService goalService;
    private final GoalRepository goalRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public GoalController(GoalService goalService, GoalRepository goalRepository, SessionRepository sessionRepository) {
        this.goalService = goalService;
        this.goalRepository = goalRepository;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("/")
    public List<Goal> obtenerTodasLasGoals() {
        return goalService.obtenerTodasLasGoals();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> obtenerGoalPorId(@PathVariable Long id) {
        Goal goal = goalService.obtenerGoalPorId(id);
        return goal != null ? ResponseEntity.ok(goal)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Goal no encontrada con ID: " + id);
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<Session>> obtenerSesiones() {
        List<Session> sessions = sessionRepository.findAll();
        return ResponseEntity.ok(sessions);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createGoal(@RequestBody Goal goal) {
        if (goal.getSession() == null || goal.getSession().getId() == null) {
            return ResponseEntity.badRequest().body("Debe especificar una sesión válida.");
        }

        Session session = sessionRepository.findById(goal.getSession().getId())
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));

        goal.setSession(session);
        Goal savedGoal = goalRepository.save(goal);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGoal);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarGoal(@PathVariable Long id, @RequestBody Goal goal) {
        if (!goalRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Goal no encontrada con ID: " + id);
        }

        goal.setId(id);
        Goal updatedGoal = goalService.guardarGoal(goal);
        return ResponseEntity.ok(updatedGoal);
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<?> restoreGoal(@PathVariable Long id) {
        try {
            Goal restoredGoal = goalService.restoreGoal(id);
            return restoredGoal != null ? ResponseEntity.ok(restoredGoal)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Goal no encontrada para restaurar con ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al restaurar la goal: " + e.getMessage());
        }
    }

    @PutMapping("/eliminar-logico/{id}")
    public ResponseEntity<?> eliminarLogico(@PathVariable Long id) {
        Goal goal = goalRepository.findById(id).orElse(null);
        if (goal == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Goal no encontrada con ID: " + id);
        }

        // Cambiar el estado a "I" (inactivo) en lugar de eliminar
        goal.setStatus("I"); // Cambiar el estado a Inactivo
        goalRepository.save(goal);

        return ResponseEntity.ok("Goal marcada como inactiva correctamente.");
    }
}
