package pe.edu.vallegrande.tranformacion.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.edu.vallegrande.tranformacion.model.Session;
import pe.edu.vallegrande.tranformacion.repository.SessionRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SessionControllerTest {

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private SessionController sessionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSession_NombreNulo() {
        Session session = new Session();
        session.setName(null); // Nombre nulo

        ResponseEntity<?> response = sessionController.createSession(session);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El nombre de la sesión es obligatorio.", response.getBody());
    }

}
