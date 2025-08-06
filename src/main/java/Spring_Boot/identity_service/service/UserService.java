package Spring_Boot.identity_service.service;

import Spring_Boot.identity_service.dto.ApiResponse;
import Spring_Boot.identity_service.dto.request.UserCreateRequest;
import Spring_Boot.identity_service.dto.request.UserUpdateRequest;
import Spring_Boot.identity_service.dto.response.PaginationInfo;
import Spring_Boot.identity_service.dto.response.UserResponse;
import Spring_Boot.identity_service.entity.Role;
import Spring_Boot.identity_service.entity.User;
import Spring_Boot.identity_service.mapper.UserMapper;
import Spring_Boot.identity_service.repository.RoleRepository;
import Spring_Boot.identity_service.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ApiResponse<List<UserResponse>>getAllUser(Integer page, Integer size){
        List<User> users;
        PaginationInfo pagination;
        if (page!=null&&size!=null){
            Pageable pageable=PageRequest.of(page-1,size);
            Page<User>userPage=userRepository.findAll(pageable);
            users=userPage.getContent();
            pagination=new PaginationInfo(userPage.getNumber()+1,userPage.getSize(),userPage.getTotalPages(),userPage.getTotalElements());
        }else {
            users = userRepository.findAll();
            pagination = new PaginationInfo(1, users.size(), 1, users.size());
        }
        List<UserResponse>data=userMapper.toUserResponseList(users);
        return new ApiResponse<>(200,"Fetch All User Success",data,pagination);
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
    public UserResponse getMyInfo(){
        var context=SecurityContextHolder.getContext();
        String name=context.getAuthentication().getName();
        User user=userRepository.findByUsername(name);
        if (user==null){
            throw new RuntimeException("username ko ton tai");
        }
        return userMapper.toUserResponse(user);
    }
}
