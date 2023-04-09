package github.guilhermeabroncari.workdatepro.domain.response;

import github.guilhermeabroncari.workdatepro.domain.entity.Release;
import github.guilhermeabroncari.workdatepro.domain.entity.enums.ReleaseStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record ReleaseResponse(Long id, String client, String service, BigDecimal price, String observation,
                              Timestamp schedule_time, ReleaseStatus status) {
    public ReleaseResponse(Release release) {
        this(release.getId(), release.getSchedule().getClient().getName(), release.getSchedule().getService().getTitle(),
                release.getSchedule().getService().getPrice(), release.getSchedule().getObservation(),
                release.getSchedule().getScheduledTime(), release.getStatus());
    }
}
