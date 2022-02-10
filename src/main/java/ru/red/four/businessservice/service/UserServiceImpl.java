package ru.red.four.businessservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.red.four.businessservice.domain.User;
import ru.red.four.businessservice.dto.UserDetachedDTO;
import ru.red.four.businessservice.mapper.UserMapper;
import ru.red.four.businessservice.repository.UsersRepository;

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
        // TODO: Web Client request to auth service and act only after callback, else, cancel.
        // This should be in a part of single creation pipeline
        throw new UnsupportedOperationException();
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
        // TODO: Test for IF user doesn't exist and we try to update him
        return usersRepository.findById(id).flatMap(existingUser -> usersRepository.save(user));
    }

    @Override
    public Mono<Void> deleteUser(Long id) {
        return usersRepository.deleteById(id);
    }
}
