package ru.red.four.businessservice.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.red.four.businessservice.domain.User;
import ru.red.four.businessservice.dto.UserDetachedDTO;
import ru.red.four.businessservice.exception.NotFoundException;
import ru.red.four.businessservice.exception.UserAlreadyExistsException;
import ru.red.four.businessservice.mapper.UserMapper;
import ru.red.four.businessservice.repository.UsersRepository;

@Log4j2
@Service
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, UserMapper userMapper) {
        this.usersRepository = usersRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Mono<User> createUser(UserDetachedDTO userDetachedDTO) {
        return usersRepository.existsByUsername(userDetachedDTO.getUsername())
                .flatMap(b -> b
                        ? Mono.error(new UserAlreadyExistsException())
                        : usersRepository.save(userMapper.UserDetachedDTOtoUser(userDetachedDTO)))
                .doOnSuccess(s -> log.info("Successfully created user [{}] {}", s.getId(), s.getUsername()))
                .doOnError(e ->
                        log.info(
                                "Failed creating user {} {}",
                                userDetachedDTO.getUsername(),
                                e
                        )
                );
    }

    @Override
    public Mono<User> updateUsername(String previous_username, String new_username) {
        return usersRepository.findByUsername(previous_username)
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .flatMap(user -> {
                    user.setUsername(new_username);
                    return usersRepository.save(user).onErrorMap(UserAlreadyExistsException::new);
                })
                .doOnSuccess(s ->
                        log.info(
                                "Successfully updated username [{}] {} -> {}",
                                s.getId(),
                                previous_username,
                                s.getUsername()
                        )
                )
                .doOnError(e ->
                        log.info(
                                "Failed updating username {} -> {} {}",
                                previous_username,
                                new_username,
                                e
                        )
                );
    }

    @Override
    public Mono<User> findUser(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public Mono<User> updateUser(UserDetachedDTO userDetachedDTO) {
        User user = userMapper.UserDetachedDTOtoUser(userDetachedDTO);
        return usersRepository.findByUsername(user.getUsername())
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .flatMap(existingUser -> usersRepository.save(user.enrichWithId(existingUser.getId())))
                .doOnSuccess(s -> log.info("Successfully updated user [{}] {}", s.getId(), s.getUsername()))
                .doOnError(e ->
                        log.info(
                                "Failed updating user {} {}",
                                userDetachedDTO.getUsername(),
                                e
                        )
                );
    }

    @Override
    public Mono<Void> deleteUser(String username) {
        return usersRepository.deleteByUsername(username)
                .doOnSuccess(s -> log.info("Successfully deleted user {}", username))
                .doOnError(e -> log.info("Failed deleting user {} {}", username, e));
    }
}
