package com.resc.pmtool.services;

import com.resc.pmtool.domain.User;
import com.resc.pmtool.exceptions.UsernameAlreadyExistsException;
import com.resc.pmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser) {

        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //Username has to be unique( Custom Exception)
//            newUser.setUsername(newUser.getUsername());
            //Make sure that password and confirmationPassword match
            //We don't persist or show confPass
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username `" + newUser.getUsername() + "` already exists");
        }
    }
}
