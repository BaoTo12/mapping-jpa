package com.javapersistence.mapping.repositories;

import org.springframework.data.repository.CrudRepository;
import com.javapersistence.mapping.model.Item;
public interface ItemRepository extends CrudRepository<Item, Long> {
    Iterable<Item> findByMetricWeight(double weight);
}
