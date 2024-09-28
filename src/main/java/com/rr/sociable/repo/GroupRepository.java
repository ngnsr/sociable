package com.rr.sociable.repo;

import com.rr.sociable.dto.GroupDto;
import com.rr.sociable.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> save(GroupDto groupDto);
}
