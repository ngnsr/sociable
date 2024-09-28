package com.rr.sociable.service;

import com.rr.sociable.dto.GroupDto;
import com.rr.sociable.entity.Group;
import com.rr.sociable.entity.Message;
import com.rr.sociable.entity.User;
import com.rr.sociable.exception.NotFoundException;
import com.rr.sociable.repo.GroupRepository;
import com.rr.sociable.repo.MessageRepository;
import com.rr.sociable.repo.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository, MessageRepository messageRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Optional<Group> findById(Long id) {
         return groupRepository.findById(id);
    }

    @Transactional
    public boolean addUserToGroup(Long groupId, Long userId) {
        Optional<Group> g = groupRepository.findById(groupId);
        Optional<User> u = userRepository.findById(userId);
        if (g.isEmpty() || u.isEmpty()) {
            return false;
        }

        Group group = g.get();

        if(group.getMemberIds().contains(userId)){
            return false;
        }

        group.getMemberIds().add(userId);
        groupRepository.save(group);

        User user = u.get();

        user.getGroupsIds().add(groupId);
        userRepository.save(user);
        return true;
    }

    @Transactional
    public Optional<Group> save(GroupDto groupDto) {
        Group group = new Group(groupDto);
        group.setMemberCount(1);
        Optional<User> u = userRepository.findById(groupDto.getCreatorId());

        if(u.isEmpty()) return Optional.empty();

        User user = u.get();

        group.getMemberIds().add(user.getId());

        Group saved = groupRepository.save(group);
        user.getGroupsIds().add(saved.getId());

        userRepository.save(user);

        return Optional.of(saved);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Group> g = groupRepository.findById(id);
        if(g.isEmpty()) return;

        Group group = g.get();
        List<User> users = userRepository.findAllById(group.getMemberIds());
        users.forEach(u -> u.getGroupsIds().remove(group.getId()));
        userRepository.saveAll(users);

        messageRepository.deleteMessageByGroupId(id);

        groupRepository.deleteById(id);
    }

    public Set<Long> getMembers(Long groupId){
        Group g = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group not found"));
        return g.getMemberIds();
    }

    public List<Group> findAllById(Iterable<Long> groupIds) {
        return groupRepository.findAllById(groupIds);
    }

    public Page<Message> getMessagesByGroupId(Pageable pageable, Long groupId) {
        groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));

        return messageRepository.findAllByGroupId(pageable, groupId);
    }

    public Optional<Group> update(Long id, GroupDto group) {
        Optional<Group> g = groupRepository.findById(id);
        if (g.isEmpty()) return Optional.empty();
        Group findedGroup = g.get();
        findedGroup.setName(group.getName());
        return Optional.of(groupRepository.save(findedGroup));
    }
}
