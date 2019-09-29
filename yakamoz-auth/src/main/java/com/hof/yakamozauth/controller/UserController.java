package com.hof.yakamozauth.controller;

import com.hof.yakamozauth.data.LoginRequest;
import com.hof.yakamozauth.data.UserDto;
import com.hof.yakamozauth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/auth/users")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final  UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
            return ResponseEntity.ok(userService.getAllUsers());
   }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
            return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody @Valid UserDto request){
            userService.createUser(request);
            return ResponseEntity.accepted().build();
            // todo uri
    }

    @PutMapping("/{id}")
    public ResponseEntity editUser (@RequestBody @Valid UserDto request, @PathVariable Long id){
        userService.editUser(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
    	// todo return ?
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok().body(userService.login(loginRequest));
    }
}
