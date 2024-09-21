package com.rr.sociable.controller;

import com.rr.sociable.dto.GroupDetailsDto;
import com.rr.sociable.dto.GroupDto;
import com.rr.sociable.dto.GroupSmallDto;
import com.rr.sociable.dto.UserSmallDto;
import com.rr.sociable.entity.Group;
import com.rr.sociable.mapper.GroupMapper;
import com.rr.sociable.mapper.MessageMapper;
import com.rr.sociable.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    private final GroupService groupService;
    private final GroupMapper groupMapper;

    public GroupController(GroupService groupService, GroupMapper groupMapper) {
        this.groupService = groupService;
        this.groupMapper = groupMapper;
    }

    @GetMapping
    @Cacheable(value = "groups")
    public List<GroupSmallDto> getAllGroups() {
        System.out.println("in");
        List<Group> groups = groupService.findAll();
        return groups.stream().map(groupMapper::toSmall).toList();
    }

    @GetMapping("/{id}")
    @Cacheable(cacheNames="groups")
    public GroupDetailsDto getGroupById(@PathVariable Long id) {
        Group group = groupService.findById(id);
        return groupMapper.toDetails(group);
    }

    @PostMapping
    @CacheEvict(value = "groups", allEntries = true)
    public GroupSmallDto createGroup(@RequestBody @Valid GroupDto group) {
        Group savedGroup = groupService.save(group);
        return groupMapper.toSmall(savedGroup);
    }

    @PostMapping("/{groupId}/members/{userId}")
    public void addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        groupService.addUserToGroup(groupId, userId);
    }

    @CacheEvict(value = "groups", allEntries = true)
    @PutMapping("/{id}")
    public GroupSmallDto updateGroup(@PathVariable Long id, @RequestBody @Valid GroupDto group) {
        Group saved = groupService.save(group);
        return groupMapper.toSmall(saved);
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

//    @GetMapping("/{id}/messages")
//    public PageDto<MessageDto> getMessagesByGroupId(@RequestParam(required = false, defaultValue = "0") Integer page,
//                                                 @RequestParam(required = false, defaultValue = "100") Integer size, @PathVariable Long id){
//        PageRequest pageRequest = PageRequest.of(page, size);
//        List<MessageDto> messages = groupService.getMessagesByGroupId(id)
//                .stream().map(messageMapper::toDto).toList();
//        return PageDto.from(new PageImpl<>(messages, pageRequest,messages.size()));
//    }

}
