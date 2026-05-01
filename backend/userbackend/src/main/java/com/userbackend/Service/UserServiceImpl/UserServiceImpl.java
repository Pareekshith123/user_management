package com.userbackend.Service.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.userbackend.Service.UserService;
import com.userbackend.entity.UserEntity;
import com.userbackend.model.User;
import com.userbackend.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    // CREATE USER
    @Override
    @CacheEvict(value = "users", allEntries = true)
    public User createUser(User user) {

        UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(user, userEntity);

        userEntity = userRepo.save(userEntity);

        user.setId(userEntity.getId());

        return user;
    }

    // UPDATE USER
    @Override
    @CachePut(value = "user", key = "#id")
    @CacheEvict(value = "users", allEntries = true)
    public User updateUser(User user, long id) {

        UserEntity existingUser = userRepo.findById(id).orElse(null);

        if (existingUser != null) {

            existingUser.setFirstname(user.getFirstname());
            existingUser.setLastname(user.getLastname());
            existingUser.setEmail(user.getEmail());

            UserEntity updatedUser = userRepo.save(existingUser);

            User updated = new User();

            BeanUtils.copyProperties(updatedUser, updated);

            return updated;
        }

        return null;
    }

    // GET ALL USERS
    @Override
    @Cacheable(value = "users")
    public List<User> getAllUser() {

        System.out.println("Fetching all users from DB...");

        List<UserEntity> userEntities = userRepo.findAll();

        List<User> users = userEntities.stream()
                .map(userEntity -> new User(
                        userEntity.getId(),
                        userEntity.getFirstname(),
                        userEntity.getLastname(),
                        userEntity.getEmail()))
                .collect(Collectors.toList());

        return users;
    }

    // GET USER BY ID
    @Override
    @Cacheable(value = "user", key = "#id")
    public User getUserById(long id) {

        System.out.println("Fetching user from DB...");

        UserEntity userEntity = userRepo.findById(id).orElse(null);

        if (userEntity != null) {

            User user = new User();

            BeanUtils.copyProperties(userEntity, user);

            return user;
        }
        return null;
    }

    // DELETE USER
    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void deleteUser(long id) {
        UserEntity user = userRepo.findById(id).orElse(null);
        if (user != null) {
            userRepo.deleteById(user.getId());
        }
    }
}