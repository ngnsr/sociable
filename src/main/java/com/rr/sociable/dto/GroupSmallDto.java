package com.rr.sociable.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class GroupSmallDto implements Serializable {
    private Long id;
    private String name;
    private Long memberCount;
}
