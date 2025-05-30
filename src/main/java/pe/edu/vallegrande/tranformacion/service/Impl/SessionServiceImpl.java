package pe.edu.vallegrande.tranformacion.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.tranformacion.model.Goal;
import pe.edu.vallegrande.tranformacion.model.Session;
import pe.edu.vallegrande.tranformacion.repository.SessionRepository;
import pe.edu.vallegrande.tranformacion.service.SessionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public List<Session> obtenerTodasLasSesiones() {
        return sessionRepository.findAll();
    }

    @Override
    public Session obtenerSessionPorId(Long id) {
        Optional<Session> session = sessionRepository.findById(id);
        return session.orElse(null);
    }

    @Override
    public Session guardarSession(Session session) {
        if (session.getGoals() == null) {
            session.setGoals(new ArrayList<>());
        }

        for (Goal goal : session.getGoals()) {
            goal.setSession(session);
        }

        return sessionRepository.save(session);
    }

    @Override
    public Session actualizarSession(Long id, Session session) {
        Session sessionExistente = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada con ID: " + id));

        sessionExistente.setNumberSession(session.getNumberSession()); // ✅ CAMBIO HECHO AQUÍ
        sessionExistente.setName(session.getName());
        sessionExistente.setDescription(session.getDescription());
        sessionExistente.setStatus(session.getStatus());

        // ✅ Asegurar que goals nunca sea null
        if (session.getGoals() == null) {
            session.setGoals(new ArrayList<>());
        }

        // ✅ Actualizar la relación bidireccional
        sessionExistente.getGoals().clear();
        for (Goal goal : session.getGoals()) {
            goal.setSession(sessionExistente);
            sessionExistente.getGoals().add(goal);
        }

        return sessionRepository.save(sessionExistente);
    }

    @Override
    public Session restoreSession(Long id) {
        Optional<Session> session = sessionRepository.findById(id);
        if (session.isPresent()) {
            Session restoredSession = session.get();
            restoredSession.setStatus("A"); // Restaurar como activo
            return sessionRepository.save(restoredSession);
        }
        return null;
    }

    @Override
    public void eliminarSession(Long id) {
        sessionRepository.deleteById(id);
    }
}
