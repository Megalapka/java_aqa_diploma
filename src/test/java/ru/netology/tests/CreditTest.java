package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pages.CreditPage;
import ru.netology.pages.MainPage;
import ru.netology.pages.PaymentPage;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;

public class CreditTest {

    @BeforeEach
    void setUP() {
        Configuration.holdBrowserOpen = true;
    }

    @AfterEach
    void tearDown() {
        closeWindow();
    }


    @Test
    @DisplayName("Should approved credit card with approved test card")
    void shouldSuccessTransactionWithCreditCard() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var creditPage = new CreditPage();
        creditPage.insertCreditCardDataForBank(cardInfo);
        creditPage.checkApprovedMessFromBank();
    }


    @Test
    @DisplayName("Should decline credit card with declined test card")
    void shouldNotSuccessTransactionWithCreditCard() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclineCard();
        var creditPage = new CreditPage();
        creditPage.insertCreditCardDataForBank(cardInfo);
        creditPage.checkErrorMessDeclineFromBank();
    }

//    граничные значения срока действия карты "ГОД" (максимум 5 лет)
//    граничные значения срока действия карты "ГОД" (минимум 0 мес, в текущем месяце карта ещё должна быть действительна)
//    граничные значения по длине имени владельца карты (максимум 21 символ, включая пробел)
//    граничные значения по длине имени владельца карты (минимум 3 символа, включая пробел) - здесь я уже фантазирую, но мне кажется в этом есть смысл.



    @Test
    @DisplayName("Should decline payment card with random test card")
    void shouldDeclineWithRandomCreditCard() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithRandomCardNumber();
        var creditPage = new CreditPage();
        creditPage.insertCreditCardDataForBank(cardInfo);
        creditPage.checkErrorMessDeclineFromBank();
    }

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

    @Test
    @DisplayName("Should to show red warning with empty card number field")
    void shouldShowMessWithEmptyCardNumberField() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var creditPage = new CreditPage();
        creditPage.insertCreditCardDataWithEmptyCardNumberField(cardInfo);
        creditPage.checkWarningUnderCardNumberField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty month field")
    void shouldShowMessWithEmptyMonthField() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var creditPage = new CreditPage();
        creditPage.insertCreditCardDataWithEmptyMonthField(cardInfo);
        creditPage.checkWarningUnderMonthField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty year field")
    void shouldShowMessWithEmptyYearField() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var creditPage = new CreditPage();
        creditPage.insertCreditCardDataWithEmptyYearField(cardInfo);
        creditPage.checkWarningUnderYearField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty card owner field")
    void shouldShowMessWithEmptyCardOwnerField() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var creditPage = new CreditPage();
        creditPage.insertCreditCardDataWithEmptyCardOwnerField(cardInfo);
        creditPage.checkWarningUnderCardOwnerField("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Should to show red warning with empty cvc field")
    void shouldShowMessWithEmptyCvcField() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var creditPage = new CreditPage();
        creditPage.insertCreditCardDataWithEmptyCvcField(cardInfo);
        creditPage.checkWarningUnderCvcField("Неверный формат");
    }
    //    дата с истёкшим сроком действия карты

//    некорректный месяц (например, "19")
//    граничные значения срока действия карты "ГОД" (максимум 3 года)
//    граничные значения срока действия карты "ГОД" (минимум 0 мес, в текущем месяце карта ещё должна быть действительна)
//    граничные значения по длине имени владельца карты (максимум 21 символ, включая пробел)
//    граничные значения по длине имени владельца карты (минимум 3 символа, включая пробел)
}
