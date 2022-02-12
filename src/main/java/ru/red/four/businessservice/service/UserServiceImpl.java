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
        return usersRepository.findByUsername(userDetachedDTO.getUsername())
                .flatMap(entity -> entity == null
                        ? usersRepository.save(userMapper.UserDetachedDTOtoUser(userDetachedDTO))
                        : Mono.error(new UserAlreadyExistsException()))
                .doOnSuccess(s -> log.info("Successfully created user [{}] {}", s.getId(), s.getUsername()))
                .doOnError(e ->
                        log.info(
                                "Failed creating user {} {}",
                                userDetachedDTO.getUsername(),
                                e.getMessage()
                        )
                );
    }

    @Override
    public Mono<User> findUser(Long id) {
        return usersRepository.findById(id);
    }

    @Override
    public Mono<User> findUser(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public Mono<User> updateUser(Long id, UserDetachedDTO userDetachedDTO) {
        User user = userMapper.UserDetachedDTOtoUser(userDetachedDTO);
        user.setId(id);
        return usersRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .flatMap(existingUser -> usersRepository.save(user))
                .doOnSuccess(s -> log.info("Successfully updated user [{}] {}", s.getId(), s.getUsername()))
                .doOnError(e ->
                        log.info(
                                "Failed updating user [{}] {} {}",
                                id,
                                userDetachedDTO.getUsername(),
                                e.getMessage()
                        )
                );
    }

    @Override
    public Mono<Void> deleteUser(Long id) {
        return usersRepository.deleteById(id)
                .doOnSuccess(s -> log.info("Successfully deleted user ID {}", id))
                .doOnError(e -> log.info("Failed deleting user ID {} {}", id, e.getMessage()));
    }
}
