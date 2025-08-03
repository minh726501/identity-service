package Spring_Boot.identity_service.controller;

import Spring_Boot.identity_service.dto.request.PermissionRequest;
import Spring_Boot.identity_service.dto.response.PermissionResponse;
import Spring_Boot.identity_service.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService){
        this.permissionService = permissionService;
    }
    @PostMapping("/permissions")
    public ResponseEntity<PermissionResponse>createPer(@RequestBody PermissionRequest request){
        return ResponseEntity.ok(permissionService.createPer(request));
    }
    @GetMapping("/permissions")
    public ResponseEntity<List<PermissionResponse>>getAllPer(){
        return ResponseEntity.ok(permissionService.getAllPer());
    }
    @GetMapping("/permissions/{id}")
    public ResponseEntity<PermissionResponse> getPermissionById(@PathVariable long id){
        return ResponseEntity.ok(permissionService.getPerById(id));
    }
    @PutMapping("/permissions/{id}")
    public ResponseEntity<PermissionResponse>updaterPer(@RequestBody PermissionRequest request,@PathVariable long id){
        return ResponseEntity.ok(permissionService.updatePer(request,id));
    }
    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<Void>deletePer(long id){
        permissionService.deletePer(id);
        return ResponseEntity.noContent().build();
    }

}
