package github.guilhermeabroncari.workdatepro.rest.service;

import github.guilhermeabroncari.workdatepro.domain.dto.ServiceDTO;
import github.guilhermeabroncari.workdatepro.domain.dto.ServiceUpdateDTO;
import github.guilhermeabroncari.workdatepro.domain.entity.Service;
import github.guilhermeabroncari.workdatepro.domain.entity.WorkDateUser;
import github.guilhermeabroncari.workdatepro.domain.repository.WorkDateUserRepository;
import github.guilhermeabroncari.workdatepro.domain.repository.WorkJobRepository;
import github.guilhermeabroncari.workdatepro.domain.response.ServiceResponse;
import github.guilhermeabroncari.workdatepro.infra.exception.exceptions.BadRequestException;
import github.guilhermeabroncari.workdatepro.infra.exception.exceptions.BusinessRuleException;
import github.guilhermeabroncari.workdatepro.infra.exception.exceptions.EntityNotFoundException;
import github.guilhermeabroncari.workdatepro.infra.exception.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceService {
    private WorkDateUserRepository workDateUserRepository;
    private WorkJobRepository workJobRepository;
    private static final String IN_USE = "This service is already in use on a schedule and cannot be deleted.";

    @Transactional
    public ServiceResponse save(Long userId, ServiceDTO serviceDTO) {
        isValid(userId);

        if (serviceDTO.hasPermanence() && serviceDTO.permanence() == null) {
            throw new BadRequestException("If field 'hasPermanence' is true, field 'permanence' cannot be null!");
        }

        WorkDateUser user = workDateUserRepository.getReferenceById(userId);
        Service service = new Service(
                null,
                serviceDTO.title(),
                serviceDTO.description(),
                serviceDTO.value(),
                serviceDTO.hasPermanence(),
                serviceDTO.permanence(),
                false, user);

        user.getServices().add(service);

        workDateUserRepository.save(user);
        workJobRepository.save(service);

        return new ServiceResponse(service);
    }

    public List<ServiceResponse> list(Long userId) {
        isValid(userId);

        var user = workDateUserRepository.getReferenceById(userId);
        List<ServiceResponse> list = new ArrayList<>();
        user.getServices().forEach(workJob -> list.add(new ServiceResponse(workJob)));

        return list;
    }

    public ServiceResponse find(Long userId, Long serviceId) {
        isValid(userId, serviceId);

        var user = workDateUserRepository.getReferenceById(userId);
        if (user.getServices().contains(workJobRepository.getReferenceById(serviceId))) {
            return new ServiceResponse(workJobRepository.getReferenceById(serviceId));
        } else throw new EntityNotFoundException();
    }

    @Transactional
    public ServiceResponse update(Long userId, Long serviceId, ServiceUpdateDTO serviceUpdateDTO) {
        isValid(userId, serviceId);

        var user = workDateUserRepository.getReferenceById(userId);
        if (user.getServices().contains(workJobRepository.getReferenceById(serviceId))) {
            var service = workJobRepository.getReferenceById(serviceId);
            if (serviceUpdateDTO.hasPermanence() && serviceUpdateDTO.permanence() == null) {
                throw new BadRequestException("If field 'hasPermanence' is true, field 'permanence' cannot be null!");
            }
            service.update(serviceUpdateDTO);
            return new ServiceResponse(service);
        } else throw new EntityNotFoundException();
    }

    @Transactional
    public void delete(Long userId, Long serviceId) {
        isValid(userId, serviceId);

        var user = workDateUserRepository.getReferenceById(userId);
        if (user.getServices().contains(workJobRepository.getReferenceById(serviceId))) {
            var service = workJobRepository.getReferenceById(serviceId);

            if (service.getScheduled()) throw new BusinessRuleException(IN_USE);

            user.getServices().remove(service);
            workJobRepository.delete(service);

            workDateUserRepository.save(user);
        } else throw new EntityNotFoundException();
    }

    private void isValid(Long userId) {
        if (!workDateUserRepository.existsById(userId)) throw new UserNotFoundException();
    }

    private void isValid(Long userId, Long serviceId) {
        if (!workDateUserRepository.existsById(userId)) throw new UserNotFoundException();
        if (!workJobRepository.existsById(serviceId)) throw new EntityNotFoundException();
    }
}
