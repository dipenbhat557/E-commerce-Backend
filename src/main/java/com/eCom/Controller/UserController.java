package com.eCom.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eCom.Model.User;
import com.eCom.Services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return new ResponseEntity<User>(this.userService.create(user),HttpStatus.CREATED);
    }

    @GetMapping("/view")
    public ResponseEntity<List<User>> viewAllUsers(){
        return new ResponseEntity<List<User>>(this.userService.getAllUsers(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/viewById/{userId}")
    public ResponseEntity<User> viewById(@PathVariable int userId){
        return new ResponseEntity<User>(this.userService.getById(userId), HttpStatus.FOUND);
    }

    @GetMapping("/delete/{userId}")
    public ResponseEntity<String> delete(@PathVariable int userId){
        this.userService.delete(userId);
        return new ResponseEntity<String>("Successfully deleted....", HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody User user){
        return new ResponseEntity<User>(this.userService.update(userId, user), HttpStatus.OK);
    }
}
