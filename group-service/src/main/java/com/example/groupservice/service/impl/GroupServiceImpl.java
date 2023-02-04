package com.example.groupservice.service.impl;

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
    public GroupDto saveGroup(String groupName, Long adminId) {

        if (groupRepository.findByGroupName(groupName).isPresent()) {
            throw new GroupAlreadyExistsException("Group with such user name already exists", null);
        }
        try {
            Group retrievedGroup = WebClient.create("https://api.telegram.org/bot" + botToken + "/getChat")
                    .get()
                    .uri(UriBuilder -> UriBuilder.queryParam("chat_id", "@" + groupName)
                            .build())
                    .retrieve()
                    .bodyToMono(Group.class)
                    .block();

            retrievedGroup.setAdminId(adminId);
            Group savedGroup = groupRepository.insert(retrievedGroup);
            return GroupMapper.instance.groupToGroupDto(savedGroup);
        } catch (WebClientResponseException exception) {
            throw new TelegramGroupNotFoundException("Telegram group with this name not found", exception.getCause());
        }
    }

    @Override
    public List<GroupDto> findGroupsByPattern(String name) {
        return groupRepository.findByGroupNameWithRegex(name)
                .stream()
                .map(GroupMapper.instance::groupToGroupDto)
                .toList();
    }

    @Override
    public GroupDto findGroupById(Long id) {
        Optional<Group> savedGroup = groupRepository.findByGroupId(id);
        return savedGroup.map(GroupMapper.instance::groupToGroupDto).orElse(null);
    }

    @Override
    public GroupDto findGroup(String name) {
        Optional<Group> savedGroup = groupRepository.findByGroupName(name);
        return savedGroup.map(GroupMapper.instance::groupToGroupDto).orElse(null);
    }

    @Override
    public List<GroupDto> findGroupByAdminId(Long id) {
        return groupRepository.findByAdminId(id)
                .stream()
                .map(GroupMapper.instance::groupToGroupDto)
                .toList();
    }

    @Override
    public String verifyTelegramGroup(String name) {
        try {
            Group retrievedGroup1 = WebClient.create("https://api.telegram.org/bot" + botToken + "/getChat")
                    .get()
                    .uri(UriBuilder -> UriBuilder.queryParam("chat_id", "@" + name)
                            .build())
                    .retrieve()
                    .bodyToMono(Group.class)
                    .block();

            System.out.println(retrievedGroup1);

            return "Success";
        } catch (WebClientResponseException exception) {
            throw new TelegramGroupNotFoundException("Telegram group with this name not found", exception.getCause());
        }
    }

    @Override
    public Long countAllGroups() {
        return groupRepository.count();
    }

    @Override
    public GroupDto saveGroupById(Long id, Long adminId) {

        if (groupRepository.findByGroupId(id).isPresent()) {
            throw new GroupAlreadyExistsException("Group with such id already exists", null);
        }

        try {
            Group retrievedGroup = WebClient.create("https://api.telegram.org/bot" + botToken + "/getChat")
                    .get()
                    .uri(UriBuilder -> UriBuilder.queryParam("chat_id", id)
                            .build())
                    .retrieve()
                    .bodyToMono(Group.class)
                    .block();
            retrievedGroup.setAdminId(adminId);
            Group savedGroup = groupRepository.insert(retrievedGroup);
            return GroupMapper.instance.groupToGroupDto(savedGroup);
        } catch (WebClientResponseException exception) {
            throw new TelegramGroupNotFoundException("Telegram group with this name not found", exception.getCause());
        }
    }
}
