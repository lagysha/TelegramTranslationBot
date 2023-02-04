package com.example.groupservice.service;

import com.example.groupservice.dto.GroupDto;

import java.util.List;

public interface GroupService {
    GroupDto saveGroup(String groupName, Long adminId);

    List<GroupDto> findGroupsByPattern(String name);

    GroupDto findGroupById(Long id);

    GroupDto findGroup(String name);

    List<GroupDto> findGroupByAdminId(Long id);

    String verifyTelegramGroup(String name);

    Long countAllGroups();

    GroupDto saveGroupById(Long id, Long adminId);
}
