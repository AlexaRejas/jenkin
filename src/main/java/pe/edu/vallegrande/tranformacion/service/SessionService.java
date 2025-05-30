package pe.edu.vallegrande.tranformacion.service;

import org.springframework.stereotype.Service;
import pe.edu.vallegrande.tranformacion.model.Session;

import java.util.List;

@Service
public interface SessionService {

    List<Session> obtenerTodasLasSesiones();

    Session obtenerSessionPorId(Long id);

    Session guardarSession(Session session);

    Session actualizarSession(Long id, Session session);

    Session restoreSession(Long id);

    void eliminarSession(Long id);
}
