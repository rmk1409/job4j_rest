package ru.job4j.auth.service;

import org.springframework.stereotype.Service;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PersonService {
    private final PersonRepository persons;

    public PersonService(PersonRepository persons) {
        this.persons = persons;
    }

    public List<Person> findAll() {
        return StreamSupport.stream(
                this.persons.findAll()
                        .spliterator(),
                false
        ).collect(Collectors.toList());
    }

    public Optional<Person> findById(int id) {
        return this.persons.findById(id);
    }

    public Person create(Person person) {
        return this.persons.save(person);
    }

    public void update(Person person) {
        this.persons.save(person);
    }

    public void delete(int id) {
        Person person = new Person();
        person.setId(id);
        this.persons.delete(person);
    }

    public List<Person> findByEmployeeId(int id) {
        return persons.findByEmployeeId(id);
    }
}
