package github.guilhermeabroncari.workdatepro.domain.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record ScheduleDTO(
        @NotNull
        Long client_id,
        @NotNull
        Long service_id,
        String observation,
        @NotNull
        @Future
        Timestamp scheduleTime) {
}
