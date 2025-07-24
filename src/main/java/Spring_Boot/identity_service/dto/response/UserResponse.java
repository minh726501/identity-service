package Spring_Boot.identity_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
public class UserResponse {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dob;
}
