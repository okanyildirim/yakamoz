package com.hof.yakamozauth.help.service;
;
import com.hof.yakamozauth.help.service.model.UserRegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    public ApiResponse<UserRegistrationResponse> createUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        UserRegistrationResponse response = userService.createUser(userRegistrationRequest);
        return ApiResponse.of(response);
    }

    @GetMapping("/")
    public ApiResponse<List<UserRegistrationResponse>> getUsers() {
        List<UserRegistrationResponse> response = userService.getAll();
        return ApiResponse.of(response);
    }

    @GetMapping("/getuser/{id}")
    public ApiResponse<User> getUser(@PathVariable Long id) {
        UserRegistrationResponse user = userService.getUser(id);
        return ApiResponse.of(user);
    }

    @PutMapping("/change-password/{id}")
    public ApiResponse<PasswordUpdateResponse> changePassword(@PathVariable Long id, @RequestBody PasswordUpdateRequest pword) {
        PasswordUpdateResponse response = userService.updatePassword(pword, id);
        return ApiResponse.of(response);
    }

    @PutMapping("/{id}")
    public ApiResponse<UserRegistrationResponse> updateUser(@RequestBody UserRegistrationRequest userRegistrationRequest, @PathVariable Long id) {
        UserRegistrationResponse response = userService.updateUser(userRegistrationRequest, id);
        return ApiResponse.of(response);
    }

    @PatchMapping("/{id}")
    public ApiResponse<UserRegistrationResponse> patchUser(@RequestBody UserUpdateRequest userRegistrationRequest, @PathVariable Long id) {
        UserRegistrationResponse response = userService.patchUser(userRegistrationRequest, id);
        return ApiResponse.of(response);
    }

    @PutMapping("/{id}/verify/")
    public ApiResponse<Void> verify(@PathVariable Long id) {
        userService.verifyUser(id);
        return ApiResponse.empty();
    }

    @DeleteMapping("/{id}/verify/")
    public ApiResponse<Void> unverify(@PathVariable Long id) {
        userService.unverifyUser(id);
        return ApiResponse.empty();
    }
}
