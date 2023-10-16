package ru.ulstu.is.sbapp.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulstu.is.sbapp.record.model.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
