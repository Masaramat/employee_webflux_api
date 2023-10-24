package com.employee.services;

import com.employee.models.Role;
import com.employee.models.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
@Service
public class UserService {
    private Map<String, User> data;

    @PostConstruct
    public void init() {
        data = new HashMap<>();

        //username:passwowrd -> user:user
        data.put("user", new User(1L, "user", "user@gmail.com", "0909876567", "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=",  Role.USER));
        data.put("admin", new User(1L, "admin", "admin@gmail.com", "080987876", "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=",  Role.ADMIN));
    }

    public Mono<User> findByUsername(String username) {
        return Mono.justOrEmpty(data.get(username));
    }
}
