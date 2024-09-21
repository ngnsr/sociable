package com.rr.sociable.mapper;

import com.rr.sociable.dto.UserDetailsDto;
import com.rr.sociable.dto.UserSmallDto;
import com.rr.sociable.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDetailsDto toDetails(User user);
    UserSmallDto toSmall(User user);
}
