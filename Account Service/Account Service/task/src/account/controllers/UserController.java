package account.controllers;


import account.domain.User;
import account.dto.ChangePasswordDto;
import account.dto.ResponseBody;
import account.dto.UserDto;
import account.services.UserServiceImpl;
import account.utils.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:28852")
@RestController
@RequestMapping("/api")
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserServiceImpl userService;

    final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a User", description = "Create a User", tags = {
            "User" })
    @ApiResponse(responseCode = "200", description = "OK")
    @PostMapping("/auth/signup")
    public User createUser(@Valid @RequestBody UserDto userDto) {

        User user = UserMapper.toEntity(userDto);

        return userService.saveUser(user);
    }

    @Operation(summary = "Change password", description = "Changes the user's password", tags = {
            "User" })
    @ApiResponse(responseCode = "200", description = "OK")
    @PostMapping("/auth/changepass")
    public ResponseBody changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto, @AuthenticationPrincipal UserDetails userDetails) {
        return userService.changePassword(changePasswordDto, userDetails);
    }

//    @Operation(summary = "Update a User", description = "Update a User", tags = {
//            "User" })
//    @ApiResponse(responseCode = "204", description = "OK")
//    @PutMapping("/{id}")
//    public User updateUser(@PathVariable Long id, @RequestBody User User) {
//        return userService.updateUser(id, User);
//    }

    @Operation(summary = "Delete a User", description = "Delete a User by id", tags = {
            "User" })
    @ApiResponse(responseCode = "200", description = "No content")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @Operation(summary = "Get a User by email", description = "Get a User by email", tags = {
            "User" })
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }


}
