package Spring_Boot.identity_service.controller;

import Spring_Boot.identity_service.dto.request.RoleRequest;
import Spring_Boot.identity_service.dto.response.RoleCreateResponse;
import Spring_Boot.identity_service.entity.Role;
import Spring_Boot.identity_service.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @GetMapping("/roles")
    public List<Role>getAllRole(){
        return roleService.getAllRole();
    }
    @GetMapping("/roles/{id}")
    public Optional<Role> getAllRole(@PathVariable long id){
        return roleService.getRoleById(id);
    }
    @PostMapping("/roles")
    public ResponseEntity<RoleCreateResponse>createRoleWithPermissions(@RequestBody RoleRequest request){
        return ResponseEntity.ok(roleService.createRoleWithPermissions(request));
    }
}
