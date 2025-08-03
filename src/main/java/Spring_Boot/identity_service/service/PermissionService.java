package Spring_Boot.identity_service.service;

import Spring_Boot.identity_service.dto.request.PermissionRequest;
import Spring_Boot.identity_service.dto.response.PermissionResponse;
import Spring_Boot.identity_service.entity.Permission;
import Spring_Boot.identity_service.mapper.PermissionMapper;
import Spring_Boot.identity_service.repository.PermissionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public PermissionService(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    public PermissionResponse createPer( PermissionRequest request){
        if (permissionRepository.existsByName(request.getName())) {
            throw new RuntimeException("Permission với tên '" + request.getName() + "' đã tồn tại");
        }
        Permission permission= permissionMapper.toPermission(request);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }
    public PermissionResponse getPerById(long id){
        Optional<Permission> getPer=permissionRepository.findById(id);
        if (getPer.isEmpty()){
            throw new RuntimeException("Không tìm thấy permission với id: " + id);
        }
        Permission existingPer= getPer.get();
        return permissionMapper.toPermissionResponse(existingPer);
    }
    public Optional<Permission> getPerEntityById(long id){
        return permissionRepository.findById(id);
    }
    public PermissionResponse updatePer(PermissionRequest request,long id){
        Optional<Permission> getPer=getPerEntityById(id);
        if (getPer.isEmpty()){
            throw new RuntimeException("Không tìm thấy permission với id: " + id);
        }
        if (permissionRepository.existsByName(request.getName())){
            throw new RuntimeException("Permission '" + request.getName() + "' đã tồn tại");
        }
        Permission existingPer= getPer.get();
        permissionMapper.updatePermissionFromRequest(request,existingPer);
        permissionRepository.save(existingPer);
        return permissionMapper.toPermissionResponse(existingPer);
    }
    public List<PermissionResponse>getAllPer(){
        List<Permission>permissions=permissionRepository.findAll();
        return permissionMapper.toPermissionResponseList(permissions);
    }
    public void deletePer(long id){
        permissionRepository.deleteById(id);
    }

}
