package Spring_Boot.identity_service.repository;

import Spring_Boot.identity_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
    boolean existsByName(String name);
    List<Permission> findByNameIn(List<String> name);
}
