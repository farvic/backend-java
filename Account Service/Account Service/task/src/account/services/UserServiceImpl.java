package account.services;

import account.domain.User;
import account.errors.UserException;
import account.errors.UserExistsException;
import account.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private Validator validator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Construct UserService
     *
     * @param validator         Validator that enforce entities' constraints
     * @param userRepository    User Repository
     */


    /**
     * Get all Users from the database
     *
     * @return List of Users
     *
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Find a user by id
     *
     * @param id the id of the User
     * @return the User
     * @throws UserException if the User is not found
     *
     */
    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserException("User not found"));
    }

    /**
     * Find all the users by name containing the given string
     *
     * @param name the name of the user
     * @return the list of users
     *
     */
    @Override
    public List<User> findUsersByName(String name) {
        return userRepository.findByNameContaining(name);
    }

    /**
     * Save a User to the database
     *
     * @param user     the name of the User
     * @return User
     *
     */

    // TODO: Should create an UserDto

    @Override
    public User saveUser(User user) {
        // TODO: should check if there's a query for searching strings without caring about case
        boolean alreadyRegisteredEmail = userRepository.findByEmail(user.getEmail().toLowerCase()).isPresent();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty() || alreadyRegisteredEmail) {
            throw new UserExistsException("Bad Request", HttpStatus.BAD_REQUEST, "User exist!");
        }
//        if (!user.getEmail().endsWith("@acme.com")) {
//            throw new UserException("User email must end with @acme.com", HttpStatus.BAD_REQUEST);
//        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));



        return userRepository.save(user);
    }

    /**
     * Update a User
     *
     * @param id        the id of the User     *
     * @param user user     *
     * @return User
     */
    @Override
    public User updateUser(Long id, User user) {
        User _user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found"));

        _user.setName(user.getName());
        _user.setLastname(user.getLastname());
        _user.setEmail(user.getEmail());
        _user.setPassword(user.getPassword());

        return userRepository.save(_user);
    }

    /**
     * Delete a User
     *
     * @param id the id of the User
     *
     */
    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Delete a User
     *
     * @param user user
     *
     */
    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

}
