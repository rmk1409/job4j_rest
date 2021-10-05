package ru.job4j.chat.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.dto.MessageDTO;
import ru.job4j.chat.repository.MessageRepository;
import ru.job4j.chat.repository.PersonRepository;
import ru.job4j.chat.repository.RoomRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final PersonRepository personRepository;

    public MessageService(MessageRepository messageRepository, RoomRepository roomRepository, PersonRepository personRepository) {
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
        this.personRepository = personRepository;
    }

    public Optional<Message> findById(@PathVariable long id) {
        return messageRepository.findById(id);
    }

    public List<Message> findAll() {
        return StreamSupport.stream(
                this.messageRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    public Message create(Message message) {
        return this.messageRepository.save(message);
    }

    public void update(Message message) {
        this.messageRepository.save(message);
    }

    public void delete(long id) {
        Message message = new Message();
        message.setId(id);
        this.messageRepository.delete(message);
    }

    public void patch(MessageDTO dto) {
        var OptionalMsg = messageRepository.findById(dto.getId());
        var room = roomRepository.findById(dto.getRoomId());
        var person = personRepository.findById(dto.getPersonId());
        if (OptionalMsg.isEmpty() || room.isEmpty() || person.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Message msg = OptionalMsg.get();
        msg.setRoom(room.get());
        msg.setPerson(person.get());
        messageRepository.save(msg);
    }
}
