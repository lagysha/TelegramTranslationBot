package com.example.groupservice.controller;

import com.example.groupservice.dto.GroupDto;
import com.example.groupservice.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/groups/v1")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    @GetMapping("/{name}")
    public ResponseEntity<List<GroupDto>> findGroupsByPattern(@PathVariable String name)
    {
        return ResponseEntity
                .ok(groupService.findGroupsByPattern(name));
    }

    @GetMapping("/exact/{name}")
    public ResponseEntity<GroupDto> findGroup(@PathVariable String name)
    {
        return ResponseEntity
                .ok(groupService.findGroup(name));
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<GroupDto> findGroupById(@PathVariable Long id)
    {
        return ResponseEntity
                .ok(groupService.findGroupById(id));
    }

    @PostMapping("saveByName/")
    public ResponseEntity<GroupDto> saveGroupByName(@RequestParam(name = "groupName") String groupName,
                                                 @RequestParam(name = "adminId") Long adminId){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(groupService.saveGroup(groupName,adminId));
    }

    @PostMapping("")
    public ResponseEntity<GroupDto> saveGroupById(@RequestParam(name = "groupId") Long groupId,
                                                    @RequestParam(name = "adminId") Long adminId){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(groupService.saveGroupById(groupId,adminId));
    }

    @GetMapping("/verify/{name}")
    public ResponseEntity<String> verifyGroup(@PathVariable String name)
    {
        return ResponseEntity
                .ok(groupService.verifyTelegramGroup(name));
    }

    @GetMapping()
    public ResponseEntity<List<GroupDto>> getGroupByAdminId(@RequestParam(name = "adminId") Long adminId){
        return ResponseEntity
                .ok(groupService.findGroupByAdminId(adminId));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getGroupsCount(){
        return ResponseEntity
                .ok(groupService.countAllGroups());
    }
}
