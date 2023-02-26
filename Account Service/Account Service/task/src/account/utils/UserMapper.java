package account.utils;

import account.domain.User;
import account.dto.UserDto;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setLastname(userDto.getLastname());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setRoles(userDto.getRoles());
        return user;
    }
}
