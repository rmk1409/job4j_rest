package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.RoomRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RoomService {
    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public Optional<Room> findById(@PathVariable long id) {
        return repository.findById(id);
    }

    public List<Room> findAll() {
        return StreamSupport.stream(
                this.repository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    public Room create(Room room) {
        return this.repository.save(room);
    }

    public void update(Room room) {
        this.repository.save(room);
    }

    public void delete(long id) {
        Room room = new Room();
        room.setId(id);
        this.repository.delete(room);
    }
}
