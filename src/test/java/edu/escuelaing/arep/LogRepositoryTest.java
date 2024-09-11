package edu.escuelaing.arep;

import edu.escuelaing.arep.model.LogEntry;
import edu.escuelaing.arep.repository.LogRepository;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class LogRepositoryTest extends TestCase {

    @Mock
    private LogRepository logRepository;  // Simulación del repositorio

    @InjectMocks
    private LogEntry logEntry;

    public LogRepositoryTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(LogRepositoryTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);  // Inicializamos los mocks
    }

    public void testSaveAndRetrieveLog() {
        // Simulamos una entrada de log
        LogEntry logEntry = new LogEntry("Test message", LocalDateTime.now());

        // Simulamos el comportamiento del método save y findTop10ByOrderByTimestampDesc
        when(logRepository.save(logEntry)).thenReturn(logEntry);
        when(logRepository.findTop10ByOrderByTimestampDesc()).thenReturn(Collections.singletonList(logEntry));

        // Ejecutamos la lógica del test
        logRepository.save(logEntry);
        List<LogEntry> logs = logRepository.findTop10ByOrderByTimestampDesc();

        // Verificamos que el mensaje guardado sea correcto
        assertFalse(logs.isEmpty());
        assertEquals("Test message", logs.get(0).getMessage());

        // Verificamos que los métodos simulados fueron llamados
        verify(logRepository, times(1)).save(logEntry);
        verify(logRepository, times(1)).findTop10ByOrderByTimestampDesc();
    }
}
