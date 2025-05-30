package pe.edu.vallegrande.tranformacion.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.edu.vallegrande.tranformacion.model.Transformation;
import pe.edu.vallegrande.tranformacion.repository.TransformationRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransformationServiceImplTest {

    @Mock
    private TransformationRepository transformationRepository;

    @InjectMocks
    private TransformationServiceImpl transformationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTransformations() {
        Transformation transformation = new Transformation();
        transformation.setStatus("En progreso");
        when(transformationRepository.findAll()).thenReturn(Arrays.asList(transformation));

        List<Transformation> transformations = transformationService.getAllTransformations();
        assertEquals(1, transformations.size());
        assertEquals("En progreso", transformations.get(0).getStatus());
        verify(transformationRepository, times(1)).findAll();
    }

    @Test
    public void testGetTransformationById_Encontrada() {
        Transformation transformation = new Transformation();
        when(transformationRepository.findById(1L)).thenReturn(Optional.of(transformation));

        Transformation result = transformationService.getTransformationById(1L);
        assertNotNull(result);
        verify(transformationRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetTransformationById_NoEncontrada() {
        when(transformationRepository.findById(99L)).thenReturn(Optional.empty());

        Transformation result = transformationService.getTransformationById(99L);
        assertNull(result);
        verify(transformationRepository, times(1)).findById(99L);
    }
}
