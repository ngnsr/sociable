package com.rr.sociable.controller;

import com.rr.sociable.dto.MessageDetailsDto;
import com.rr.sociable.dto.MessageDto;
import com.rr.sociable.dto.MessageSmallDto;
import com.rr.sociable.dto.MessageUpdateDto;
import com.rr.sociable.entity.Message;
import com.rr.sociable.exception.InvalidArgumentException;
import com.rr.sociable.exception.NotFoundException;
import com.rr.sociable.mapper.MessageMapper;
import com.rr.sociable.service.MessageProducerService;
import com.rr.sociable.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final MessageProducerService messageProducerService;

    public MessageController(MessageService messageService, MessageMapper messageMapper, MessageProducerService messageProducerService) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
        this.messageProducerService = messageProducerService;
    }

    @GetMapping
    public Page<MessageSmallDto> getAllMessages(@RequestParam Integer page,
                                        @RequestParam Integer size) {
        if(page < 0) throw new InvalidArgumentException("Page number should be >= 0");
        if(size < 1) throw new InvalidArgumentException("Page size should be >= 1");

        final Pageable pageable = PageRequest.of(page,size);
        final Page<Message> messages = messageService.findAll(pageable);
        final List<MessageSmallDto> dtos = messages.get().map(messageMapper::toSmall).toList();
        return new PageImpl<>(dtos, pageable, messages.getTotalElements());
    }

    @GetMapping("/{id}")
    public MessageDetailsDto getMessageById(@PathVariable Long id) {
        Optional<Message> message = messageService.findById(id);
        if(message.isEmpty()) {
            throw new NotFoundException("Message with id %d not found".formatted(id));
        }
        return messageMapper.toDetails(message.orElse(null));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createMessage(@RequestBody @Valid MessageDto message) {
        messageProducerService.sendMessage(message);
    }

    @PutMapping("/{id}")
    public String updateMessage(@PathVariable Long id, @RequestBody @Valid MessageDto message) {
        MessageUpdateDto messageUpdateDto = new MessageUpdateDto(id, message);

        messageProducerService.updateMessage(messageUpdateDto);
        return "Ok";
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageProducerService.deleteMessage(id);
    }

}
