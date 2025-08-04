package Spring_Boot.identity_service.service;

import Spring_Boot.identity_service.entity.RefreshToken;
import Spring_Boot.identity_service.entity.User;
import Spring_Boot.identity_service.jwt.JwtService;
import Spring_Boot.identity_service.repository.RefreshTokenRepository;
import Spring_Boot.identity_service.repository.UserRepository;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RefreshTokenService {
    @Value("${jwt.secret}")
    private String key;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public void deleteRefreshToken(String refreshToken) {
        RefreshToken tokenInDb = refreshTokenRepository.findByToken(refreshToken);
        if (tokenInDb != null) {
            refreshTokenRepository.delete(tokenInDb);
        } else {
            throw new RuntimeException("Refresh token không tồn tại");
        }
    }


    public JWTClaimsSet parseToken(String refreshToken) {
        try {
            //  Giải mã JWT: Header + Payload + Signature
            JWSObject jwsObject = JWSObject.parse(refreshToken);
            //Xác thực chữ ký của refresh token
            JWSVerifier verifier = new MACVerifier(key.getBytes());
            if (!jwsObject.verify(verifier)) {
                throw new RuntimeException("Chữ ký token không hợp lệ");
            }
            //  Lấy Payload (phần dữ liệu)
            return JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
        } catch (Exception e) {
            throw new RuntimeException("Token không hợp lệ", e);
        }
    }

    public String refreshAccessToken(String refreshToken) {
        // 1. Decode token
        JWTClaimsSet jwtClaimsSet = parseToken(refreshToken);
        // 2. Kiểm tra type
        String type = (String) jwtClaimsSet.getClaim("type");
        if (!"refresh".equals(type)) {
            throw new RuntimeException("Token không phải loại refresh");
        }
        // 3. Kiểm tra thời hạn
        Date exp = jwtClaimsSet.getExpirationTime();
        if (exp.before(new Date())) {
            throw new RuntimeException("Refresh token đã hết hạn");
        }
        // 4. Lấy user từ subject
        String username = jwtClaimsSet.getSubject();
        User user = userRepository.findByUsername(username);
        // 5. Kiểm tra token có trong DB không
        RefreshToken tokenInDb = refreshTokenRepository.findByToken(refreshToken);
        if (tokenInDb == null) {
            throw new RuntimeException("Refresh token không tồn tại");
        }
        // 6. Tạo access token mới
        return jwtService.createAccessToken(user);
    }
}
