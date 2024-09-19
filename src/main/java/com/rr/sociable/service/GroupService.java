package com.rr.sociable.service;

import com.rr.sociable.entity.Group;
import com.rr.sociable.exception.NotFoundException;
import com.rr.sociable.repo.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Group findById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d not found", id)));
    }

    public Group save(Group group) {
        return groupRepository.save(group);
    }

    public void delete(Long id) {
        groupRepository.deleteById(id);
    }
}
