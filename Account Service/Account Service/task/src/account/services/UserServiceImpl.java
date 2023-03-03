package account.services;

import account.domain.Group;
import account.domain.Role;
import account.domain.User;
import account.dto.ChangePasswordDto;
import account.dto.ResponseBody;
import account.dto.UserDto;
import account.dto.UserRoleRequest;
import account.errors.UserException;
import account.errors.UserExistsException;
import account.model.Operation;

import account.repositories.UserRepository;
import account.utils.SecurityChecker;
import account.utils.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.validation.Validator;

import java.util.Arrays;
import java.util.HashSet;
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

    final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private GroupServiceImpl groupService;


    /**
     * Get all Users from the database
     *
     * @return List of Users
     *
     */
    @Override
    public List<UserDto> findAllUsers() {
        LOGGER.info("Getting all users");
        return userRepository.findAll().stream().map(UserMapper::toDto).toList();
    }

//    /**
//     * Find a user by id
//     *
//     * @param id the id of the User
//     * @return the User
//     * @throws UserException if the User is not found
//     *
//     */
//    @Override
//    public UserDto findUserById(Long id) {
//        return userRepository.findById(id).orElseThrow(() -> new UserException("User not found"));
//    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase()).orElseThrow(() -> new UserExistsException("Not Found",HttpStatus.NOT_FOUND,"User not found!"));
    }

    /**
     * Save a User to the database
     *
     * @param user     the name of the User
     * @return User
     * @throws UserExistsException if the User already exists
     */

    // TODO: Should create an UserRequest

    @Override
    public UserDto saveUser(User user) {

        boolean alreadyRegisteredEmail;

        if (SecurityChecker.isBreached(user.getPassword())) {
            throw new UserExistsException("Bad Request", HttpStatus.BAD_REQUEST, "The password is in the hacker's database!");
        }

        alreadyRegisteredEmail = userRepository.findByEmail(user.getEmail().toLowerCase()).isPresent();


        if (alreadyRegisteredEmail) {
            throw new UserExistsException("Bad Request", HttpStatus.BAD_REQUEST, "User exist!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());

        boolean _userExists = userRepository.findById(1).isPresent();

        Group group = groupService.getGroupByRole(_userExists ? Role.ROLE_USER : Role.ROLE_ADMINISTRATOR);

        user.getSecurityGroup().add(group);

        return UserMapper.toDto(userRepository.save(user));
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
     * Changes the user's role
     *
     * @param userRoleRequest   User's role
     * @return User
     */
    @Override
    public UserDto changeUserRole(UserRoleRequest userRoleRequest) {

        LOGGER.info("{} user role for user: {} to role: {}", userRoleRequest.getOperation(),userRoleRequest.getUser(), userRoleRequest.getRole());

        if (Arrays.stream(Role.values()).noneMatch(role -> role.name().equals("ROLE_" + userRoleRequest.getRole()))) {
            throw new UserExistsException("Not Found", HttpStatus.NOT_FOUND, "Role not found!");
        }

        Role roleFromRequest = Role.valueOf("ROLE_" + userRoleRequest.getRole());
        Operation operation = userRoleRequest.getOperation();

        if (roleFromRequest == Role.ROLE_ADMINISTRATOR) {
            if (operation == Operation.REMOVE) {
                throw new UserExistsException("Bad Request", HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
            }
            throw new UserExistsException("Bad Request", HttpStatus.BAD_REQUEST, "The user cannot combine administrative and business roles!");
        }

        User _user = findUserByEmail(userRoleRequest.getUser());
        Set<Group> userSecurityGroups = new HashSet<>(_user.getSecurityGroup());

        if (operation == Operation.REMOVE) {

            if (userSecurityGroups.stream().noneMatch(group -> group.getRole() == roleFromRequest)) {
                throw new UserExistsException("Bad Request", HttpStatus.BAD_REQUEST, "The user does not have a role!");
            }

            if (userSecurityGroups.size() == 1) {
                throw new UserExistsException("Bad Request", HttpStatus.BAD_REQUEST, "The user must have at least one role!");
            }
//            if (_user.getSecurityGroup().stream().findAny(group -> group.(new Group(roleFromRequest)))) {
//                throw new UserExistsException("Bad Request", HttpStatus.BAD_REQUEST, "The user cannot combine administrative and business roles!");
//            }

            userSecurityGroups.removeIf(group -> group.getRole() == roleFromRequest);
        }
        else{
            if (userSecurityGroups.stream().anyMatch(group -> group.getRole() == Role.ROLE_ADMINISTRATOR)) {
                throw new UserExistsException("Bad Request", HttpStatus.BAD_REQUEST, "The user cannot combine administrative and business roles!");
            }
            userSecurityGroups.add(groupService.getGroupByRole(roleFromRequest));

        }


        _user.setSecurityGroup(userSecurityGroups);

        return UserMapper.toDto(userRepository.save(_user));
    }


    /**
     * Delete a User
     *
     * @param email         the email of the User
     *
     */
    @Override
    public ResponseBody deleteUserByEmail(String email) {

        ResponseBody responseBody = new ResponseBody();

        User _user = findUserByEmail(email);

        if (_user.getId() == 1) {
            throw new UserExistsException("Bad Request", HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
        }

        userRepository.delete(_user);

        responseBody.setUser(email);
        responseBody.setStatus("Deleted successfully!");
        return responseBody;
    }


}
