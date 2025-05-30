package pe.edu.vallegrande.tranformacion.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.edu.vallegrande.tranformacion.model.Transformation;
import pe.edu.vallegrande.tranformacion.service.TransformationService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransformationControllerTest {

    @Mock
    private TransformationService transformationService;

    @InjectMocks
    private TransformationController transformationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGuardarTransformacion() {
        Transformation transformation = new Transformation();
        transformation.setStatus("En progreso");
        
        when(transformationService.createTransformation(transformation)).thenReturn(transformation);

        Transformation result = transformationController.guardarTransformacion(transformation);
        assertNotNull(result);
        assertEquals("En progreso", result.getStatus());
        verify(transformationService, times(1)).createTransformation(transformation);
    }

  
}
