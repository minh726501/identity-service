package Spring_Boot.identity_service.mapper;
import Spring_Boot.identity_service.dto.response.RoleCreateResponse;
import Spring_Boot.identity_service.entity.Permission;
import Spring_Boot.identity_service.entity.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleCreateResponse toRoleCreateResponse(Role role);
    default List<String>map(List<Permission>permissions){
        return permissions.stream().map(Permission::getName).toList();
    }

}
