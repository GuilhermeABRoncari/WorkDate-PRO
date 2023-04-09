package github.guilhermeabroncari.workdatepro.domain.repository;

import github.guilhermeabroncari.workdatepro.domain.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}