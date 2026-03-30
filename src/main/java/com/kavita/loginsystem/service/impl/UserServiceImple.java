package com.kavita.loginsystem.service.impl;

import com.kavita.loginsystem.entity.User;
import com.kavita.loginsystem.repository.UserRepo;
import com.kavita.loginsystem.service.UserService;
import com.kavita.loginsystem.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service
@RequiredArgsConstructor
public class UserServiceImple implements UserService {

    private final UserRepo userRepo;
    private final StringRedisTemplate redisTemplate;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String register(String email, String password) {

        if (userRepo.findByEmail(email) != null) {
            return "User already exists";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        userRepo.save(user);

        return "User registered successfully";
    }

    @Override
    public String login(String email, String password) {

        String key = Constant.PREFIX + email;

        Long attempts = redisTemplate.opsForValue().increment(key);

        if (attempts > Constant.MAX_ATTEMPTS) {
            return "Account locked Try after 24 hours";
        }
        User user = userRepo.findByEmail(email);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {

            Long count = redisTemplate.opsForValue().increment(key);

            if (count == 1) {
                redisTemplate.expire(key, Duration.ofHours(24));
            }

            if (count >= Constant.MAX_ATTEMPTS) {
                return "Account locked after 5 failed attempts";
            }

            return "Invalid credentials" + count;
        }

        redisTemplate.delete(key);

        return "Login successful";
    }
}