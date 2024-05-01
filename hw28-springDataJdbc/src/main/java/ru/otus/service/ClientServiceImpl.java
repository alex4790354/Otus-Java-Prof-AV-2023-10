package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.model.Client;
import ru.otus.repository.ClientRepository;
import java.util.List;
import java.util.Optional;


@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        log.info("##24 - ClientService.findAll()");
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(long id) {
        log.info("##30 - ClientService.findById() with id = " + id);
        return clientRepository.findById(id);
    }

    @Override
    public Optional<Client> findByName(String name) {
        log.info("##36 - ClientService.findByName(). name = " + name);
        return clientRepository.findByName(name);
    }

    @Override
    public Optional<Client> save(Client client) {
        log.info("##42 - ClientService.save(). client = " + client);
        return Optional.ofNullable(clientRepository.save(client));
    }

    @Override
    public void delete(Client client) {
        clientRepository.delete(client);
    }
}
