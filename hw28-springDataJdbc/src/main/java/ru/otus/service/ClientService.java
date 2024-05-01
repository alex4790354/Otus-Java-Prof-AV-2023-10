package ru.otus.service;

import ru.otus.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> findAll();

    Optional<Client> findById(long id);

    Optional<Client> findByName(String name);

    Optional<Client> save(Client client);

    void delete(Client client);

}
