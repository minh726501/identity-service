package Spring_Boot.identity_service.service;

import Spring_Boot.identity_service.dto.request.UserCreateRequest;
import Spring_Boot.identity_service.dto.request.UserUpdateRequest;
import Spring_Boot.identity_service.dto.response.UserResponse;
import Spring_Boot.identity_service.entity.User;
import Spring_Boot.identity_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserResponse createUser(UserCreateRequest request){
        User user=new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());
        user.setDob(request.getDob());
        userRepository.save(user);
        return convertToUser(user);
    }
    public UserResponse convertToUser(User user){
        UserResponse response=new UserResponse();
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setDob(user.getDob());
        return response;
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
        return convertToUser(existingUser);
    }
    public List<UserResponse>getAllUser(){
        List<User>listUsers=userRepository.findAll();
        List<UserResponse>listUserResponse=new ArrayList<>();
        for (User user:listUsers){
            UserResponse userResponse=convertToUser(user);
            listUserResponse.add(userResponse);
        }
        return listUserResponse;
    }
    public UserResponse updateUser(UserUpdateRequest request){
        Optional<User> getUser=getUserById(request.getId());
        if (getUser.isEmpty()){
            throw new RuntimeException("ko ton tai User voi id="+request.getId());
        }
        User existingUser= getUser.get();

        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setDob(request.getDob());
        userRepository.save(existingUser);
        return convertToUser(existingUser);
    }
    public void deleteUserById(long id){
        Optional<User> existingUser=getUserById(id);
        if (existingUser.isEmpty()){
            throw new RuntimeException("ko ton tai User voi id="+id);
        }
        userRepository.deleteById(id);
    }
}
