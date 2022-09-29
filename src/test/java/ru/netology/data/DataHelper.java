package ru.netology.data;

import com.github.javafaker.Faker;
import com.google.protobuf.StringValue;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private static Faker faker = new Faker(new Locale("en"));

    private static int validYear = Integer.parseInt(getCurrentYear()) + 1;
    public static CardInfo generateDataWithApprovedCard() {

        //var randomMonth = faker.number().numberBetween(01, 12);
        // на данный момент программа не принимает однозначные числа. Надо 0 ставить перед однозначными
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo("4444 4444 4444 4441", getCurrentMonth(), String.valueOf(validYear),
                randomName, randomCvc);
    }

    public static CardInfo generateDataWithDeclineCard() {

        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo("4444 4444 4444 4442", getCurrentMonth(), String.valueOf(validYear),
                randomName, randomCvc);
    }

    public static CardInfo generateDataWithRandomCardNumber() {
        var randomName = faker.name().fullName();
        var randomCardNumber = faker.number().digits(16);
        var randomCvc = faker.number().digits(3);
        return  new CardInfo(randomCardNumber, getCurrentMonth(), String.valueOf(validYear), randomName, randomCvc);
    }

    public static CardInfo generateDataWithApprovedCardAndParametrizedMonthAndYear(String month, String year) {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return  new CardInfo("4444 4444 4444 4441", month, year, randomName, randomCvc);
    }

    public static String getCurrentMonth() {
        LocalDate date = LocalDate.now();
        String currentMonth = date.format(DateTimeFormatter.ofPattern("MM"));
        return currentMonth;
    }

    public static String getCurrentYear() {
        LocalDate date = LocalDate.now();
        String currentYear = date.format(DateTimeFormatter.ofPattern("yy"));
        return currentYear;
    }

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
