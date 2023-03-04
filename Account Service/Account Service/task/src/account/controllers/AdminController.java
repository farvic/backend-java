package account.controllers;



import account.dto.ResponseBody;
import account.dto.UserAccessRequest;
import account.dto.UserDto;
import account.dto.UserRoleRequest;

import account.model.AccessOperation;
import account.services.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "Admin API")
public class AdminController {


    private final AuthServiceImpl userService;

    public AdminController(AuthServiceImpl userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get Users", description = "Get all the users", tags = {
            "Admin" })
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/user")
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @Operation(summary = "Change user roles", description = "Add/remove roles from a user", tags = {
        "Admin" })
    @ApiResponse(responseCode = "200", description = "OK")
    @Transactional
    @PutMapping("/user/role")
    public ResponseEntity<UserDto> changeUserRoles(@Valid @RequestBody UserRoleRequest userRoleRequest) {
        return ResponseEntity.ok(userService.changeUserRole(userRoleRequest));
    }

    @Operation(summary = "Lock/unlock user access", description = "Change user access status", tags = {
            "Admin" })
    @ApiResponse(responseCode = "200", description = "OK")
    @Transactional
    @PutMapping("/user/access")
    public ResponseEntity<ResponseBody> changeUserAccessStatus(@Valid @RequestBody UserAccessRequest userAccessRequest) {

        AccessOperation access = userAccessRequest.getOperation();
        ResponseBody responseBody;

        if (access == AccessOperation.LOCK) {
            responseBody = userService.lock(userAccessRequest.getUser());
        } else {
            responseBody = userService.unlock(userAccessRequest.getUser());
        }

        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "Delete a User", description = "Delete a User by email", tags = {
            "Admmin" })
    @ApiResponse(responseCode = "200", description = "OK")
    @DeleteMapping("/user/{email}")
    public ResponseEntity<ResponseBody> deleteUser(@PathVariable String email) {
        return ResponseEntity.ok(userService.deleteUserByEmail(email));
    }

}
