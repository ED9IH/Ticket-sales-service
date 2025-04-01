package ru.demanin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.demanin.mapper.CreateClientMapper;
import ru.demanin.repository.PersonRepository;

@Service
public class ClientService {
    private final PersonRepository personRepository;
    private final CreateClientMapper createClientMapper;

    @Autowired
    public ClientService(PersonRepository personRepository, CreateClientMapper createClientMapper) {
        this.personRepository = personRepository;
        this.createClientMapper = createClientMapper;
    }




}
