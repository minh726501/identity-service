package Spring_Boot.identity_service.config;

import Spring_Boot.identity_service.entity.User;
import Spring_Boot.identity_service.repository.RoleRepository;
import Spring_Boot.identity_service.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public ApplicationRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository repository) {
        return args -> {
            String username = "admin";

            if (userRepository.existsByUsername(username)) {
                System.out.println("✅ Admin already exists, skipping creation.");
                return;
            }

            User admin = new User();
            admin.setUsername(username);
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRole(repository.findByName("ADMIN")); // hoặc set Role object nếu bạn dùng quan hệ

            userRepository.save(admin);
            System.out.println("✅ Admin user created successfully!");
        };
    }
}
