package com.javapersistence.mapping;

import com.javapersistence.mapping.configuration.SpringDataConfiguration;
import com.javapersistence.mapping.model.*;
import com.javapersistence.mapping.repositories.ItemRepository;
import com.javapersistence.mapping.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringDataConfiguration.class})
public class MappingValuesSpringDataJPATest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
//    @Test
//    void storeLoadEntities() {
//        User user = new User();
//        user.setUsername("username");
//        user.setHomeAddress(new Address("Flowers Street",
//                "12345", "Boston"));
//        userRepository.save(user);
//        Item item = new Item();
//        item.setName("Some Item");
//        item.setMetricWeight(2);
//        item.setDescription("descriptiondescription");
//        itemRepository.save(item);
//        List<User> users = (List<User>) userRepository.findAll();
//        List<Item> items = (List<Item>)
//                itemRepository.findByMetricWeight(2.0);
//        assertAll(
//                () -> assertEquals(1, users.size()),
//                () -> assertEquals("username", users.get(0).getUsername()),
//                () -> assertEquals("Flowers Street",
//                        users.get(0).getHomeAddress().getStreet()),
//                () -> assertEquals("12345",
//                        users.get(0).getHomeAddress().getZipcode()),
//                () -> assertEquals("Boston",
//                        users.get(0).getHomeAddress().getCity()),
//                () -> assertEquals(1, items.size()),
//                () -> assertEquals("AUCTION: Some Item",items.get(0).getName()),
//                () -> assertEquals("descriptiondescription",
//                        items.get(0).getDescription()),
//                () -> assertEquals(AuctionType.HIGHEST_BID,
//                        items.get(0).getAuctionType()),
//                () -> assertEquals("descriptiond...",
//                        items.get(0).getShortDescription()),
//                () -> assertEquals(2.0, items.get(0).getMetricWeight()),
//                () -> assertEquals(LocalDate.now(),
//                        items.get(0).getCreatedOn()),
//                () ->
//                        assertTrue(ChronoUnit.SECONDS.between(
//                                LocalDateTime.now(),
//                                items.get(0).getLastModified()) < 1),
//                () -> assertEquals(new BigDecimal("1.00"),
//                        items.get(0).getInitialPrice())
//        );
//    }
    @Test
    void storeLoadEntities1() {
        // creating city
        City city = new City();
        city.setName("Boston");
        city.setZipcode(new GermanZipcode("12345"));
        city.setCountry("USA");
        // creating user
        User user = new User();
        user.setUsername("username");
        user.setHomeAddress(new Address("Flowers Street", city));
        userRepository.save(user);
        // creating item
        Item item = new Item();
        item.setName("Some Item");
        item.setMetricWeight(2);
        item.setBuyNowPrice(new MonetaryAmount(
                BigDecimal.valueOf(1.1), Currency.getInstance("USD")));
        item.setDescription("descriptiondescription");
        itemRepository.save(item);
        List<User> users = (List<User>) userRepository.findAll();
        List<Item> items = (List<Item>)
                itemRepository.findByMetricWeight(2.0);
        assertAll(
                () -> assertEquals(1, users.size()),
                () -> assertEquals("username", users.get(0).getUsername()),
                () -> assertEquals("Flowers Street",
                        users.get(0).getHomeAddress().getStreet()),
                () -> assertEquals("Boston",
                        users.get(0).getHomeAddress().getCity().getName()),
                () -> assertEquals("USA",
                        users.get(0).getHomeAddress().getCity().getCountry()),
                () -> assertEquals(1, items.size()),
                () -> assertEquals("AUCTION: Some Item",
                        items.get(0).getName()),
                () -> assertEquals("1.1 USD",
                        items.get(0).getBuyNowPrice().toString()),
                () -> assertEquals("descriptiondescription",
                        items.get(0).getDescription()),
                () -> assertEquals(AuctionType.HIGHEST_BID,
                        items.get(0).getAuctionType()),
                () -> assertEquals("descriptiond...",
                        items.get(0).getShortDescription()),
                () -> assertEquals(2.0, items.get(0).getMetricWeight()),
                () -> assertEquals(LocalDate.now(),
                        items.get(0).getCreatedOn()),
                () ->
                        assertTrue(ChronoUnit.SECONDS.between(
                                LocalDateTime.now(),
                                items.get(0).getLastModified()) < 1),
                () -> assertEquals(new BigDecimal("1.00"),
                        items.get(0).getInitialPrice())
        );
    }
}