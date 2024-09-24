package com.rr.sociable.entity;

import com.rr.sociable.dto.MessageDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_message")
@ToString
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;

    @CreationTimestamp
    private LocalDateTime timestamp;

    private Long groupId;

    private Long userId;

    public Message(MessageDto messageDto){
        content = messageDto.getContent();
        groupId = messageDto.getGroupId();
        userId = messageDto.getUserId();
    }

}