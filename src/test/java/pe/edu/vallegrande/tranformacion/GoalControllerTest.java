package pe.edu.vallegrande.tranformacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.edu.vallegrande.tranformacion.controller.GoalController;
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

        System.out.println("Prueba de obtener todas las goals: Éxito - Listado realizado correctamente.");
    }

    @Test
    public void testObtenerGoalPorId_Encontrada() {
        Goal goal = new Goal("Goal 1", "Indicator 1", "Objective 1", "Current Situation", "A", null);
        when(goalService.obtenerGoalPorId(1L)).thenReturn(goal);

        ResponseEntity<?> response = goalController.obtenerGoalPorId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Goal 1", ((Goal) response.getBody()).getName());
        verify(goalService, times(1)).obtenerGoalPorId(1L);

        System.out.println("Prueba de obtener goal por ID existente: Éxito - Goal encontrada correctamente.");
    }

    @Test
    public void testObtenerGoalPorId_NoEncontrada() {
        when(goalService.obtenerGoalPorId(99L)).thenReturn(null);

        ResponseEntity<?> response = goalController.obtenerGoalPorId(99L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Goal no encontrada con ID: 99", response.getBody());
        verify(goalService, times(1)).obtenerGoalPorId(99L);

        System.out.println("Prueba de obtener goal por ID inexistente: Éxito - Goal no encontrada manejada adecuadamente.");
    }

    @Test
    public void testRestaurarGoal_Exito() {
        Goal goal = new Goal("Goal Restored", "Indicator", "Objective", "Current Situation", "A", null);
        when(goalService.restoreGoal(1L)).thenReturn(goal);

        ResponseEntity<?> response = goalController.restoreGoal(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Goal Restored", ((Goal) response.getBody()).getName());
        verify(goalService, times(1)).restoreGoal(1L);

        System.out.println("Prueba de restaurar goal existente: Éxito - Goal restaurada correctamente.");
    }

    @Test
    public void testRestaurarGoal_NoEncontrada() {
        when(goalService.restoreGoal(99L)).thenReturn(null);

        ResponseEntity<?> response = goalController.restoreGoal(99L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Goal no encontrada para restaurar con ID: 99", response.getBody());
        verify(goalService, times(1)).restoreGoal(99L);

        System.out.println("Prueba de restaurar goal inexistente: Éxito - Error manejado correctamente.");
    }
}
