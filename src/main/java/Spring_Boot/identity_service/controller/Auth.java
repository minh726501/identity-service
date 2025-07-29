package Spring_Boot.identity_service.controller;

import Spring_Boot.identity_service.dto.ApiResponse;
import Spring_Boot.identity_service.dto.request.LoginDTO;
import Spring_Boot.identity_service.dto.response.LoginResponse;
import Spring_Boot.identity_service.entity.User;
import Spring_Boot.identity_service.jwt.JwtService;
import Spring_Boot.identity_service.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Auth {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public Auth(AuthenticationManager authenticationManager, JwtService jwtUtil,UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtUtil;
        this.userRepository=userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>>login(@RequestBody LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword());
        Authentication authentication=authenticationManager.authenticate(token);
        User user=userRepository.findByUsername(loginDTO.getUsername());
        String jwt= jwtService.createJwtToken(user);
        return ResponseEntity.ok(new ApiResponse<>(200, "Login successful", new LoginResponse(jwt)));
    }

}
