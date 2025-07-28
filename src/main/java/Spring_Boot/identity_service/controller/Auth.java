package Spring_Boot.identity_service.controller;

import Spring_Boot.identity_service.dto.ApiResponse;
import Spring_Boot.identity_service.dto.request.LoginDTO;
import org.springframework.http.HttpStatus;
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

    public Auth(AuthenticationManager authentication) {
        this.authenticationManager = authentication;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>>login(@RequestBody LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword());
        Authentication authentication=authenticationManager.authenticate(token);
        return ResponseEntity.ok(new ApiResponse<>(200, "Login successful", null));
    }

}
