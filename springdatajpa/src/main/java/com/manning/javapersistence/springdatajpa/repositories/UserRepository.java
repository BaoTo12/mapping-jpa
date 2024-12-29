package com.manning.javapersistence.springdatajpa.repositories;

import com.manning.javapersistence.springdatajpa.model.Projection;
import com.manning.javapersistence.springdatajpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findAllByOrderByUsernameAsc();

    List<User> findByRegistrationDateBetween(LocalDate start, LocalDate end);

    List<User> findByUsernameAndEmail(String username, String email);

    List<User> findByUsernameOrEmail(String username, String email);

    List<User> findByUsernameIgnoreCase(String username);

    List<User> findByLevelOrderByUsernameDesc(int level);

    List<User> findByLevelGreaterThanEqual(int level);

    List<User> findByUsernameContaining(String text);

    List<User> findByUsernameLike(String text);

    List<User> findByUsernameStartingWith(String start);

    List<User> findByUsernameEndingWith(String end);

    List<User> findByActive(boolean active);

    List<User> findByRegistrationDateIn(Collection<LocalDate> dates);

    List<User> findByRegistrationDateNotIn(Collection<LocalDate> dates);

    Optional<User> findFirstByOrderByUsernameAsc();
    Optional<User> findTopByOrderByRegistrationDateDesc();
//    Paging and Sorting
    Page<User> findAll(Pageable pageable);
    List<User> findFirst2ByLevel(int level, Sort sort);
    List<User> findByLevel(int level, Sort sort);
    List<User> findByActive(boolean active, Pageable pageable);

    Streamable<User> findByEmailContaining(String text);
    Streamable<User> findByLevel(int level);

    @Query("select count(u) from User u where u.active = ?1")
    int findNumberOfUsersByActivity(boolean active);
    @Query("select u from User u where u.level = :level and u.active = :active")
    List<User> findByLevelAndActive(@Param("level") int level,
                                    @Param("active") boolean active);

    @Query(value = "select count(*) from USERS where active = ?1", nativeQuery = true)
    int findNumberOfUsersByActivityNative(boolean active);

    @Query("select u.username, LENGTH(u.email) as email_length from #{#entityName} u where u.username LIKE %?1%")
    List<Object[]> findByAsArrayAndSort(String text, Sort sort);

    List<Projection.UserSummary> findByRegistrationDateAfter(LocalDate date);
    List<Projection.UsernameOnly> findByEmail(String username);
    <T> List<T> findByEmail(String username, Class<T> type);

    @Modifying
    @Transactional
    @Query("update User u set u.level = ?2 where u.level = ?1")
    int updateLevel(int level, int newLevel);

    @Transactional
    int deleteByLevel(int level);
    @Transactional
    @Modifying
    @Query("delete from User u where u.level = ?1")
    int deleteBulkByLevel(int level);
}
