package Spring_Boot.identity_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String token;
    private Timestamp expiry;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
