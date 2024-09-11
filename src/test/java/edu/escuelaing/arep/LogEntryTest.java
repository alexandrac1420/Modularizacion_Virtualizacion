package edu.escuelaing.arep;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import edu.escuelaing.arep.model.LogEntry;
import java.time.LocalDateTime;

public class LogEntryTest extends TestCase {

    public LogEntryTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(LogEntryTest.class);
    }

    public void testLogEntryCreation() {
        LocalDateTime timestamp = LocalDateTime.now();
        LogEntry logEntry = new LogEntry("Test message", timestamp);

        assertEquals("Test message", logEntry.getMessage());
        assertEquals(timestamp, logEntry.getTimestamp());
    }

    public void testLogEntrySettersAndGetters() {
        LogEntry logEntry = new LogEntry();
        LocalDateTime timestamp = LocalDateTime.now();

        logEntry.setMessage("New message");
        logEntry.setTimestamp(timestamp);

        assertEquals("New message", logEntry.getMessage());
        assertEquals(timestamp, logEntry.getTimestamp());
    }
}
