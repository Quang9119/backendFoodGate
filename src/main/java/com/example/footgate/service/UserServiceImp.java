package com.example.footgate.service;

import com.example.footgate.config.JwtUtilsHelper;
import com.example.footgate.entities.User;
import com.example.footgate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtUtilsHelper jwtUtilsHelper;
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtUtilsHelper.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if(user==null) {
            throw new Exception("user not found");
        }
        return user;
    }
}
