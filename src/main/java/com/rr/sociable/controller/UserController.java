package com.rr.sociable.controller;

import com.rr.sociable.dto.UserDetailsDto;
import com.rr.sociable.dto.UserDto;
import com.rr.sociable.dto.UserSmallDto;
import com.rr.sociable.entity.User;
import com.rr.sociable.exception.ConflictException;
import com.rr.sociable.exception.NotFoundException;
import com.rr.sociable.mapper.UserMapper;
import com.rr.sociable.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserSmallDto> getAllUsers() {
        List<User> users = userService.findAll();
        return users.stream().map(userMapper::toSmall).toList();
    }

    @GetMapping("/{id}")
    public UserDetailsDto getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        if(user.isEmpty()) throw new NotFoundException("User not found");

        return userMapper.toDetails(user.orElse(null));
    }

    @PostMapping
    public UserSmallDto createUser(@RequestBody @Valid UserDto userDto) {
        Optional<User> optional = userService.findByEmail(userDto.getEmail());

        if (optional.isPresent()) {
            throw new ConflictException("Client already exists");
        }

        User saved = userService.save(userDto);
        return userMapper.toSmall(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}/groups")
    public Map<Long, String> getGroups(@PathVariable Long id){
        return userService.getGroups(id);
    }
}
