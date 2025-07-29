package Spring_Boot.identity_service.controller;

import Spring_Boot.identity_service.dto.ApiResponse;
import Spring_Boot.identity_service.dto.request.LoginDTO;
import Spring_Boot.identity_service.dto.response.LoginResponse;
import Spring_Boot.identity_service.jwt.JwtUtil;
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
    private final JwtUtil jwtService;

    public Auth(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>>login(@RequestBody LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword());
        Authentication authentication=authenticationManager.authenticate(token);
        String jwt= jwtService.createJwtToken(authentication.getName());
        return ResponseEntity.ok(new ApiResponse<>(200, "Login successful", new LoginResponse(jwt)));
    }

}
