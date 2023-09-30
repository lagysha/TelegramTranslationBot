package com.example.groupservice.service.impl;

import com.example.groupservice.client.TelegramClient;
import com.example.groupservice.dto.GroupCreateRequest;
import com.example.groupservice.dto.GroupDto;
import com.example.groupservice.exceptions.GroupAlreadyExistsException;
import com.example.groupservice.mapper.GroupMapper;
import com.example.groupservice.model.Group;
import com.example.groupservice.repository.GroupRepository;
import com.example.groupservice.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final TelegramClient telegramClient;


    @Override
    public GroupDto findGroupById(Long id) {
        Optional<Group> savedGroup = groupRepository.findByGroupId(id);
        return savedGroup.map(GroupMapper.instance::groupToGroupDto)
                .orElse(null);
    }

    @Override
    public Long countAllGroups() {
        return groupRepository.count();
    }

    @Override
    @Transactional
    public GroupDto saveGroupById(GroupCreateRequest groupCreateRequest) {
        groupRepository.findByGroupId(groupCreateRequest.getGroupId()).ifPresent(group -> {
            throw new GroupAlreadyExistsException("Group with id = " + groupCreateRequest.getGroupId() + " already exists");
        });
        Group retrievedGroup = telegramClient.getTelegramGroup(groupCreateRequest.getGroupId());
        retrievedGroup.setAdminId(groupCreateRequest.getAdminId());
        Group savedGroup = groupRepository.insert(retrievedGroup);
        return GroupMapper.instance.groupToGroupDto(savedGroup);
    }
}
