package ru.red.four.businessservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.red.four.businessservice.domain.User;

public interface UsersRepository extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByUsername(String username);
}
