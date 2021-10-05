package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Role> findAll() {
        return service.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Role> add(@RequestBody Role role) {
        return new ResponseEntity<>(this.service.create(role), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public void update(@RequestBody Role role) {
        this.service.update(role);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        this.service.delete(id);
    }
}
