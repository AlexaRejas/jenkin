package pe.edu.vallegrande.tranformacion.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.edu.vallegrande.tranformacion.model.Session;
import pe.edu.vallegrande.tranformacion.repository.SessionRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SessionServiceImplTest {

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private SessionServiceImpl sessionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerTodasLasSesiones() {
        Session session = new Session("Session 1", "Description", "A", "1");
        when(sessionRepository.findAll()).thenReturn(Arrays.asList(session));

        List<Session> sessions = sessionService.obtenerTodasLasSesiones();
        assertEquals(1, sessions.size());
        assertEquals("Session 1", sessions.get(0).getName());
        verify(sessionRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerSessionPorId_Encontrada() {
        Session session = new Session("Session 1", "Description", "A", "1");
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        Session result = sessionService.obtenerSessionPorId(1L);
        assertNotNull(result);
        assertEquals("Session 1", result.getName());
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    public void testObtenerSessionPorId_NoEncontrada() {
        when(sessionRepository.findById(99L)).thenReturn(Optional.empty());

        Session result = sessionService.obtenerSessionPorId(99L);
        assertNull(result);
        verify(sessionRepository, times(1)).findById(99L);
    }
}
