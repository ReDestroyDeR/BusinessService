package ru.red.four.businessservice.mapper;

import org.springframework.stereotype.Component;
import ru.red.four.businessservice.domain.User;
import ru.red.four.businessservice.dto.UserDetachedDTO;

@Component
public class UserMapper {
    public User UserDetachedDTOtoUser(UserDetachedDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setDescription(dto.getDescription());
        user.setBirthDate(dto.getBirthDate());
        return user;
    }

    public UserDetachedDTO UserToUserDetachedDTO(User user) {
        UserDetachedDTO dto = new UserDetachedDTO();
        dto.setUsername(user.getUsername());
        dto.setDescription(user.getDescription());
        dto.setBirthDate(user.getBirthDate());
        return dto;
    }
}
