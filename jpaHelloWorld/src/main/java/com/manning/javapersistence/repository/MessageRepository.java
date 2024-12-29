package com.manning.javapersistence.repository;

import org.springframework.data.repository.CrudRepository;
import com.manning.javapersistence.Message;
public interface MessageRepository extends CrudRepository<Message, Long> {
}
