package edu.escuelaing.arep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.escuelaing.arep.model.LogEntry;
import edu.escuelaing.arep.repository.LogRepository;

import java.time.LocalDateTime;
import java.util.List;


@RestController
public class LogServiceController {

    @Autowired
    private LogRepository logRepository;

    @PostMapping("/log")
    public List<LogEntry> logMessage(@RequestBody String message) {
        LogEntry logEntry = new LogEntry();
        logEntry.setMessage(message);
        logEntry.setTimestamp(LocalDateTime.now());
        logRepository.save(logEntry);

        return logRepository.findTop10ByOrderByTimestampDesc();
    }
}
