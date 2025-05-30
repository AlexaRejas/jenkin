package pe.edu.vallegrande.tranformacion.service;

import org.springframework.stereotype.Service;
import pe.edu.vallegrande.tranformacion.model.Goal;

import java.util.List;

@Service
public interface GoalService {

    List<Goal> obtenerTodasLasGoals();
    Goal obtenerGoalPorId(Long id);
    Goal guardarGoal(Goal goal);
    Goal restoreGoal(Long id);
    void eliminarGoal(Long id);
}
