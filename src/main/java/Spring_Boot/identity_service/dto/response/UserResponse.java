package Spring_Boot.identity_service.dto.response;

import Spring_Boot.identity_service.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
public class UserResponse {
    private long id;
    private String username;
    private String firstName;
    private String email;
    private String lastName;
    private LocalDate dob;
    private Role role;
}
