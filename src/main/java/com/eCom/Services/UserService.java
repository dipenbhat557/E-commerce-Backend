package com.eCom.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eCom.Exception.ResourceNotFoundException;
import com.eCom.Model.User;
import com.eCom.Repositories.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public User update(int userId, User user) {

        User oldUser = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("The requested resource is not found"));

        oldUser.setAbout(user.getAbout());
        oldUser.setActive(user.isActive());
        oldUser.setAddress(user.getAddress());
        oldUser.setDate(user.getDate());
        oldUser.setEmail(user.getEmail());
        oldUser.setGender(user.getGender());
        oldUser.setName(user.getName());
        oldUser.setPassword(user.getPassword());
        oldUser.setPhone(user.getPhone());

        return this.userRepo.save(oldUser);
    }

    public void delete(int userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("The requested resource is not found"));

        this.userRepo.delete(user);

    }

    public User getById(int userId) {
        return this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("The requested resource is not found"));

    }

    public List<User> getAllUsers() {
        return this.userRepo.findAll();
    }
    
}
