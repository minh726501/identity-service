package Spring_Boot.identity_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class RoleCreateResponse {
    private String name;
    private List<String> permissions;
}
