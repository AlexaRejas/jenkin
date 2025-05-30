package pe.edu.vallegrande.tranformacion.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.edu.vallegrande.tranformacion.model.Goal;
import pe.edu.vallegrande.tranformacion.repository.GoalRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GoalServiceImplTest {

    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private GoalServiceImpl goalService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerTodasLasGoals() {
        Goal goal = new Goal("Goal 1", "Indicator 1", "Objective 1", "Current Situation", "A", null);
        when(goalRepository.findAll()).thenReturn(Arrays.asList(goal));

        List<Goal> goals = goalService.obtenerTodasLasGoals();
        assertEquals(1, goals.size());
        assertEquals("Goal 1", goals.get(0).getName());
        verify(goalRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerGoalPorId_Encontrada() {
        Goal goal = new Goal("Goal 1", "Indicator 1", "Objective 1", "Current Situation", "A", null);
        when(goalRepository.findById(1L)).thenReturn(Optional.of(goal));

        Goal result = goalService.obtenerGoalPorId(1L);
        assertNotNull(result);
        assertEquals("Goal 1", result.getName());
        verify(goalRepository, times(1)).findById(1L);
    }

    @Test
    public void testObtenerGoalPorId_NoEncontrada() {
        when(goalRepository.findById(99L)).thenReturn(Optional.empty());

        Goal result = goalService.obtenerGoalPorId(99L);
        assertNull(result);
        verify(goalRepository, times(1)).findById(99L);
    }
}
