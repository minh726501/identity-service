package Spring_Boot.identity_service.controller;
import Spring_Boot.identity_service.dto.ApiResponse;
import Spring_Boot.identity_service.dto.request.UserCreateRequest;
import Spring_Boot.identity_service.dto.request.UserUpdateRequest;
import Spring_Boot.identity_service.dto.response.UserResponse;
import Spring_Boot.identity_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserResponse>>createUser(@RequestBody @Valid UserCreateRequest request){
        return ResponseEntity.ok(new ApiResponse<>(200,"Create User Success",userService.createUser(request)));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>>getUserById(@PathVariable long id){
        return ResponseEntity.ok(new ApiResponse<>(200,"Fetch User By Id Success",userService.findUserById(id)));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>>getAllUser(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size){
        return ResponseEntity.ok(userService.getAllUser(page,size));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users")
    public ResponseEntity<ApiResponse<UserResponse>>updateUser(@RequestBody @Valid UserUpdateRequest request){
        return ResponseEntity.ok(new ApiResponse<>(200,"Update User Success",userService.updateUser(request)));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void>deleteUser(@PathVariable long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/myInfo")
    public ResponseEntity<UserResponse>getInfo(){
        return ResponseEntity.ok(userService.getMyInfo());
    }

}
