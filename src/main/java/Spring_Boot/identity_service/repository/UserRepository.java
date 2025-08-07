package Spring_Boot.identity_service.repository;

import Spring_Boot.identity_service.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUsername(String username);
    User findByUsername(String username);
    @Query(value = "select*from user u where( u.username like %:username% OR :username is null) and (u.first_name = :firstName OR :firstName is null) and (u.last_name = :lastName OR :lastName is null)",
            countQuery ="select count(*)from user u where( u.username like %:username% OR :username is null) and (u.first_name = :firstName OR :firstName is null) and (u.last_name = :lastName OR :lastName is null)",nativeQuery = true)
    Page<User> searchUser(@Param(value = "username")String username,
                          @Param(value = "firstName")String firstName,
                          @Param(value = "lastName")String lastname,
                          Pageable pageable);
    @Query(value = "select*from user u where( u.username like %:username% OR :username is null) and (u.first_name = :firstName OR :firstName is null) and (u.last_name = :lastName OR :lastName is null)",
            countQuery ="select count(*)from user u where( u.username like %:username% OR :username is null) and (u.first_name = :firstName OR :firstName is null) and (u.last_name = :lastName OR :lastName is null)",nativeQuery = true)
    List<User> searchUserNoPage(@Param(value = "username")String username,
                          @Param(value = "firstName")String firstName,
                          @Param(value = "lastName")String lastname
                          );
}
