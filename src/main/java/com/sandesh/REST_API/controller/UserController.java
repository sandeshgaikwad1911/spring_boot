package com.sandesh.REST_API.controller;

import com.sandesh.REST_API.entity.User;
import com.sandesh.REST_API.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try{
            List<User> allUsers = userService.getAllUsers();
//            System.out.println("allUsers"+" "+allUsers);
            if(!allUsers.isEmpty()) {
                return new ResponseEntity<>(allUsers, HttpStatus.OK);
            }
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return null;
    }

    @PostMapping("/create-new")
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        try{
            userService.saveUser(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println("exception"+" "+e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username , @RequestBody User user) {
        try{
            User existingUser = userService.findByUsername(username);
            System.out.println("existingUser"+" "+existingUser);
            if(existingUser != null) {
                existingUser.setUsername(!user.getUsername().isEmpty() ? user.getUsername() : existingUser.getUsername());
                existingUser.setPassword(!user.getPassword().isEmpty() ? user.getPassword() : existingUser.getPassword());
                userService.saveUser(existingUser);
            }
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}

/*
    /update/{username} => {}   use to get dynamic value => localhost:8080/update/James => James is dynamicVal here.
* */
