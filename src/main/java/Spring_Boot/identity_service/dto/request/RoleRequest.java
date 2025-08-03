package Spring_Boot.identity_service.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class RoleRequest {
    private String name;
    private List<String>permissions;
}
