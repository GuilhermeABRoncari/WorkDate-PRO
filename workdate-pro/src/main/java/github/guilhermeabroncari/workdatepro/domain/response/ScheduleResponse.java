package github.guilhermeabroncari.workdatepro.domain.response;

import github.guilhermeabroncari.workdatepro.domain.entity.Schedule;
import github.guilhermeabroncari.workdatepro.domain.entity.enums.ScheduleStatus;

import java.sql.Timestamp;

public record ScheduleResponse(Long id, ClientResponse client, ServiceResponse service, Timestamp scheduleTime, String observation, ScheduleStatus status) {
    public ScheduleResponse(Schedule schedule) {
        this(schedule.getId(), new ClientResponse(schedule.getClient()), new ServiceResponse(schedule.getService()), schedule.getScheduledTime(), schedule.getObservation(), schedule.getStatus());
    }
}
