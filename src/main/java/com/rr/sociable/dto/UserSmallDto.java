package com.rr.sociable.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSmallDto {
    private Long id;
    private String username;
    private String email;
    private String password;
}
