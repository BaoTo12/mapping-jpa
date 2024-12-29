package com.manning.javapersistence.springdatajpa;

import com.manning.javapersistence.springdatajpa.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ModifyQueryTest extends SpringdatajpaApplicationTests{
    @Test
    void testModifyLevel() {
        int updated = userRepository.updateLevel(5, 4);
        List<User> users = userRepository.findByLevel(4, Sort.by("username"));
        assertAll(
                () -> assertEquals(1, updated),
                () -> assertEquals(3, users.size()),
                () -> assertEquals("katie", users.get(1).getUsername())
        );
    }
    @Test
    void testDeleteByLevel() {
        int deleted = userRepository.deleteByLevel(2);
        List<User> users = userRepository.findByLevel(2, Sort.by("username"));
        assertEquals(0, users.size());
    }
    @Test
    void testDeleteBulkByLevel() {
        int deleted = userRepository.deleteBulkByLevel(2);
        List<User> users = userRepository.findByLevel(2, Sort.by("username"));
        assertEquals(0, users.size());
    }

}
