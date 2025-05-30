package pe.edu.vallegrande.tranformacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.tranformacion.model.Goal;
import pe.edu.vallegrande.tranformacion.model.Session;
import pe.edu.vallegrande.tranformacion.repository.SessionRepository;

import java.util.List;

@RestController
@RequestMapping("/session")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class SessionController {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionController(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    // Obtener todas las sesiones
    @GetMapping("/")
    public ResponseEntity<List<Session>> obtenerTodasLasSesiones() {
        List<Session> sessions = sessionRepository.findAll();
        return ResponseEntity.ok(sessions);
    }

    // Obtener sesiones por estado
    @GetMapping("/status/{status}")
    public List<Session> getSessionsByStatus(@PathVariable String status) {
        return sessionRepository.findByStatus(status);
    }

    // Obtener sesión por ID
    @GetMapping("/listar/{id}")
    public ResponseEntity<?> obtenerSessionPorId(@PathVariable Long id) {
        Session session = sessionRepository.findById(id).orElse(null);
        return session != null ? ResponseEntity.ok(session)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sesión no encontrada con ID: " + id);
    }

    // Crear nueva sesión
    @PostMapping("/create")
    public ResponseEntity<?> createSession(@RequestBody Session session) {
        if (session.getName() == null || session.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre de la sesión es obligatorio.");
        }

        Session savedSession = sessionRepository.save(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSession);
    }

    // Actualizar sesión existente
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarSession(
            @PathVariable Long id,
            @RequestBody Session session
    ) {
        try {
            if (!sessionRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Sesión no encontrada con ID: " + id);
            }

            Session sessionExistente = sessionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Sesión no encontrada con ID: " + id));

            // Actualizar campos
            sessionExistente.setNumberSession(session.getNumberSession());
            sessionExistente.setName(session.getName());
            sessionExistente.setDescription(session.getDescription());
            sessionExistente.setStatus(session.getStatus());

            // Actualizar metas (goals)
            if (session.getGoals() != null) {
                sessionExistente.getGoals().clear();
                for (Goal goal : session.getGoals()) {
                    goal.setSession(sessionExistente);
                    sessionExistente.getGoals().add(goal);
                }
            }

            Session updatedSession = sessionRepository.save(sessionExistente);
            return ResponseEntity.ok(updatedSession);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la sesión: " + e.getMessage());
        }
    }

    // Marcar sesión como inactiva
    @PutMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarSession(@PathVariable Long id) {
        Session session = sessionRepository.findById(id).orElse(null);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sesión no encontrada con ID: " + id);
        }

        session.setStatus("I");
        sessionRepository.save(session);
        return ResponseEntity.ok("Sesión marcada como inactiva correctamente.");
    }

    // Restaurar sesión
    @PutMapping("/restore/{id}")
    public ResponseEntity<?> restoreSession(@PathVariable Long id) {
        Session session = sessionRepository.findById(id).orElse(null);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sesión no encontrada con ID: " + id);
        }

        session.setStatus("A");
        sessionRepository.save(session);
        return ResponseEntity.ok("Sesión activada correctamente.");
    }
}
