package com.betaplan.fatjon.projectmanager.services;

import com.betaplan.fatjon.projectmanager.models.LoginUser;
import com.betaplan.fatjon.projectmanager.models.User;
import com.betaplan.fatjon.projectmanager.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            return null;
        }
    }

    //    public User findByEmail(String email){
//        Optional<User> optionalUser = userRepository.findByEmail(email);
//        if(optionalUser.isPresent()){
//            return optionalUser.get();
//        }
//        else {
//            return null;
//        }
//    }
    public User register(User newUser, BindingResult result) {
        Optional<User> potentialUser = this.userRepository.findByEmail(newUser.getEmail());
        if (potentialUser.isPresent()) {
            result.rejectValue("Email", "Exist", "This email is taken");
        }
        if (!newUser.getPassword().equals(newUser.getConfirm())) {
            result.rejectValue("Confirm", "Matches", "Confirm must match the password");
        }
        if (result.hasErrors()) {
            return null;
        } else {
            String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
            newUser.setPassword(hashed);
            return userRepository.save(newUser);
        }
    }

    public User login(LoginUser newLoginObject, BindingResult result) {
        Optional<User> potentialUser = userRepository.findByEmail(newLoginObject.getEmail());
        if (!potentialUser.isPresent()) {
            result.rejectValue("email", "Invalid", "Email does not exist");
        } else {
            if (!BCrypt.checkpw(newLoginObject.getPassword(), potentialUser.get().getPassword())) {
                result.rejectValue("password", "Matches", "The password is not correct");
            }
        }
        if (result.hasErrors()) {
            return null;
        } else {
            return potentialUser.get();
        }
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}