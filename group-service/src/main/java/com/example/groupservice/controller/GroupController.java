package com.example.groupservice.controller;

import com.example.groupservice.dto.GroupCreateRequest;
import com.example.groupservice.dto.GroupDto;
import com.example.groupservice.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/groups/v1")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping("{id}")
    public ResponseEntity<GroupDto> findGroupById(@PathVariable Long id)
    {
        return ResponseEntity
                .ok(groupService.findGroupById(id));
    }

    @PostMapping
    public ResponseEntity<GroupDto> saveGroupById(@RequestBody GroupCreateRequest groupCreateRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(groupService.saveGroupById(groupCreateRequest));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getGroupsCount(){
        return ResponseEntity
                .ok(groupService.countAllGroups());
    }
}
