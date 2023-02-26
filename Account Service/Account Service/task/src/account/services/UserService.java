package account.services;

import account.domain.User;
import account.dto.ChangePasswordDto;
import account.dto.ResponseBody;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();

    User findUserById(Long id);

    User findUserByEmail(String email);

    List<User> findUsersByName(String name);

    User saveUser(User User);

    ResponseBody changePassword(ChangePasswordDto changePasswordDto, UserDetails userDetails);

    void deleteUserById(Long id);

    void deleteUser(User User);
}
