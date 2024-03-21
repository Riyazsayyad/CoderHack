package com.codehack.codehack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList; // Added import
import java.util.List;
import java.util.Optional;

import com.codehack.codehack.repository.*;
import com.codehack.codehack.entity.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsersSortedByScore() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "score"));
    }

    public User getUserById(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElse(null); // Returns null if user not found
    }

    public User registerUser(String username) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setScore(0);
        newUser.setBadges(new ArrayList<>());
        return userRepository.save(newUser);
    }

    public User updateUserScore(String userId, int newScore) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setScore(newScore);
            user.setBadges(assignBadges(newScore));
            return userRepository.save(user);
        } else {
            return null; // User not found
        }
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    private List<String> assignBadges(int score) {
        List<String> badges = new ArrayList<>();
        if (score >= 1 && score <= 30) {
            badges.add("Code Ninja");
        } else if (score > 30 && score <= 60) {
            badges.add("Code Champ");
        } else if (score > 60 && score <= 100) {
            badges.add("Code Master");
        }
        return badges;
    }

}

