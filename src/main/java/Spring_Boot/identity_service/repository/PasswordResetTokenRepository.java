package Spring_Boot.identity_service.repository;

import Spring_Boot.identity_service.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken ,Long> {
    PasswordResetToken findByToken(String otp);
}
