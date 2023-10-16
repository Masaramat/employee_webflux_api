package com.employee.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {
    private Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUserValidation(){
        User user = new User();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        int errorNo = 0;

        for(ConstraintViolation<User> violation : violations){
            errorNo++;
            String errorMessage = violation.getMessage();
            System.out.println("Validation Error "  +errorNo + ": " + errorMessage);
        }

        assertEquals(4, errorNo);
    }

    @Test
    public void testUserValidationPass(){
        User user = new User();
        user.setName("Mangut Innocent");
        user.setEmail("mangut@gmail.com");
        user.setPhone("09098987878");
        user.setPassword("password");
        user.setRole(Role.USER);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void invalidEmailTest(){
        User user = new User();
        user.setName("Mangut Innocent");
        user.setEmail("mangut.com");
        user.setPhone("09098987878");
        user.setPassword("password");
        user.setRole(Role.USER);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        int errorNo = 0;

        for(ConstraintViolation<User> violation : violations){
            errorNo++;
            String errorMessage = violation.getMessage();
            System.out.println("Validation Error "  +errorNo + ": " + errorMessage);
        }

        assertEquals(1, errorNo);
    }










    private User createUserWithRole(String name, String email, String password, Role role) {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }
}
