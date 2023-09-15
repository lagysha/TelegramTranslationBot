package com.example.groupservice.repository;

import com.example.groupservice.model.Group;
import com.example.groupservice.model.Result;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GroupRepositoryTest {

    @Autowired
    GroupRepository groupRepository;

    @Test
    @Order(1)
    @DisplayName("findByGroupId returns group by existing id")
    public void findByGroupId(){
        Group group1 = new Group();
        group1.setAdminId(1L);
        group1.setOk(true);
        Result result = new Result();
        result.setId(1L);
        group1.setResult(result);
        Group groupSaved = groupRepository.insert(group1);
        Group retrievedGroup = groupRepository.findByGroupId(1L).get();
        assertEquals(groupSaved,retrievedGroup);
    }


    @Test
    @Order(2)
    @DisplayName("findByGroupId does not return group by non-existing id")
    public void findByGroupNonExistingId(){
        Group group1 = new Group();
        group1.setAdminId(1L);
        group1.setOk(true);
        Result result = new Result();
        result.setId(1L);
        group1.setResult(result);
        groupRepository.insert(group1);
        assertFalse(groupRepository.findByGroupId(2L).isPresent());
    }
}
