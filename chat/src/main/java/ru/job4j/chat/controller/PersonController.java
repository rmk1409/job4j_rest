package ru.job4j.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.dto.PersonDTO;
import ru.job4j.chat.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable long id) {
        return service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Person is not found. Please, check id."
                ));
    }

    @GetMapping("/")
    public List<Person> findAll() {
        return service.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Person> add(@RequestBody Person person) {
        return new ResponseEntity<>(this.service.create(person), HttpStatus.CREATED);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(new HashMap<>() {
            {
                put("message", e.getMessage());
                put("type", e.getClass());
            }
        }));
    }

    @PutMapping("/")
    public void update(@RequestBody Person person) {
        this.service.update(person);
    }

    @PatchMapping("/")
    public void patch(@RequestBody PersonDTO dto) {
        this.service.patch(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        this.service.delete(id);
    }
}
