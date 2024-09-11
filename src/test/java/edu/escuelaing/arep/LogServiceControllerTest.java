package edu.escuelaing.arep;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import edu.escuelaing.arep.controller.LogServiceController;
import edu.escuelaing.arep.model.LogEntry;
import edu.escuelaing.arep.repository.LogRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class LogServiceControllerTest extends TestCase {

    @Mock
    private LogRepository logRepository;

    @InjectMocks
    private LogServiceController logServiceController;

    public LogServiceControllerTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(LogServiceControllerTest.class);
    }

    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public void testLogMessage() {
        List<LogEntry> mockEntries = Arrays.asList(
                new LogEntry("Test message 1", LocalDateTime.now()),
                new LogEntry("Test message 2", LocalDateTime.now())
        );
        when(logRepository.findTop10ByOrderByTimestampDesc()).thenReturn(mockEntries);

        List<LogEntry> response = logServiceController.logMessage("Test message");

        verify(logRepository, times(1)).save(any(LogEntry.class));
        verify(logRepository, times(1)).findTop10ByOrderByTimestampDesc();

        assertEquals(2, response.size());
        assertEquals("Test message 1", response.get(0).getMessage());
    }
}
