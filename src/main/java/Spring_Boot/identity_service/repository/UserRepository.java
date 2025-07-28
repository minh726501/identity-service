package Spring_Boot.identity_service.repository;

import Spring_Boot.identity_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUsername(String username);
    User findByUsername(String username);
}
