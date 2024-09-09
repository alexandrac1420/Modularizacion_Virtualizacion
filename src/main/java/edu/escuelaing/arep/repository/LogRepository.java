package edu.escuelaing.arep.repository;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import edu.escuelaing.arep.model.LogEntry;

public interface LogRepository extends MongoRepository<LogEntry, String> {
    List<LogEntry> findTop10ByOrderByTimestampDesc();
}