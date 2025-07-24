package Spring_Boot.identity_service.mapper;

import Spring_Boot.identity_service.dto.request.UserCreateRequest;
import Spring_Boot.identity_service.dto.request.UserUpdateRequest;
import Spring_Boot.identity_service.dto.response.UserResponse;
import Spring_Boot.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequest request);
    @Mapping(target = "id", source = "id")
    UserResponse toUserResponse(User user);
    @Mapping(target = "username", ignore = true) // nếu không cho phép sửa username
    void updateUserFromRequest(UserUpdateRequest request,@MappingTarget User user);
    List<UserResponse> toUserResponseList(List<User>users);
}
