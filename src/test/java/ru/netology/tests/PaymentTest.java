package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
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
        //SQLHelper.cleanDatabase();


    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Test
    @DisplayName("Should approved payment card with approved test card")
    void shouldSuccessTransactionWithPaymentCard() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkApprovedMessFromBank();
    }


    @Test
    @DisplayName("Should decline payment card with declined test card")
    void shouldNotSuccessTransactionWithPaymentCard() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithDeclineCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkErrorMessDeclineFromBank();
    }

    @Test
    @DisplayName("Should decline payment card with approved test card and max date")
    void shouldSuccessTransactionWithMaxAllowedDate() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.paymentPage();
        var currentMonth = DataHelper.getCurrentMonth();
        var maxYear = Integer.parseInt(DataHelper.getCurrentYear()) + 5;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear(currentMonth,
                String.valueOf(maxYear));
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkApprovedMessFromBank();
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
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkErrorMessDeclineFromBank();
    }

    @Test
    @DisplayName("Should to show red warning with empty card number field")
    void shouldShowMessWithEmptyCardNumberField() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataWithEmptyCardNumberField(cardInfo);
        paymentPage.checkWarningUnderCardNumberField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty month field")
    void shouldShowMessWithEmptyMonthField() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataWithEmptyMonthField(cardInfo);
        paymentPage.checkWarningUnderMonthField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty year field")
    void shouldShowMessWithEmptyYearField() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataWithEmptyYearField(cardInfo);
        paymentPage.checkWarningUnderYearField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty card owner field")
    void shouldShowMessWithEmptyCardOwnerField() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataWithEmptyCardOwnerField(cardInfo);
        paymentPage.checkWarningUnderCardOwnerField("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Should to show red warning with empty cvc field")
    void shouldShowMessWithEmptyCvcField() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataWithEmptyCvcField(cardInfo);
        paymentPage.checkWarningUnderCvcField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty all field")
    void shouldShowMessWithEmptyAllField() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.paymentPage();
        var paymentPage = new PaymentPage();
        paymentPage.clickProceedButton();
        paymentPage.checkWarningUnderCardNumberField("Неверный формат");
        paymentPage.checkWarningUnderMonthField("Неверный формат");
        paymentPage.checkWarningUnderYearField("Неверный формат");
        paymentPage.checkWarningUnderCardOwnerField("Поле обязательно для заполнения");
        paymentPage.checkWarningUnderCvcField("Неверный формат");
    }


//    дата с истёкшим сроком действия карты
//    некорректный месяц (например, "78")
//    граничные значения срока действия карты "ГОД" (максимум 5 лет)
//    граничные значения срока действия карты "ГОД" (минимум 0 мес, в текущем месяце карта ещё должна быть действительна)
//    граничные значения по длине имени владельца карты (максимум 21 символ, включая пробел)
//    граничные значения по длине имени владельца карты (минимум 3 символа, включая пробел)


}
