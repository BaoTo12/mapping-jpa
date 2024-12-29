package com.javapersistence.mapping.model;

import com.javapersistence.mapping.converter.ZipcodeConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    private Long id;

    private String username;

    // The Address is @Embeddable, no annotation needed here...
    @Embedded
    @Convert(
            converter = ZipcodeConverter.class,
            attributeName = "city.zipcode" // Or "city.zipcode" for nested embeddables
    )
    private Address homeAddress;

//    @Embedded // Not necessary...
//    @AttributeOverride(name = "street",
//            column = @Column(name = "BILLING_STREET")) // NULLable!
//    @AttributeOverride(name = "city.zipcode.value",
//            column = @Column(name = "BILLING_ZIPCODE", length = 5))
//    @AttributeOverride(name = "city.name",
//            column = @Column(name = "BILLING_CITY"))
//    @AttributeOverride(name = "city.country", column = @Column(name = "BILLING_COUNTRY"))
//    private Address billingAddress;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }


}
