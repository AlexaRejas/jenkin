package pe.edu.vallegrande.tranformacion.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.edu.vallegrande.tranformacion.model.Goal;
import pe.edu.vallegrande.tranformacion.service.GoalService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GoalControllerTest {

    @Mock
    private GoalService goalService;

    @InjectMocks
    private GoalController goalController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerTodasLasGoals() {
        Goal goal = new Goal("Goal 1", "Indicator 1", "Objective 1", "Current Situation", "A", null);
        when(goalService.obtenerTodasLasGoals()).thenReturn(Arrays.asList(goal));

        List<Goal> goals = goalController.obtenerTodasLasGoals();
        assertEquals(1, goals.size());
        assertEquals("Goal 1", goals.get(0).getName());
        verify(goalService, times(1)).obtenerTodasLasGoals();
    }

    // Otras pruebas...
}
