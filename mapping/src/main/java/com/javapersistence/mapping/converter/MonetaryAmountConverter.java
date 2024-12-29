package com.javapersistence.mapping.converter;

import com.javapersistence.mapping.model.MonetaryAmount;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class MonetaryAmountConverter implements AttributeConverter<MonetaryAmount, String> {
    @Override
    public String convertToDatabaseColumn(MonetaryAmount monetaryAmount) {
        return monetaryAmount.toString();
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(String dbData) {
        return MonetaryAmount.fromString(dbData);
    }
}
