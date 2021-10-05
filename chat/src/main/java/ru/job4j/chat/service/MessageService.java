package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.repository.MessageRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MessageService {
    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public Optional<Message> findById(@PathVariable long id) {
        return repository.findById(id);
    }

    public List<Message> findAll() {
        return StreamSupport.stream(
                this.repository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    public Message create(Message message) {
        return this.repository.save(message);
    }

    public void update(Message message) {
        this.repository.save(message);
    }

    public void delete(long id) {
        Message message = new Message();
        message.setId(id);
        this.repository.delete(message);
    }
}
