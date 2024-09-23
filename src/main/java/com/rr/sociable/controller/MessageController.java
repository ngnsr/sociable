package com.rr.sociable.controller;

import com.rr.sociable.dto.MessageDetailsDto;
import com.rr.sociable.dto.MessageDto;
import com.rr.sociable.dto.MessageSmallDto;
import com.rr.sociable.dto.PageDto;
import com.rr.sociable.entity.Message;
import com.rr.sociable.mapper.MessageMapper;
import com.rr.sociable.service.MessageProducerService;
import com.rr.sociable.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.List;

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
    public PageDto<MessageSmallDto> getAllMessages(@RequestParam Integer page,
                                        @RequestParam Integer size) {
        final Pageable pageable = PageRequest.of(page,size);
        final Page<Message> messages = messageService.findAll(pageable);
        final List<MessageSmallDto> dtos = messages.get().map(messageMapper::toSmall).toList();
        return PageDto.from(new PageImpl<>(dtos, pageable, messages.getTotalElements()));
    }

    @GetMapping("/{id}")
    public MessageDetailsDto getMessageById(@PathVariable Long id) {
        Message message = messageService.findById(id);
        return messageMapper.toDetails(message);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createMessage(@RequestBody @Valid MessageDto message) {
        // kafka
        System.err.println("controller");
        messageProducerService.sendMessage(message);
        return "Ok";
//        Message savedMessage = messageService.save(message);
//        return messageMapper.toDetails(savedMessage);
    }

    @PutMapping("/{id}")
    public MessageSmallDto updateMessage(@PathVariable Long id, @RequestBody @Valid MessageDto message) throws Exception {
        throw new Exception();
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageService.delete(id);
    }
}
