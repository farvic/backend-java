package account.services;

import account.domain.User;
import account.dto.ChangePasswordDto;
import account.dto.ResponseBody;
import account.dto.UserDto;
import account.dto.UserRoleRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    List<UserDto> findAllUsers();

//    UserDto findUserById(Long id);

    User findUserByEmail(String email);

    UserDto saveUser(User User);

    UserDto changeUserRole(UserRoleRequest userRoleRequest);

    ResponseBody changePassword(ChangePasswordDto changePasswordDto, UserDetails userDetails);

    ResponseBody deleteUserByEmail(String email);

}
