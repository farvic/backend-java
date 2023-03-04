package account.utils;

import account.domain.User;
import account.dto.UserDto;
import account.dto.UserRequest;
import org.springframework.stereotype.Component;

import java.util.List;




@Component
public class UserMapper {
    public static UserDto toDto(User user) {
        List<String> roles = user.getSecurityGroup().stream().map(group -> group.getRole().name()).sorted().toList();
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getEmail(),
                roles
        );
    }

    public static User toEntity(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setLastname(userRequest.getLastname());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setSecurityGroup(userRequest.getSecurityGroup());
        user.setAccountNonLocked(true);
        return user;
    }
}
