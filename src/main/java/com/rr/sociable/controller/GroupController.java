package com.rr.sociable.controller;

import com.rr.sociable.dto.*;
import com.rr.sociable.entity.Group;
import com.rr.sociable.entity.Message;
import com.rr.sociable.exception.InvalidArgumentException;
import com.rr.sociable.exception.NotFoundException;
import com.rr.sociable.mapper.GroupMapper;
import com.rr.sociable.mapper.MessageMapper;
import com.rr.sociable.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    private final GroupService groupService;
    private final GroupMapper groupMapper;
    private final MessageMapper messageMapper;

    public GroupController(GroupService groupService, GroupMapper groupMapper, MessageMapper messageMapper) {
        this.groupService = groupService;
        this.groupMapper = groupMapper;
        this.messageMapper = messageMapper;
    }

    @GetMapping
    @Cacheable(value = "groups")
    public List<GroupSmallDto> getAllGroups() {
        System.out.println("getAllGroups()");
        List<Group> groups = groupService.findAll();
        return groups.stream().map(groupMapper::toSmall).toList();
    }

    @GetMapping("/{id}")
    @Cacheable(cacheNames="groups")
    public GroupDetailsDto getGroupById(@PathVariable Long id) {
        Optional<Group> group = groupService.findById(id);
        if (group.isEmpty()) {
            throw new NotFoundException("Group not found");
        }
        return groupMapper.toDetails(group.get());
    }

    @PostMapping
    @CacheEvict(value = "groups", allEntries = true)
    public GroupSmallDto createGroup(@RequestBody @Valid GroupDto group) {
        Optional<Group> savedGroup = groupService.save(group);
        if(savedGroup.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        return groupMapper.toSmall(savedGroup.orElse(null));
    }

    @PostMapping("/{groupId}/members/{userId}")
    public void addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        boolean added = groupService.addUserToGroup(groupId, userId);
        if(!added) throw new NotFoundException("User not found");
    }

    @CacheEvict(value = "groups", allEntries = true)
    @PutMapping("/{id}")
    public GroupSmallDto updateGroup(@PathVariable Long id, @RequestBody @Valid GroupDto group) {
        Optional<Group> saved = groupService.update(id, group);
        if (saved.isEmpty()) {
            throw new NotFoundException("Group not found");
        }
        return groupMapper.toSmall(saved.get());
    }

    @CacheEvict(value = "groups", allEntries = true)
    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        groupService.delete(id);
    }

    @GetMapping("/{id}/members")
    public Set<Long> getMembers(@PathVariable Long id){
        return groupService.getMembers(id);
    }

    @GetMapping("/{id}/messages")
    public Page<MessageSmallDto> getMessagesByGroupId(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                         @RequestParam(required = false, defaultValue = "100") Integer size, @PathVariable Long id){
        if(page < 0) throw new InvalidArgumentException("Page number should be >= 0");
        if(size < 1) throw new InvalidArgumentException("Page size should be >= 1");
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Message> messages = groupService.getMessagesByGroupId(pageRequest, id);
        List<MessageSmallDto> dtos = messages.get()
                .map(messageMapper::toSmall).toList();
        return new PageImpl<>(dtos, pageRequest, messages.getTotalElements());
    }

}
