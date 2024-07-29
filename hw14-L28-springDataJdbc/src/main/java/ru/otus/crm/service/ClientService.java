package ru.otus.crm.service;

import java.util.List;
import ru.otus.crm.model.Client;

public interface ClientService {
    List<Client> findAll();

    Client findById(Long id);

    Client save(Client client);

    void delete(Long id);
}
