package Spring_Boot.identity_service.repository;

import Spring_Boot.identity_service.entity.RefreshToken;
import Spring_Boot.identity_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    void deleteByUser(User user);
    RefreshToken findByToken(String token);
}
