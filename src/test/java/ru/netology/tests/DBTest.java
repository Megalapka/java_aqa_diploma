package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.APIHelper;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.MainPage;


import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

public class DBTest {
   // MainPage mainPage = open("http://localhost:8080/", MainPage.class);

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Should added payment data to database with APPROVED through API")
    void shouldSuccessTransactionWithApprovedPaymentCardThroughAPI() {
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        APIHelper.createPayment(cardInfo);
        var paymentCardData = SQLHelper.getPaymentCardData();
        assertEquals("APPROVED", paymentCardData.getStatus());

    }
    @Test
    @DisplayName("Should added credit data to database with APPROVED through API")
    void shouldSuccessTransactionWithApprovedCreditCardThroughAPI() {
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        APIHelper.createCredit(cardInfo);
        var creditCardData = SQLHelper.getCreditCardData();
        assertEquals("APPROVED", creditCardData.getStatus());

    }

    @Test
    @DisplayName("Should added payment data to database with DECLINED through API")
    void shouldSuccessTransactionWithDeclinedPaymentCardThroughAPI() {
        var cardInfo = DataHelper.generateDataWithDeclineCard();
        APIHelper.createPayment(cardInfo);
        var paymentCardData = SQLHelper.getPaymentCardData();
        assertEquals("DECLINED", paymentCardData.getStatus());

    }
    @Test
    @DisplayName("Should added credit data to database with DECLINED through API")
    void shouldSuccessTransactionWithDeclinedCreditCardThroughAPI() {
        var cardInfo = DataHelper.generateDataWithDeclineCard();
        APIHelper.createCredit(cardInfo);
        var creditCardData = SQLHelper.getCreditCardData();
        assertEquals("DECLINED", creditCardData.getStatus());

    }

    @Test
    @DisplayName("Should added correct created date in payment table with APPROVED card")
    void shouldAddedCorrectDateInPaymentTableWithApprovedCard() {
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        APIHelper.createPayment(cardInfo);
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        var paymentCardData = SQLHelper.getPaymentCardData();
        String dateFromDB = paymentCardData.getCreated();
        var dateDB = dateFromDB.substring(0, dateFromDB.length() - 10);
        assertEquals(formatForDateNow.format(dateNow), dateDB);
    }

    @Test
    @DisplayName("Should added correct created date in credit table with APPROVED card")
    void shouldAddedCorrectDateInCreditTableWithApprovedCard() {
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        APIHelper.createCredit(cardInfo);
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        var creditCardData = SQLHelper.getCreditCardData();
        String dateFromDB = creditCardData.getCreated();
        var dateDB = dateFromDB.substring(0, dateFromDB.length() - 10);
        assertEquals(formatForDateNow.format(dateNow), dateDB);
    }

    @Test
    @DisplayName("Should added correct created date in payment table with DECLINED card")
    void shouldAddedCorrectDateInPaymentTableWithDeclinedCard() {
        var cardInfo = DataHelper.generateDataWithDeclineCard();
        APIHelper.createPayment(cardInfo);
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        var paymentCardData = SQLHelper.getPaymentCardData();
        String dateFromDB = paymentCardData.getCreated();
        var dateDB = dateFromDB.substring(0, dateFromDB.length() - 10);
        assertEquals(formatForDateNow.format(dateNow), dateDB);
    }

    @Test
    @DisplayName("Should added correct created date in credit table with DECLINED card")
    void shouldAddedCorrectDateInCreditTableWithDeclinedCard() {
        var cardInfo = DataHelper.generateDataWithDeclineCard();
        APIHelper.createCredit(cardInfo);
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        var creditCardData = SQLHelper.getCreditCardData();
        String dateFromDB = creditCardData.getCreated();
        var dateDB = dateFromDB.substring(0, dateFromDB.length() - 10);
        assertEquals(formatForDateNow.format(dateNow), dateDB);
    }

}
