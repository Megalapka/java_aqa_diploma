package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.MainPage;


import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;

public class BDTest {

    MainPage mainPage = open("http://localhost:8080/", MainPage.class);

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @BeforeEach
    void setUP() {
        Configuration.holdBrowserOpen = true;
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
    @AfterEach
    void tearDown() {
        closeWindow();
    }

    @Test
    @DisplayName("Should added payment data to database with APPROVED")
    void shouldSuccessTransactionWithApprovedPaymentCard() {
      var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
        var paymentCardData = SQLHelper.getPaymentCardData();
        Assertions.assertEquals("APPROVED", paymentCardData.getStatus());
        System.out.println(paymentCardData.getCreated());

        Date dateNow = new Date();
        String dateFromDB = paymentCardData.getCreated();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        var dateDB = dateFromDB.substring(0, dateFromDB.length() - 10);
        Assertions.assertEquals(formatForDateNow.format(dateNow), dateDB);

    }
}
