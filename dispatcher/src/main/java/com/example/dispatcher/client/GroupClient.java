package com.example.dispatcher.client;

import com.example.dispatcher.dto.group.GroupCreateRequest;
import com.example.dispatcher.dto.group.GroupDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "GROUP-SERVICE", path = "api/groups/v1")
public interface GroupClient {
    @PostMapping
    GroupDto saveGroupById(@RequestBody GroupCreateRequest groupCreateRequest);

    @GetMapping("{id}")
    GroupDto findGroupById(@PathVariable Long id);

    @GetMapping("/count")
    Long getGroupsCount();
}
