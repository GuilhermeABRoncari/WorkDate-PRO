package github.guilhermeabroncari.workdatepro.domain.repository;

import github.guilhermeabroncari.workdatepro.domain.entity.Schedule;
import github.guilhermeabroncari.workdatepro.domain.entity.enums.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByStatus(ScheduleStatus status);

    boolean existsByScheduledTime(Timestamp timestamp);
}
