package ru.demanin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.demanin.entity.Ticket;
import ru.demanin.mapper.CreateClientMapper;
import ru.demanin.repository.ClientRepository;


@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final CreateClientMapper createClientMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, CreateClientMapper createClientMapper) {
        this.clientRepository = clientRepository;
        this.createClientMapper = createClientMapper;
    }






}
