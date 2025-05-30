package pe.edu.vallegrande.tranformacion.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.tranformacion.model.Goal;
import pe.edu.vallegrande.tranformacion.repository.GoalRepository;
import pe.edu.vallegrande.tranformacion.service.GoalService;

import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;

    @Autowired
    public GoalServiceImpl(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public List<Goal> obtenerTodasLasGoals() {
        return goalRepository.findAll();
    }

    @Override
    public Goal obtenerGoalPorId(Long id) {
        Optional<Goal> goal = goalRepository.findById(id);
        return goal.orElse(null);
    }

    @Override
    public Goal guardarGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    @Override
    public Goal restoreGoal(Long id) {
        Optional<Goal> goal = goalRepository.findById(id);
        if (goal.isPresent()) {
            Goal restoredGoal = goal.get();
            restoredGoal.setStatus("A"); // O el estado que necesites
            return goalRepository.save(restoredGoal);
        }
        return null;
    }

    @Override
    public void eliminarGoal(Long id) {
        goalRepository.deleteById(id);
    }
}
