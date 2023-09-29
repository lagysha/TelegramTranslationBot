package com.example.groupservice.service;

import com.example.groupservice.dto.GroupCreateRequest;
import com.example.groupservice.dto.GroupDto;

public interface GroupService {
    GroupDto findGroupById(Long id);
    Long countAllGroups();
    GroupDto saveGroupById(GroupCreateRequest groupCreateRequest);
}
