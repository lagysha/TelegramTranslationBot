package com.example.groupservice.service;


import com.example.groupservice.client.TelegramClient;
import com.example.groupservice.dto.GroupCreateRequest;
import com.example.groupservice.dto.GroupDto;
import com.example.groupservice.exceptions.GroupAlreadyExistsException;
import com.example.groupservice.mapper.GroupMapper;
import com.example.groupservice.model.Group;
import com.example.groupservice.model.Result;
import com.example.groupservice.repository.GroupRepository;
import com.example.groupservice.service.impl.GroupServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GroupServiceTest {

    @Mock
    GroupRepository groupRepository;

    @Mock
    TelegramClient telegramClient;

    @InjectMocks
    GroupServiceImpl groupService;

    Group testGroup;

    @BeforeEach
    public void populateGroup(){
        testGroup = new Group();
        testGroup.setAdminId(1L);
        testGroup.setId("1");
        testGroup.setOk(true);
        Result result = new Result();
        result.setId(1L);
        testGroup.setResult(result);
    }

    @Test
    @Order(1)
    @DisplayName("findGroupById returns group by existing id")
    public void findGroupById() {
        Mockito.when(groupRepository.findByGroupId(1L)).thenReturn(Optional.of(testGroup));
        GroupDto actualGroupDto = groupService.findGroupById(1L);
        assertEquals(GroupMapper.instance.groupToGroupDto(testGroup)
                ,actualGroupDto);
    }

    @Test
    @Order(2)
    @DisplayName("findGroupById does not return group by non-existing id")
    public void findGroupByNonExistingId() {
        Mockito.when(groupRepository.findByGroupId(2L)).thenReturn(Optional.empty());
        GroupDto actualGroupDto = groupService.findGroupById(2L);
        assertNull(actualGroupDto);
    }

    @Test
    @Order(3)
    @DisplayName("count return number of all groups")
    public void countAllGroups() {
        Mockito.when(groupRepository.count()).thenReturn(10L);
        var expected = 10L;
        assertEquals(expected,groupService.countAllGroups());
    }

    @Test
    @Order(4)
    @DisplayName("saveGroupById returns group by non-existing id")
    public void saveGroupById() {
        GroupCreateRequest createRequest = new GroupCreateRequest(2L,2L);
        Mockito.when(groupRepository.findByGroupId(2L)).thenReturn(Optional.empty());
        testGroup.setId(null);
        Mockito.when(telegramClient.getTelegramGroup(2L)).thenReturn(testGroup);
        Mockito.when(groupRepository.insert(testGroup)).thenReturn(testGroup);
        testGroup.setId("1");

        GroupDto actualGroupDto = groupService.saveGroupById(createRequest);

        assertEquals(GroupMapper.instance.groupToGroupDto(testGroup)
                ,actualGroupDto);
    }

    @Test
    @Order(5)
    @DisplayName("saveGroupById does not return group by non-existing id")
    public void saveGroupByNonExistingId() {
        GroupCreateRequest createRequest = new GroupCreateRequest(1L,1L);
        Mockito.when(groupRepository.findByGroupId(1L)).thenReturn(Optional.of(testGroup));

        GroupAlreadyExistsException thrown = assertThrows(
                GroupAlreadyExistsException.class,
                () -> groupService.saveGroupById(createRequest)
        );

        assertEquals(thrown.getMessage(),"Group with id = " + createRequest.getGroupId() + " already exists");
    }
}
