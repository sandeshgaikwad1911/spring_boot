package com.sandesh.REST_API.service;

import com.sandesh.REST_API.entity.User;
import com.sandesh.REST_API.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public  void saveUser(User user){userRepository.save(user);}

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        System.out.println("username"+" "+username);
        return userRepository.findByUsername(username);
    }
    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }
}
