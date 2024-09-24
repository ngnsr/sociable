package com.rr.sociable.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageDto {
    private String content;
    private Long groupId;
    private Long userId;
}
