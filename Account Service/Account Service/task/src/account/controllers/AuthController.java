package account.controllers;


import account.domain.User;
import account.dto.*;
import account.dto.ResponseBody;
import account.services.AuthServiceImpl;
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
public class AuthController {

    private final AuthServiceImpl userService;

    final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthServiceImpl userService) {
        this.userService = userService;
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



}
