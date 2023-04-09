package github.guilhermeabroncari.workdatepro.domain.response;

import github.guilhermeabroncari.workdatepro.domain.entity.Client;

public record ClientResponse(Long id, String name, String fone, String address, Boolean scheduled) {
    public ClientResponse(Client client){
        this(client.getId(), client.getName(), client.getFone(), client.getAddress(), client.getScheduled());
    }
}
