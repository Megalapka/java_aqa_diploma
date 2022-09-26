package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Locale;

public class DataHelper {

    private static Faker faker = new Faker(new Locale("en"));

    public static CardInfo generateDataWithApprovedCard() {

        //var randomMonth = faker.number().numberBetween(01, 12);
        // на данный момент программа не принимает однозначные числа. Надо 0 ставить перед однозначными
        var randomName = faker.name().fullName();

        return new CardInfo("4444 4444 4444 4441", "11", "23", randomName, "123");
    }

    public static CardInfo generateDataWithDeclineCard() {

        var randomName = faker.name().fullName();

        return new CardInfo("4444 4444 4444 4442", "11", "23", randomName, "123");
    }

    public static CardInfo generateDataWithRandomCardNumber() {
        var randomName = faker.name().fullName();
        var randomCardNumber = faker.number().digits(16);
        return  new CardInfo(randomCardNumber, "11", "23", randomName, "123");
    }


// generateRandomCard


    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String cardOwner;
        String cvc;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreditCardData {
        private String id;
        private String bank_id; //пока будем брать данные из поля bank_id вместо id
        private String created;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentCardData {
        private String id;
        private String amount;
        private String created;
        private String status;
        private String transaction_id; //пока будем брать данные из поля transaction_id вместо id
    }
}
