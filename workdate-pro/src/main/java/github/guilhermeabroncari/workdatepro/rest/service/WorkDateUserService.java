package github.guilhermeabroncari.workdatepro.rest.service;

import github.guilhermeabroncari.workdatepro.domain.dto.WorkDateUserDTO;
import github.guilhermeabroncari.workdatepro.domain.entity.WorkDateUser;
import github.guilhermeabroncari.workdatepro.domain.repository.WorkDateUserRepository;
import github.guilhermeabroncari.workdatepro.domain.response.WorkDateUserResponse;
import github.guilhermeabroncari.workdatepro.infra.exception.exceptions.EmailInUseException;
import github.guilhermeabroncari.workdatepro.infra.security.SecurityConfigurations;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WorkDateUserService {
    private WorkDateUserRepository workDateUserRepository;
    private SecurityConfigurations configurations;

    private static final String EMAIL_IN_USE = "Email is already in use.";

    @Transactional
    public WorkDateUserResponse save(WorkDateUserDTO workDateUserDTO) {
        if (workDateUserRepository.existsByLogin(workDateUserDTO.loginEmail())) {
            throw new EmailInUseException(EMAIL_IN_USE);
        }
        WorkDateUser workDateUser = new WorkDateUser(
                null,
                workDateUserDTO.loginEmail(),
                workDateUserDTO.password(),
                true,
                null,
                null,
                null,
                null);

        workDateUser.setPassword(configurations.passwordEncoder().encode(workDateUserDTO.password()));

        workDateUserRepository.save(workDateUser);
        return new WorkDateUserResponse(workDateUser);

    }
}