package com.manning.javapersistence.springdatajpa;

import com.manning.javapersistence.springdatajpa.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class FindUsersUsingQueriesTest extends SpringdatajpaApplicationTests {

    @Test
    public void testFindAll() {
        List<User> users = userRepository.findAll();
        Assertions.assertEquals(10, users.size());
    }

    @Test
    public void testFindUserByName() throws Exception {
        User user = userRepository.findByUsername("john").orElseThrow(() -> new Exception("User Not Found"));
        Assertions.assertEquals("john", user.getUsername());
    }

    @Test
    void testFindAllByOrderByUsernameAsc() {
        List<User> users = userRepository.findAllByOrderByUsernameAsc();
        Assertions.assertAll(() -> Assertions.assertEquals(10, users.size()),
                () -> Assertions.assertEquals("beth", users.get(0).getUsername()),
                () -> Assertions.assertEquals("stephanie",
                        users.get(users.size() - 1).getUsername()));
    }
    @Test
    void testFindByRegistrationDateBetween() {
        List<User> users = userRepository.findByRegistrationDateBetween(
                LocalDate.of(2020, Month.JULY, 1),
                LocalDate.of(2020, Month.DECEMBER, 31));
        Assertions.assertEquals(4, users.size());
    }
}
