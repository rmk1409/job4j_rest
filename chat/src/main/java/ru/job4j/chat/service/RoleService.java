package ru.job4j.chat.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RoleService {
    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Optional<Role> findById(@PathVariable long id) {
        return repository.findById(id);
    }

    public List<Role> findAll() {
        return StreamSupport.stream(
                this.repository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    public Role create(Role role) {
        return this.repository.save(role);
    }

    public void update(Role role) {
        this.repository.save(role);
    }

    public void delete(long id) {
        Role role = new Role();
        role.setId(id);
        this.repository.delete(role);
    }

    public void patch(Role role) {
        repository.findById(role.getId()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Role is not found. Please, check id."
        ));
        update(role);
    }
}
