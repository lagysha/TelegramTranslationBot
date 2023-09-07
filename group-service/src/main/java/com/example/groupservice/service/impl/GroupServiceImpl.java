package com.example.groupservice.service.impl;

import com.example.groupservice.dto.GroupCreateRequest;
import com.example.groupservice.dto.GroupDto;
import com.example.groupservice.exceptions.GroupAlreadyExistsException;
import com.example.groupservice.exceptions.TelegramGroupNotFoundException;
import com.example.groupservice.mapper.GroupMapper;
import com.example.groupservice.model.Group;
import com.example.groupservice.repository.GroupRepository;
import com.example.groupservice.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Value("${bot.token}")
    private String botToken;

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
    public GroupDto saveGroupById(GroupCreateRequest groupCreateRequest) {
        groupRepository.findByGroupId(groupCreateRequest.getGroupId()).ifPresent(user -> {
            throw new GroupAlreadyExistsException("Group with id = " + groupCreateRequest.getGroupId() + " already exists");
        });
        Group retrievedGroup = getTelegramGroup(groupCreateRequest.getGroupId());
        retrievedGroup.setAdminId(groupCreateRequest.getAdminId());
        Group savedGroup = groupRepository.insert(retrievedGroup);
        return GroupMapper.instance.groupToGroupDto(savedGroup);
    }

    private Group getTelegramGroup(Long id) {
        return WebClient.create("https://api.telegram.org/bot" + botToken + "/getChat")
                .get()
                .uri(UriBuilder -> UriBuilder.queryParam("chat_id", id)
                        .build())
                .retrieve()
                .bodyToMono(Group.class)
                .block();
    }
}
