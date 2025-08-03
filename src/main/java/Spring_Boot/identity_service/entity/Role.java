package Spring_Boot.identity_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.List;


@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<User>users;
    @ManyToMany
    @JoinTable(
            name = "role_permission", // bảng trung gian
            joinColumns = @JoinColumn(name = "role_id"), // cột bên Role
            inverseJoinColumns = @JoinColumn(name = "permission_id") // cột bên Permission
    )
    @JsonIgnore
    List<Permission>permissions;
}
