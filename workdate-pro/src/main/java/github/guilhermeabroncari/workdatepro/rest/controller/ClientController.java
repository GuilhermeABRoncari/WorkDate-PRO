package github.guilhermeabroncari.workdatepro.rest.controller;

import github.guilhermeabroncari.workdatepro.domain.dto.ClientDTO;
import github.guilhermeabroncari.workdatepro.domain.dto.ClientUpdateDTO;
import github.guilhermeabroncari.workdatepro.domain.response.ClientResponse;
import github.guilhermeabroncari.workdatepro.rest.service.ClientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("workdate/{user_id}/clients")
@AllArgsConstructor
public class ClientController {
    private ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponse save(@PathVariable Long user_id, @RequestBody @Valid ClientDTO clientDTO) {
        return clientService.save(user_id, clientDTO);
    }

    @GetMapping
    public List<ClientResponse> findAllClients(@PathVariable Long user_id) {
        return clientService.list(user_id);
    }

    @GetMapping("/{client_id}")
    public ClientResponse findClientById(@PathVariable Long user_id, @PathVariable Long client_id) {
        return clientService.find(user_id, client_id);
    }

    @PutMapping("/{client_id}")
    public ClientResponse updateClient(@PathVariable Long user_id, @PathVariable Long client_id, @RequestBody ClientUpdateDTO clientDTO) {
        return clientService.update(user_id, client_id, clientDTO);
    }

    @DeleteMapping("/{client_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long user_id, @PathVariable Long client_id) {
        clientService.delete(user_id, client_id);
    }
}
