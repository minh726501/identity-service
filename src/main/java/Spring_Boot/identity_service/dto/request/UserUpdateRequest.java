package Spring_Boot.identity_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class UserUpdateRequest {
    private long id;
    @NotBlank(message = "Password not null")
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dob;
}
