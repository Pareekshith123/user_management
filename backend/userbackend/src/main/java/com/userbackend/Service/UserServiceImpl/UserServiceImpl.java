package com.userbackend.Service.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userbackend.Service.UserService;
import com.userbackend.entity.UserEntity;
import com.userbackend.model.User;
import com.userbackend.repository.UserRepo;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;
    @Override
public User createUser(User user) {
    UserEntity userEntity = new UserEntity();
    BeanUtils.copyProperties(user, userEntity);
    userEntity = userRepo.save(userEntity); // Save the entity and get the saved instance with generated ID

    // Copy the ID from the saved entity back to the User object before returning
    user.setId(userEntity.getId());
    return user;
}


    @Override
    public User updateUser(User user, long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public List<User> getAllUser() {
        List<UserEntity> userEntities=userRepo.findAll();
        List<User> users=userEntities.stream()
        .map(userEntity ->new User(
            userEntity.getId(),
            userEntity.getFirstname(),
            userEntity.getLastname(),
            userEntity.getEmail()
        )).collect(Collectors.toList());
        return users;

    }

    @Override
   public User getUserById(long id) {
    UserEntity userEntity = userRepo.findById(id).orElse(null);
    if (userEntity != null) {
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }
    return null; // Handle the case where the user with the specified ID is not found.
}

    @Override
    public void deleteUser(long id) {
       UserEntity user=userRepo.findById(id).get();
        this.userRepo.deleteById(user);
      
    }
    
}
