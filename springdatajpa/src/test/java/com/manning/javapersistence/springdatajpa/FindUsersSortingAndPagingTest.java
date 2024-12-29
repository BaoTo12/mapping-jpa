package com.manning.javapersistence.springdatajpa;

import com.manning.javapersistence.springdatajpa.model.User;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public class FindUsersSortingAndPagingTest extends SpringdatajpaApplicationTests{
    @Test
    void testOrder(){
        User user1 = userRepository.findFirstByOrderByUsernameAsc().get();
        User user2 = userRepository.findTopByOrderByRegistrationDateDesc().get();
        Page<User> userPage = userRepository.findAll(PageRequest.of(0, 3));
        List<User> users = userRepository.findFirst2ByLevel(2,
                Sort.by("registrationDate"));

//        System.out.println("Content: " + userPage.getContent());
//        System.out.println("Page Size: " + userPage.getSize());
//        System.out.println("Total Pages: " + userPage.getTotalPages());
//        System.out.println("Total Elements: " + userPage.getTotalElements());
//        System.out.println("Is First Page: " + userPage.isFirst());
//        System.out.println("Is Last Page: " + userPage.isLast());

        Assertions.assertAll(
                () -> Assertions.assertEquals("beth", user1.getUsername()),
                () -> Assertions.assertEquals("julius", user2.getUsername()),
                () -> Assertions.assertEquals(2, users.size()),
                () -> Assertions.assertEquals(3, userPage.getSize()),
                () -> Assertions.assertEquals("beth", users.get(0).getUsername()),
                () -> Assertions.assertEquals("marion", users.get(1).getUsername())
        );
    }
    @Test
    void testFindByLevel() {
        Sort.TypedSort<User> user = Sort.sort(User.class);
        List<User> users = userRepository.findByLevel(3,
                user.by(User::getRegistrationDate).descending());
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, users.size()),
                () -> Assertions.assertEquals("james", users.get(0).getUsername())
        );
    }
    @Test
    void testFindByActive() {
        List<User> users = userRepository.findByActive(true,
                PageRequest.of(1, 4, Sort.by("registrationDate")));
        assertAll(
                () -> assertEquals(4, users.size()),
                () -> assertEquals("burk", users.get(0).getUsername())
        );
    }
}
