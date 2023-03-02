package account.services;

import account.domain.User;
import account.dto.ChangePasswordDto;
import account.dto.ResponseBody;
import account.errors.UserException;
import account.errors.UserExistsException;
import account.repositories.UserRepository;
import account.utils.SecurityChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.validation.Validator;

import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private Validator validator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


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
     * @throws UserException if the User is not found
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
     * @throws UserExistsException if the User already exists
     */

    // TODO: Should create an UserDto

    @Override
    public User saveUser(User user) {

        boolean alreadyRegisteredEmail;


        LOGGER.info("Boy?:" + user.getPassword() );
        if (SecurityChecker.isBreached(user.getPassword())) {
            throw new UserExistsException("Bad Request", HttpStatus.BAD_REQUEST, "The password is in the hacker's database!");
        }

        alreadyRegisteredEmail = userRepository.findByEmail(user.getEmail().toLowerCase()).isPresent();


        if (alreadyRegisteredEmail) {
            throw new UserExistsException("Bad Request", HttpStatus.BAD_REQUEST, "User exist!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());

        return userRepository.save(user);
    }

    /**
     * Changes the user's password
     *
     * @param changePasswordDto   User's password
     * @param userDetails       User's details
     * @return User
     */
    @Override
    public ResponseBody changePassword(ChangePasswordDto changePasswordDto, UserDetails userDetails) {

        ResponseBody responseBody = new ResponseBody();

        if(passwordEncoder.matches(changePasswordDto.getPassword(), userDetails.getPassword())){
            throw new UserExistsException("Bad Request", HttpStatus.BAD_REQUEST, "The passwords must be different!");
        }

        User _user = findUserByEmail(userDetails.getUsername());

        if (SecurityChecker.isBreached(changePasswordDto.getPassword())) {
            throw new UserExistsException("Bad Request", HttpStatus.BAD_REQUEST, "The password is in the hacker's database!");
        }

        _user.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));

        userRepository.save(_user);

        responseBody.setEmail(_user.getEmail());
        responseBody.setStatus("The password has been updated successfully");

        return responseBody;
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
