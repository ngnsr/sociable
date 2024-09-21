package com.rr.sociable.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageSmallDto {
    private Long id;
    private String content;
    private Long groupId;
    private Long authorId;
}
