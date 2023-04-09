package github.guilhermeabroncari.workdatepro.domain.repository;

import github.guilhermeabroncari.workdatepro.domain.entity.WorkDateUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface WorkDateUserRepository extends JpaRepository<WorkDateUser, Long> {

    UserDetails findByLogin(String login);

    boolean existsByLogin(String login);

}
