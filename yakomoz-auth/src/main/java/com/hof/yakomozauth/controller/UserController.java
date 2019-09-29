package com.hof.yakomozauth.controller;

import com.hof.yakomozauth.data.LoginRequest;
import com.hof.yakomozauth.data.UserRegistrationRequest;
import com.hof.yakomozauth.data.UserRequest;
import com.hof.yakomozauth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RestController
@RequestMapping("/auth/users")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final  UserService userService;

    @GetMapping
    public ResponseEntity getUsers(){
            return ResponseEntity.ok(userService.getAllUsers());
   }

    @GetMapping(value = "/{id}")
    public ResponseEntity getUser(@PathVariable Long id){
            return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody @Valid UserRegistrationRequest request){
            userService.createUser(request);
            return ResponseEntity.accepted().build();
            // uri
    }

    @PutMapping("/{id}")
    public ResponseEntity editUser (@RequestBody @Valid UserRequest request, @PathVariable Long id){
        userService.editUser(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok().body(userService.login(loginRequest));
    }
}
