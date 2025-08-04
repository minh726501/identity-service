package Spring_Boot.identity_service.jwt;

import Spring_Boot.identity_service.entity.RefreshToken;
import Spring_Boot.identity_service.entity.User;
import Spring_Boot.identity_service.repository.UserRepository;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@Component
public class JwtService {
    private final UserRepository userRepository;

    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Value("${jwt.secret}")
    private String key;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public String createAccessToken(User user){
        try {
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer("bqminh")
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + accessTokenExpiration))
                    .claim("role",user.getRole().getName())
                    .build();
            Payload payload = new Payload(jwtClaimsSet.toJSONObject());
            JWSObject jwsObject = new JWSObject(jwsHeader, payload);
            jwsObject.sign(new MACSigner(key.getBytes()));
            return jwsObject.serialize();
        }
         catch (Exception e) {
            throw new RuntimeException("Không tạo được JWT", e);
        }
    }
    public String createRefreshToken(User user){
        try{
            JWSHeader header=new JWSHeader(JWSAlgorithm.HS512);
            JWTClaimsSet jwtClaimsSet=new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer("bqminh")
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis()+refreshTokenExpiration))
                    .claim("type", "refresh")
                    .build();
            Payload payload=new Payload(jwtClaimsSet.toJSONObject());
            JWSObject jwsObject=new JWSObject(header,payload);
            jwsObject.sign(new MACSigner(key.getBytes()));
            return jwsObject.serialize();
        }
        catch (Exception e) {
            throw new RuntimeException("Không tạo được JWT", e);
        }
    }
}
