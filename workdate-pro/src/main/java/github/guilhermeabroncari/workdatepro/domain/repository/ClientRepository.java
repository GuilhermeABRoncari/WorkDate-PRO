package github.guilhermeabroncari.workdatepro.domain.repository;

import github.guilhermeabroncari.workdatepro.domain.entity.Client;
import github.guilhermeabroncari.workdatepro.domain.entity.WorkDateUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
