package com.rr.sociable.service;

import com.rr.sociable.dto.MessageDto;
import com.rr.sociable.entity.Message;
import com.rr.sociable.exception.NotFoundException;
import com.rr.sociable.repo.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Page<Message> findAll(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    public Message findById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Message with id %d not found", id)));
    }

    @Transactional
    public Message save(MessageDto messageDto) {
        Message m = new Message(messageDto);
        return messageRepository.save(m);
    }

    @Transactional
    public void delete(Long id) {
        messageRepository.deleteById(id);
    }
}
