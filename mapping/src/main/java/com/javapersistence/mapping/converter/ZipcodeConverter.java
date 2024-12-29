package com.javapersistence.mapping.converter;

import com.javapersistence.mapping.model.GermanZipcode;
import com.javapersistence.mapping.model.SwissZipcode;
import com.javapersistence.mapping.model.Zipcode;
import jakarta.persistence.AttributeConverter;

public class ZipcodeConverter implements AttributeConverter<Zipcode, String> {
    @Override
    public String convertToDatabaseColumn(Zipcode attribute) {
        return attribute.getValue();
    }

    @Override
    public Zipcode convertToEntityAttribute(String dbData) {
        if (dbData.length() == 4){
            return new SwissZipcode(dbData);
        }else if (dbData.length() == 5){
            return new GermanZipcode(dbData);
        }else {
            throw new IllegalArgumentException(
                    "Unsupported zipcode in database: " + dbData
            );
        }
    }
}
