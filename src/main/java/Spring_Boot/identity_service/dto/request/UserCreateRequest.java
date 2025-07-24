package Spring_Boot.identity_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class UserCreateRequest {
    @NotBlank(message = "Username not null")
    private String username;
    @NotBlank(message = "Password not null")
    @Size(min = 6,message = "Password phai co 6 ki tu tro len")
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dob;
}
