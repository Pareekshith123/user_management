package com.userbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userbackend.Service.UserService;
import com.userbackend.model.User;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;
     @PostMapping("/")
     public User create(@RequestBody User user){
        return this.userService.createUser(user);
    }
    @GetMapping("/")
    public List<User> getAllUser(){
        return this.userService.getAllUser();
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id){
        this.userService.deleteUser(id);
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id){
      return this.userService.getUserById(id);
    }
    
}
