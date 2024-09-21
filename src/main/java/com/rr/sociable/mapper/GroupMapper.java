package com.rr.sociable.mapper;

import com.rr.sociable.dto.GroupDetailsDto;
import com.rr.sociable.dto.GroupSmallDto;
import com.rr.sociable.entity.Group;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupDetailsDto toDetails(Group group);

    GroupSmallDto toSmall(Group group);
}

