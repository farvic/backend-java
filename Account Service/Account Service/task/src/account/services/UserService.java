package account.services;

import account.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();

    User findUserById(Long id);

    User findUserByEmail(String email);

    List<User> findUsersByName(String name);

    User saveUser(User User);

    User updateUser(Long id, User User);

    void deleteUserById(Long id);

    void deleteUser(User User);
}
