package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.dto.MessageDTO;
import ru.job4j.chat.service.MessageService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Message> findAll() {
        return service.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Message> add(@Valid @RequestBody Message message) {
        return new ResponseEntity<>(this.service.create(message), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public void update(@Valid @RequestBody Message message) {
        this.service.update(message);
    }

    @PatchMapping("/")
    public void patch(@RequestBody MessageDTO dto) {
        this.service.patch(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        this.service.delete(id);
    }
}
