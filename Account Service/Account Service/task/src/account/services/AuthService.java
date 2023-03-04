package account.services;

import account.domain.User;
import account.dto.ChangePasswordDto;
import account.dto.ResponseBody;
import account.dto.UserDto;
import account.dto.UserRoleRequest;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;
import java.util.List;

public interface AuthService {
    List<UserDto> findAllUsers();

//    UserDto findUserById(Long id);

    User findUserByEmail(String email);

    UserDto saveUser(User User);

    UserDto changeUserRole(UserRoleRequest userRoleRequest);

    ResponseBody changePassword(ChangePasswordDto changePasswordDto, UserDetails userDetails);

    ResponseBody deleteUserByEmail(String email);

    @Transactional
    void resetFailedAttempts(String email);

    @Transactional
    void increaseFailedAttempts(String email);

    @Transactional
    ResponseBody lock(String username);

    @Transactional
    ResponseBody unlock(String username);
}
