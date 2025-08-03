package Spring_Boot.identity_service.mapper;

import Spring_Boot.identity_service.dto.request.PermissionRequest;
import Spring_Boot.identity_service.dto.response.PermissionResponse;
import Spring_Boot.identity_service.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
    void updatePermissionFromRequest(PermissionRequest request, @MappingTarget Permission permission);
    List<PermissionResponse>toPermissionResponseList(List<Permission>permissions);
}
