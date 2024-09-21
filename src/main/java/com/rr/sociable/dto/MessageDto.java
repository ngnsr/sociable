package com.rr.sociable.dto;

import lombok.Data;

@Data
public class MessageDto {
    private String content;
    private Long groupId;
    private Long authorId;
}
