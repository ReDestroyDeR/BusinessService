package ru.red.four.businessservice.service;

import reactor.core.publisher.Mono;
import ru.red.four.businessservice.domain.User;
import ru.red.four.businessservice.dto.UserDetachedDTO;

public interface UserService {
    Mono<User> createUser(UserDetachedDTO userDetachedDTO);
    Mono<User> findUser(Long id);
    Mono<User> findUser(String username);
    Mono<User> updateUser(Long id, UserDetachedDTO userDetachedDTO);
    Mono<Boolean> deleteUser(Long id);
}
