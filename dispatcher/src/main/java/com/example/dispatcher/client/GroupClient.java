package com.example.dispatcher.client;

import com.example.dispatcher.dto.group.GroupDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "GROUP-SERVICE", path = "api/groups/v1")
public interface GroupClient {

    @GetMapping("/{groupName}")
    List<GroupDto> getGroup(@PathVariable String groupName);

    @GetMapping()
    List<GroupDto> getGroupByAdminId(@RequestParam(name = "adminId") Long adminId);

    @PostMapping("saveByName/")
    GroupDto saveGroupByName(@RequestParam(name = "groupName") String groupName,
                                                    @RequestParam(name = "adminId") Long adminId);

    @PostMapping("")
    GroupDto saveGroupById(@RequestParam(name = "groupId") Long groupId,
                                                  @RequestParam(name = "adminId") Long adminId);

    @GetMapping("findById/{id}")
    GroupDto findGroupById(@PathVariable Long id);

    @GetMapping("/exact/{name}")
    GroupDto findGroup(@PathVariable String name);

    @GetMapping("/verify/{name}")
    String verifyGroup(@PathVariable String name);

    @GetMapping("/count")
    Long getGroupsCount();
}
