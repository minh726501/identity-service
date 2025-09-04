package Spring_Boot.identity_service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String otp;
    private String newPassword;
}
