package ru.red.four.businessservice.service;

import io.netty.util.AsyncMapping;
import reactor.core.publisher.Mono;
import ru.red.four.businessservice.domain.User;
import ru.red.four.businessservice.dto.UserDetachedDTO;

public interface UserService {
    Mono<User> createUser(UserDetachedDTO userDetachedDTO);
    Mono<User> updateUsername(String previous_username, String new_username);
    Mono<User> findUser(String username);
    Mono<User> updateUser(UserDetachedDTO userDetachedDTO);
    Mono<Void> deleteUser(String username);
}
