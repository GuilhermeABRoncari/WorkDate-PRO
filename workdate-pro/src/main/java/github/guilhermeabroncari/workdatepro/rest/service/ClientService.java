package github.guilhermeabroncari.workdatepro.rest.service;

import github.guilhermeabroncari.workdatepro.domain.dto.ClientDTO;
import github.guilhermeabroncari.workdatepro.domain.dto.ClientUpdateDTO;
import github.guilhermeabroncari.workdatepro.domain.entity.Client;
import github.guilhermeabroncari.workdatepro.domain.entity.WorkDateUser;
import github.guilhermeabroncari.workdatepro.domain.repository.ClientRepository;
import github.guilhermeabroncari.workdatepro.domain.repository.WorkDateUserRepository;
import github.guilhermeabroncari.workdatepro.domain.response.ClientResponse;
import github.guilhermeabroncari.workdatepro.infra.exception.exceptions.BusinessRuleException;
import github.guilhermeabroncari.workdatepro.infra.exception.exceptions.EntityNotFoundException;
import github.guilhermeabroncari.workdatepro.infra.exception.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {
    private WorkDateUserRepository workDateUserRepository;
    private ClientRepository clientRepository;
    private static final String IN_USE = "This client is already in use on a schedule and cannot be deleted.";

    @Transactional
    public ClientResponse save(Long userId, ClientDTO clientDTO) {
        if (!workDateUserRepository.existsById(userId)) throw new UserNotFoundException();

        WorkDateUser user = workDateUserRepository.getReferenceById(userId);
        Client client = new Client(null, clientDTO.name(), clientDTO.fone(), clientDTO.address(), false, user);

        clientRepository.save(client);

        user.getClients().add(client);
        workDateUserRepository.save(user);

        return new ClientResponse(client);
    }

    public List<ClientResponse> list(Long userId) {
        isValid(userId);

        var user = workDateUserRepository.getReferenceById(userId);
        List<ClientResponse> list = new ArrayList<>();
        user.getClients().forEach(client -> list.add(new ClientResponse(client)));

        return list;
    }

    public ClientResponse find(Long userId, Long clientId) {
        isValid(userId, clientId);

        var user = workDateUserRepository.getReferenceById(userId);
        if (user.getClients().contains(clientRepository.getReferenceById(clientId))) {
            return new ClientResponse(clientRepository.getReferenceById(clientId));
        }else throw new EntityNotFoundException();
    }

    @Transactional
    public ClientResponse update(Long userId, Long clientId, ClientUpdateDTO clientDTO) {
        isValid(userId, clientId);

        var user = workDateUserRepository.getReferenceById(userId);
        if (user.getClients().contains(clientRepository.getReferenceById(clientId))) {
            var client = clientRepository.getReferenceById(clientId);
            client.update(clientDTO);
            return new ClientResponse(client);
        }else throw new EntityNotFoundException();
    }

    @Transactional
    public void delete(Long userId, Long clientId) {
        isValid(userId, clientId);

        var user = workDateUserRepository.getReferenceById(userId);
        if (user.getClients().contains(clientRepository.getReferenceById(clientId))) {
            var client = clientRepository.getReferenceById(clientId);

            if (client.getScheduled()) throw new BusinessRuleException(IN_USE);

            user.getClients().remove(client);
            clientRepository.delete(client);

            workDateUserRepository.save(user);
        }else throw new EntityNotFoundException();
    }
    private void isValid(Long userId) {
        if (!workDateUserRepository.existsById(userId)) throw new UserNotFoundException();
    }
    private void isValid(Long userId, Long clientId) {
        if (!workDateUserRepository.existsById(userId)) throw new UserNotFoundException();
        if (!clientRepository.existsById(clientId)) throw new EntityNotFoundException();
    }
}
