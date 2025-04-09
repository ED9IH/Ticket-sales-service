package ru.demanin.Authorization.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.demanin.Authorization.security.PersonDetails;
import ru.demanin.entity.Client;
import ru.demanin.repository.ClientRepository;


import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;

    @Autowired
    public PersonDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Client> person = Optional.ofNullable(clientRepository.findByLogin(s));
        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return new PersonDetails(person.get());
    }
}
