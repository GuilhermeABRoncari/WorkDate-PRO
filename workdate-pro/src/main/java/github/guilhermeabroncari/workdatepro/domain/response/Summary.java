package github.guilhermeabroncari.workdatepro.domain.response;

import java.math.BigDecimal;

public record Summary(BigDecimal total_open, BigDecimal total_paid, BigDecimal summation_total) {
}
