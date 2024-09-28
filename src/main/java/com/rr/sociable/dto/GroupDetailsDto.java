package com.rr.sociable.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GroupDetailsDto extends GroupSmallDto{
    private List<Long> memberIds;
}
