package ru.red.four.businessservice.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.red.four.businessservice.dto.UserDetachedDTO;
import ru.red.four.businessservice.mapper.UserMapper;
import ru.red.four.businessservice.service.UserService;

@Log4j2
@RestController
@RequestMapping("/")
public class UserController {
    private final UserService userService;
    private final UserMapper mapper;

    @Autowired
    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping
    public Mono<UserDetachedDTO> create(@RequestBody UserDetachedDTO dto) {
        return userService.createUser(dto).map(mapper::UserToUserDetachedDTO);
    }

    @GetMapping("{id}")
    public Mono<UserDetachedDTO> getById(@PathVariable("id") Long id) {
        return userService.findUser(id).map(mapper::UserToUserDetachedDTO);
    }

    @GetMapping("{username}")
    public Mono<UserDetachedDTO> getByUsername(@PathVariable("username") String username) {
        return userService.findUser(username).map(mapper::UserToUserDetachedDTO);
    }

    @PatchMapping("{id}")
    public Mono<UserDetachedDTO> update(@PathVariable("id") Long id, @RequestBody UserDetachedDTO dto) {
        return userService.updateUser(id, dto).map(mapper::UserToUserDetachedDTO);
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }
}
