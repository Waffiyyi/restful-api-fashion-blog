package com.example.week8taask.Services;

import com.example.week8taask.DTOs.LoginDTO;
import com.example.week8taask.Entities.User;
import com.example.week8taask.Repositories.UserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public User register(User user){
        String password = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(password);
        return userRepo.save(user);
    }


    public LoginDTO findByEmailAndPassword(String email, String password) {
       LoginDTO user = userRepo.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }
}
