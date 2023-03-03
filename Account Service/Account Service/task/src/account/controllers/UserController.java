package account.controllers;


import account.domain.User;
import account.dto.*;
import account.dto.ResponseBody;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @Operation(summary = "Get a User", description = "Get a User", tags = {
            "User" })
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/admin/user")
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @Operation(summary = "Create a User", description = "Create a User", tags = {
            "User" })
    @ApiResponse(responseCode = "200", description = "OK")
    @PostMapping("/auth/signup")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserRequest userRequest) {

        User user = UserMapper.toEntity(userRequest);

        return ResponseEntity.ok(userService.saveUser(user));
    }

    @Operation(summary = "Change password", description = "Changes the user's password", tags = {
            "User" })
    @ApiResponse(responseCode = "200", description = "OK")
    @PostMapping("/auth/changepass")
    public ResponseBody changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto, @AuthenticationPrincipal UserDetails userDetails) {
        return userService.changePassword(changePasswordDto, userDetails);
    }

    @Transactional
    @PutMapping("/admin/user/role")
    public ResponseEntity<UserDto> setUserRole(@Valid @RequestBody UserRoleRequest userRoleRequest) {
        return ResponseEntity.ok(userService.changeUserRole(userRoleRequest));
    }

    @Operation(summary = "Delete a User", description = "Delete a User by id", tags = {
            "User" })
    @ApiResponse(responseCode = "200", description = "No content")
    @DeleteMapping("/admin/user/{email}")
    public ResponseEntity<ResponseBody> deleteUser(@PathVariable String email) {
        return ResponseEntity.ok(userService.deleteUserByEmail(email));
    }

    @Operation(summary = "Get a User by email", description = "Get a User by email", tags = {
            "User" })
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }


}
