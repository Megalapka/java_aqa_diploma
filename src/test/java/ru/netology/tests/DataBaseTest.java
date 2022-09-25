package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.MainPage;
import ru.netology.pages.PaymentPage;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;

public class DataBaseTest {

    @BeforeEach
    void setUP() {
        Configuration.holdBrowserOpen = true;
    }
    @AfterEach
    void tearDown() {
        closeWindow();
    }


    @Test
    void testMyCode() {
        System.out.println(SQLHelper.getCreditCardData());
        System.out.println(SQLHelper.getPaymentCardData());
    }

    @Test
    @DisplayName("Should approved payment card with fist test card")
    void shouldSuccessTransactionWithPaymentCard() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataForBank(cardInfo);
        paymentPage.approvedMessFromBank();
    }
}
