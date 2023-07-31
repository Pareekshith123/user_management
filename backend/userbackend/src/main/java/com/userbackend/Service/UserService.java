package com.userbackend.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.userbackend.model.User;



@Service
public interface UserService {
    public User createUser(User user);
    public User updateUser(User user,long id);
    public List getAllUser();
    public User getUserById(long id);
    public void deleteUser(long id);
}
