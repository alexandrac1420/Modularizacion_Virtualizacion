package edu.escuelaing.arep.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "logs")
public class LogEntry {

    @Id
    private String id;
    private String message;
    private LocalDateTime timestamp;

    public LogEntry() {
    }

    public LogEntry(String message, LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
