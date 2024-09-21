package com.rr.sociable.service;

import com.rr.sociable.dto.GroupDto;
import com.rr.sociable.dto.MessageSmallDto;
import com.rr.sociable.entity.Group;
import com.rr.sociable.entity.User;
import com.rr.sociable.exception.NotFoundException;
import com.rr.sociable.exception.UserAlreadyInGroupException;
import com.rr.sociable.mapper.GroupMapper;
import com.rr.sociable.repo.GroupRepository;
import com.rr.sociable.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Group findById(Long id) {
         return groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d not found", id)));
    }

    @Transactional
    public void addUserToGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        if(group.getMemberIds().contains(userId)){
           throw new UserAlreadyInGroupException("User already in group");
        }

        group.getMemberIds().add(userId);
        groupRepository.save(group);

        user.getGroupsIds().add(groupId);
        userRepository.save(user);
    }

    @Transactional
    public Group save(GroupDto groupDto) {
        Group group = new Group(groupDto);
        return groupRepository.save(group);
    }

    @Transactional
    public void delete(Long id) {
        groupRepository.deleteById(id);
    }

    public Set<Long> getMembers(Long groupId){
        Group g = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group not found"));
        return g.getMemberIds();
    }

    public List<Group> findAllById(Iterable<Long> groupIds) {
        return groupRepository.findAllById(groupIds);
    }
//
//    public List<MessageSmallDto> getMessagesByGroupId(Long groupId) {
//        Group group = groupRepository.findById(groupId)
//                .orElseThrow(() -> new NotFoundException("Group not found"));
//        return group.getM;
//    }

}
