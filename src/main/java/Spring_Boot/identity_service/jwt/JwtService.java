package Spring_Boot.identity_service.jwt;

import Spring_Boot.identity_service.entity.User;
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
import java.util.Stack;

@Service
@Component
public class JwtService {
    @Value("${jwt.secret}")
    private String key;

    @Value("${jwt.expiration}")
    private long expiration;
    public String createJwtToken(User user){
        try {
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer("bqminh")
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + expiration))
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

}
