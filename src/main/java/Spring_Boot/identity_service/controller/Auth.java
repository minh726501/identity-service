package Spring_Boot.identity_service.controller;

import Spring_Boot.identity_service.dto.ApiResponse;
import Spring_Boot.identity_service.dto.request.LoginDTO;
import Spring_Boot.identity_service.dto.request.RefreshTokenRequest;
import Spring_Boot.identity_service.dto.response.LoginResponse;
import Spring_Boot.identity_service.dto.response.RefreshTokenResponse;
import Spring_Boot.identity_service.entity.RefreshToken;
import Spring_Boot.identity_service.entity.User;
import Spring_Boot.identity_service.jwt.JwtService;
import Spring_Boot.identity_service.repository.RefreshTokenRepository;
import Spring_Boot.identity_service.repository.UserRepository;
import Spring_Boot.identity_service.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class Auth {
    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    public Auth(AuthenticationManager authenticationManager, JwtService jwtUtil,UserRepository userRepository,RefreshTokenRepository refreshTokenRepository,RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtUtil;
        this.userRepository=userRepository;
        this.refreshTokenRepository=refreshTokenRepository;
        this.refreshTokenService=refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>>login(@RequestBody LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword());
        Authentication authentication=authenticationManager.authenticate(token);
        User user=userRepository.findByUsername(loginDTO.getUsername());
        String accessToken= jwtService.createAccessToken(user);
        String refreshToken= jwtService.createRefreshToken(user);
        // Lưu refresh token vào DB
        RefreshToken refresh=new RefreshToken();
        refresh.setUser(user);
        refresh.setToken(refreshToken);
        refresh.setExpiryDate(new Date(System.currentTimeMillis()+refreshTokenExpiration));
        refreshTokenRepository.save(refresh);
        return ResponseEntity.ok(new ApiResponse<>(200, "Login successful", new LoginResponse(accessToken,refreshToken)));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>>refreshToken(@RequestBody RefreshTokenRequest refreshToken){
        String newAccessToken=refreshTokenService.refreshAccessToken(refreshToken.getRefreshToken());
        return ResponseEntity.ok(new ApiResponse<>(200,"Access token refreshed", new RefreshTokenResponse(newAccessToken)));
    }
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>>logout(@RequestBody RefreshTokenRequest request){
        String refreshToken = request.getRefreshToken();
        refreshTokenService.deleteRefreshToken(refreshToken);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),"Logout successful",null));
    }

}
