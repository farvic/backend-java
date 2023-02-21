package account.controllers;


import account.domain.User;
import account.services.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:28852")
@RestController
@RequestMapping("/api")
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl UserService) {
        this.userService = UserService;
    }

    @Operation(summary = "Get all Users", description = "Get all Users, available or not", tags = {
            "User" })
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping()
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @Operation(summary = "Get a User by id", description = "Get a User by id", tags = {
            "User" })
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @Operation(summary = "Get a User by name", description = "Get a User by name", tags = {
            "User" })
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/name/{name}")
    public List<User> getUserByName(@PathVariable String name) {
        return userService.findUsersByName(name);
    }

    @Operation(summary = "Create a User", description = "Create a User", tags = {
            "User" })
    @ApiResponse(responseCode = "200", description = "OK")
    @PostMapping("/auth/signup")
    public User createUser(@RequestBody User User) {
        return userService.saveUser(User);
    }

    @Operation(summary = "Update a User", description = "Update a User", tags = {
            "User" })
    @ApiResponse(responseCode = "204", description = "OK")
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User User) {
        return userService.updateUser(id, User);
    }

    @Operation(summary = "Delete a User", description = "Delete a User by id", tags = {
            "User" })
    @ApiResponse(responseCode = "200", description = "No content")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @Operation(summary = "Delete a User", description = "Delete a User", tags = {
            "User" })
    @ApiResponse(responseCode = "204", description = "No content")
    @DeleteMapping()
    public void deleteUser(@RequestBody User User) {
        userService.deleteUser(User);
    }

}
