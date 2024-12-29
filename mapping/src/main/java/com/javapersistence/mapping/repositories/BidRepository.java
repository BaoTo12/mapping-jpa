package com.javapersistence.mapping.repositories;

import com.javapersistence.mapping.model.Bid;
import com.javapersistence.mapping.model.ItemBidSummary;
import org.springframework.data.repository.CrudRepository;

public interface BidRepository extends CrudRepository<Bid, Long> {

}