package ru.job4j.chat.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.repository.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PersonService {
    private final PersonRepository repository;
    private final BCryptPasswordEncoder encoder;

    public PersonService(PersonRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public Optional<Person> findById(@PathVariable long id) {
        return repository.findById(id);
    }

    public List<Person> findAll() {
        return StreamSupport.stream(
                this.repository.findAll().spliterator(), false
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
        return this.repository.save(person);
    }

    public void update(Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        this.repository.save(person);
    }

    public void delete(long id) {
        Person person = new Person();
        person.setId(id);
        this.repository.delete(person);
    }
}
