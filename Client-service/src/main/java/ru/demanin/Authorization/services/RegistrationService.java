package ru.demanin.Authorization.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.demanin.entity.Client;
import ru.demanin.repository.ClientRepository;


@Service
public class RegistrationService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void register(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.savePerson(client);
    }
}
