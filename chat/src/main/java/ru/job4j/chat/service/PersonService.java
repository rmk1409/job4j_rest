package ru.job4j.chat.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.model.dto.PersonDTO;
import ru.job4j.chat.repository.PersonRepository;
import ru.job4j.chat.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    public PersonService(PersonRepository personRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public Optional<Person> findById(@PathVariable long id) {
        return personRepository.findById(id);
    }

    public List<Person> findAll() {
        return StreamSupport.stream(
                this.personRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    public Person create(Person person) {
        String name = person.getName();
        String password = person.getPassword();
        if (name == null || password == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Invalid password. Password length must be more than 5 characters.");
        }
        person.setPassword(encoder.encode(password));
        return this.personRepository.save(person);
    }

    public void update(Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        this.personRepository.save(person);
    }

    public void delete(long id) {
        Person person = new Person();
        person.setId(id);
        this.personRepository.delete(person);
    }

    public void patch(PersonDTO dto) {
        var optionalPerson = personRepository.findById(dto.getId());
        if (optionalPerson.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        List<Role> roles = new ArrayList<>();
        for (Long roleId : dto.getRoleIds()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Role is not found. Please, check id."
                    ));
            roles.add(role);
        }
        Person person = optionalPerson.get();
        person.setRoles(roles);
        personRepository.save(person);
    }
}
