package com.hof.cms.cmsuser.controller;

import com.hof.cms.cmsuser.data.CmsUserDto;
import com.hof.cms.cmsuser.data.LoginRequest;
import com.hof.cms.cmsuser.service.CmsUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/cms-users")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CmsUserController {

    private final CmsUserService cmsUserService;

    @GetMapping
    public ResponseEntity<List<CmsUserDto>> getUsers(){
            return ResponseEntity.ok(cmsUserService.getAllUsers());
   }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CmsUserDto> getUser(@PathVariable Long id){
            return ResponseEntity.ok(cmsUserService.getUser(id));
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody @Valid CmsUserDto request){
            cmsUserService.createUser(request);
            return ResponseEntity.accepted().build();
            // todo uri
    }

    @PutMapping("/{id}")
    public ResponseEntity editUser (@RequestBody @Valid CmsUserDto request, @PathVariable Long id){
        cmsUserService.editUser(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
    	// todo return ?
        cmsUserService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok().body(cmsUserService.login(loginRequest));
    }
}
