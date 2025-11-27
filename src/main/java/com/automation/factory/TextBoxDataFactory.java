package com.automation.factory;

import com.automation.model.TextBoxData;
import com.github.javafaker.Faker;

public class TextBoxDataFactory {
    private final static Faker faker = new Faker();

    public static TextBoxData createValidTextBoxInputData(){
        return TextBoxData.builder()
                .fullName(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .currentAddress(faker.address().fullAddress())
                .permanentAddress(faker.address().fullAddress())
                .build();
    }

    public static TextBoxData createInvalidTextBoxInputDataWithWrongEmail(){
        return TextBoxData.builder()
                .fullName(faker.name().fullName())
                .email(faker.name().firstName())
                .currentAddress(faker.address().fullAddress())
                .permanentAddress(faker.address().fullAddress())
                .build();
    }
}
