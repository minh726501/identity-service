package Spring_Boot.identity_service.service;

import Spring_Boot.identity_service.dto.request.UserCreateRequest;
import Spring_Boot.identity_service.dto.request.UserUpdateRequest;
import Spring_Boot.identity_service.dto.response.UserResponse;
import Spring_Boot.identity_service.entity.Role;
import Spring_Boot.identity_service.entity.User;
import Spring_Boot.identity_service.mapper.UserMapper;
import Spring_Boot.identity_service.repository.RoleRepository;
import Spring_Boot.identity_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository repository;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, RoleRepository repository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    public UserResponse createUser(UserCreateRequest request){
        if (userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username da ton tai");
        }
        User user=userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role defaultRole= repository.findByName("USER");
        user.setRole(defaultRole);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public Optional<User> getUserById(long id){
        return userRepository.findById(id);

    }
    public UserResponse findUserById(long id){
        Optional<User> user=userRepository.findById(id);
        if (user.isEmpty()){
            throw new RuntimeException("user ko ton tai voi id="+id);
        }
        User existingUser=user.get();
        return userMapper.toUserResponse(existingUser);
    }
    public List<UserResponse>getAllUser(){
        List<User>listUsers=userRepository.findAll();
        return userMapper.toUserResponseList(listUsers);

    }
    public UserResponse updateUser(UserUpdateRequest request){
        Optional<User> getUser=getUserById(request.getId());
        if (getUser.isEmpty()){
            throw new RuntimeException("ko ton tai User voi id="+request.getId());
        }
        User existingUser= getUser.get();
        userMapper.updateUserFromRequest(request,existingUser);
        existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(existingUser);
        return userMapper.toUserResponse(existingUser);
    }
    public void deleteUserById(long id){
        Optional<User> existingUser=getUserById(id);
        if (existingUser.isEmpty()){
            throw new RuntimeException("ko ton tai User voi id="+id);
        }
        userRepository.deleteById(id);
    }
}
