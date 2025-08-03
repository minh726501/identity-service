package Spring_Boot.identity_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PermissionRequest {
    @NotBlank(message = "Permission name is required")
    private String name;
}
