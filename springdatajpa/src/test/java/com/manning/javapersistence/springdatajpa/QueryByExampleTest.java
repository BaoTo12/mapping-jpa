package com.manning.javapersistence.springdatajpa;

import com.manning.javapersistence.springdatajpa.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueryByExampleTest extends SpringdatajpaApplicationTests {
    @Test
    void testEmailWithQueryByExample() {
        User user = new User();
        user.setEmail("@someotherdomain.com");
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnorePaths("level", "active")
                .withMatcher("email", matcher -> matcher.endsWith());
        Example<User> example = Example.of(user, exampleMatcher);
        List<User> users = userRepository.findAll(example);
        assertEquals(4, users.size());
    }

    @Test
    void testUsernameWithQueryByExample() {
        User user = new User();
        user.setUsername("J");
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnorePaths("level", "active")
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING)
                .withIgnoreCase();
        Example<User> example = Example.of(user, exampleMatcher);
        List<User> users = userRepository.findAll(example);
        assertEquals(3, users.size());
    }
}
