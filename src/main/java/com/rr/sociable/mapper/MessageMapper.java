package com.rr.sociable.mapper;

import com.rr.sociable.dto.MessageDetailsDto;
import com.rr.sociable.dto.MessageSmallDto;
import com.rr.sociable.entity.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageDetailsDto toDetails(Message message);

    MessageSmallDto toSmall(Message message);
}
