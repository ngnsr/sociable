package com.rr.sociable.service;

import com.rr.sociable.dto.UserDto;
import com.rr.sociable.entity.Group;
import com.rr.sociable.entity.User;
import com.rr.sociable.exception.NotFoundException;
import com.rr.sociable.exception.NotImplementedException;
import com.rr.sociable.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final GroupService groupService;

    public UserService(UserRepository userRepository, GroupService groupService) {
        this.userRepository = userRepository;
        this.groupService = groupService;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    public User save(UserDto userDto) {
        User u = new User(userDto);
        return userRepository.save(u);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    // cannot change email
    public User update(UserDto userDto) {
        throw new NotImplementedException();
//        Optional<User> u = userRepository.findUserByEmail(userDto.getEmail());
//        if(u.isEmpty()){
//            throw new NotFoundException("User not found");
//        }
//        User user = u.get();
//
////        if(!user.getEmail().equals(userDto.getEmail())){
////            throw new
////        }
//
//        User newUser = new User(userDto);
//        newUser.se
//        return userRepository.save(newUser);
    }

    public Map<Long, String> getGroups(Long id) {
        Set<Long> groupIds = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"))
                .getGroupsIds();
        List<Group> groups = groupService.findAllById(groupIds);

        return groups.stream().collect(Collectors.toMap(Group::getId, Group::getName));
    }
}
