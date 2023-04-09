package github.guilhermeabroncari.workdatepro.domain.repository;

import github.guilhermeabroncari.workdatepro.domain.entity.Release;
import github.guilhermeabroncari.workdatepro.domain.entity.enums.ReleaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReleaseRepository extends JpaRepository<Release, Long> {
    List<Release> findAllByStatus(ReleaseStatus status);
}
