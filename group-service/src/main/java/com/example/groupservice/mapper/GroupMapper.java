package com.example.groupservice.mapper;

import com.example.groupservice.dto.GroupDto;
import com.example.groupservice.model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupMapper instance = Mappers.getMapper(GroupMapper.class);

    GroupDto groupToGroupDto(Group group);
    Group groupDtoToGroup(GroupDto groupDto);
}
