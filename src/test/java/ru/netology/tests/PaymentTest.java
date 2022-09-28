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

public class PaymentTest {

    @BeforeEach
    void setUP() {
        Configuration.holdBrowserOpen = true;
    }

    @AfterEach
    void tearDown() {
        closeWindow();
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Test
    void testMyCode() {
        open("http://localhost:8080/");
        SQLHelper.cleanDatabase();

//        System.out.println(SQLHelper.getCreditCardData());
//        System.out.println(SQLHelper.getPaymentCardData());

    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Test
    @DisplayName("Should approved payment card with approved test card")
    void shouldSuccessTransactionWithPaymentCard() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataForBank(cardInfo);
        paymentPage.checkApprovedMessFromBank();
    }


    @Test
    @DisplayName("Should decline payment card with declined test card")
    void shouldNotSuccessTransactionWithPaymentCard() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithDeclineCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataForBank(cardInfo);
        paymentPage.checkErrorMessDeclineFromBank();
    }
//    граничные значения срока действия карты "ГОД" (максимум 5 лет)
//    граничные значения срока действия карты "ГОД" (минимум 0 мес, в текущем месяце карта ещё должна быть действительна)
//    граничные значения по длине имени владельца карты (максимум 21 символ, включая пробел)
//    граничные значения по длине имени владельца карты (минимум 3 символа, включая пробел) - здесь я уже фантазирую, но мне кажется в этом есть смысл.


    @Test
    @DisplayName("Should decline payment card with random test card")
    void shouldDeclineWithRandomPaymentCard() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithRandomCardNumber();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataForBank(cardInfo);
        paymentPage.checkErrorMessDeclineFromBank();
    }


    //    оставить поля пустыми (проверить все по очереди)
    @Test
    @DisplayName("Should to show red warning with empty card number field")
    void shouldShowMessWithEmptyCardNumberField() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithRandomCardNumber();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataWithEmptyCardNumberField(cardInfo);
        paymentPage.checkWarningUnderCardNumberField("Неверный формат");
    }

//    дата с истёкшим сроком действия карты
//    некорректный месяц (например, "78")
//    граничные значения срока действия карты "ГОД" (максимум 5 лет)
//    граничные значения срока действия карты "ГОД" (минимум 0 мес, в текущем месяце карта ещё должна быть действительна)
//    граничные значения по длине имени владельца карты (максимум 21 символ, включая пробел)
//    граничные значения по длине имени владельца карты (минимум 3 символа, включая пробел)


}
