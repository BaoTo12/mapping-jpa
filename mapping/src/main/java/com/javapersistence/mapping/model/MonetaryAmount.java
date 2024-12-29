package com.javapersistence.mapping.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;


public class MonetaryAmount implements Serializable {

    /*
        The class does not need a special constructor, you can make it immutable, even with
        <code>final</code> fields, as your code will be the only place an instance is created.
     */
    private final BigDecimal value;
    private final Currency currency;

    public MonetaryAmount(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    /*
        You should implement the <code>equals()</code> and <code>hashCode()</code>
        methods, and compare monetary amounts "by value".
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonetaryAmount that = (MonetaryAmount) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }

    /*
            You will need a <code>String</code> representation of a monetary
            amount. Implement the <code>toString()</code> method and a static method to
            create an instance from a <code>String</code>.
         */
    @Override
    public String toString() {
        return value + " " + currency;
    }

    public static MonetaryAmount fromString(String s) {
        String[] split = s.split(" ");
        return new MonetaryAmount(
                new BigDecimal(split[0]),
                Currency.getInstance(split[1])
        );
    }
}