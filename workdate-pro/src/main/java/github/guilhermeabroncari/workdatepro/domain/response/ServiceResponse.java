package github.guilhermeabroncari.workdatepro.domain.response;

import github.guilhermeabroncari.workdatepro.domain.entity.Service;

import java.math.BigDecimal;
import java.sql.Time;

public record ServiceResponse(
        Long id,
        String title,
        String description,
        BigDecimal value,
        Boolean hasPermanence,
        Time permanence,
        Boolean scheduled
) {
    public ServiceResponse(Service service) {
        this(service.getId(), service.getTitle(), service.getDescription(), service.getPrice(),
                service.getHasPermanence(), service.getPermanence(), service.getScheduled());
    }
}
