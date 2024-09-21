package com.rr.sociable.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rr.sociable.dto.MessageDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;

    @CreationTimestamp
    private LocalDateTime timestamp;

    private Long groupId;

    private Long authorId;

    public Message(MessageDto messageDto){
        content = messageDto.getContent();
    }

}