package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.RoomService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService service;

    public RoomController(RoomService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Room> findAll() {
        return service.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Room> add(@RequestBody Room room) {
        return new ResponseEntity<>(this.service.create(room), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public void update(@Valid @RequestBody Room room) {
        this.service.update(room);
    }

    @PatchMapping("/")
    public void patch(@Valid @RequestBody Room room) {
        this.service.patch(room);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        this.service.delete(id);
    }
}
