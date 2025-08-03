package Spring_Boot.identity_service.service;

import Spring_Boot.identity_service.dto.request.RoleRequest;
import Spring_Boot.identity_service.dto.response.RoleCreateResponse;
import Spring_Boot.identity_service.entity.Permission;
import Spring_Boot.identity_service.entity.Role;
import Spring_Boot.identity_service.mapper.RoleMapper;
import Spring_Boot.identity_service.repository.PermissionRepository;
import Spring_Boot.identity_service.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository repository,RoleMapper roleMapper,PermissionRepository permissionRepository) {
        this.roleRepository = repository;
        this.roleMapper=roleMapper;
        this.permissionRepository=permissionRepository;
    }
    public List<Role>getAllRole(){
        return roleRepository.findAll();
    }
    public Optional<Role> getRoleById(long id){
        Optional<Role>getRole=roleRepository.findById(id);
        if (getRole.isEmpty()){
            throw new RuntimeException("Ko tim thay role voi id"+id);
        }
        return getRole;
    }
    public RoleCreateResponse createRoleWithPermissions(RoleRequest request){
       Role role=roleRepository.findByName(request.getName());
        if (role == null){
            throw new RuntimeException("Không tìm thấy role: " + request.getName());
        }
        List<Permission>permissions=permissionRepository.findByNameIn(request.getPermissions());
        if (permissions.size() != request.getPermissions().size()) {
            throw new RuntimeException("Một số permission không tồn tại trong hệ thống");
        }
        role.setPermissions(permissions);
        Role roleSave=roleRepository.save(role);
        return roleMapper.toRoleCreateResponse(roleSave);

    }
}
