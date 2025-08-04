package Spring_Boot.identity_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RefreshTokenResponse {
    private String accessToken;
}
