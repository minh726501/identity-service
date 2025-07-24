package Spring_Boot.identity_service.controller;

import Spring_Boot.identity_service.dto.request.UserCreateRequest;
import Spring_Boot.identity_service.dto.request.UserUpdateRequest;
import Spring_Boot.identity_service.dto.response.UserResponse;
import Spring_Boot.identity_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse>createUser(@RequestBody @Valid UserCreateRequest request){
        return ResponseEntity.ok(userService.createUser(request));
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse>getUserById(@PathVariable long id){
        return ResponseEntity.ok(userService.findUserById(id));
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>>getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }
    @PutMapping("/users")
    public ResponseEntity<UserResponse>updateUser(@RequestBody @Valid UserUpdateRequest request){
        return ResponseEntity.ok(userService.updateUser(request));
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void>deleteUser(@PathVariable long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

}
