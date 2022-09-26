package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.CreditPage;
import ru.netology.pages.MainPage;
import ru.netology.pages.PaymentPage;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;

public class UITest {

    @BeforeEach
    void setUP() {
        Configuration.holdBrowserOpen = true;
    }

    @AfterEach
    void tearDown() {
        closeWindow();
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Test
    void testMyCode() {
        open("http://localhost:8080/");
        SQLHelper.cleanDatabase();

//        System.out.println(SQLHelper.getCreditCardData());
//        System.out.println(SQLHelper.getPaymentCardData());

    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


//1. Тестирование формы оплаты картой:

//позитивные

    // с картой, которая будет одобрена
    @Test
    @DisplayName("Should approved payment card with approved test card")
    void shouldSuccessTransactionWithPaymentCard() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataForBank(cardInfo);
        paymentPage.approvedMessFromBank();
    }

    //с картой, которой будет отказано
    @Test
    @DisplayName("Should decline payment card with declined test card")
    void shouldNotSuccessTransactionWithPaymentCard() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithDeclineCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataForBank(cardInfo);
        paymentPage.errorMessDeclineFromBank();
    }
//    граничные значения срока действия карты "ГОД" (максимум 5 лет)
//    граничные значения срока действия карты "ГОД" (минимум 0 мес, в текущем месяце карта ещё должна быть действительна)
//    граничные значения по длине имени владельца карты (максимум 21 символ, включая пробел)
//    граничные значения по длине имени владельца карты (минимум 3 символа, включая пробел) - здесь я уже фантазирую, но мне кажется в этом есть смысл.

// негативные

    //со случайным номером карты
    @Test
    @DisplayName("Should decline payment card with random test card")
    void shouldDeclineWithRandomPaymentCard() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithRandomCardNumber();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataForBank(cardInfo);
        paymentPage.errorMessDeclineFromBank();
    }

//    дата с истёкшим сроком действия карты
//    оставить поля пустыми (проверить все по очереди)
//    некорректный месяц (например, "78")
//    граничные значения срока действия карты "ГОД" (максимум 5 лет)
//    граничные значения срока действия карты "ГОД" (минимум 0 мес, в текущем месяце карта ещё должна быть действительна)
//    граничные значения по длине имени владельца карты (максимум 21 символ, включая пробел)
//    граничные значения по длине имени владельца карты (минимум 3 символа, включая пробел)

//2. Тестирование формы оплаты в кредит

//позитивные

    // с картой, которая будет одобрена
    @Test
    @DisplayName("Should approved credit card with approved test card")
    void shouldSuccessTransactionWithCreditCard() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var creditPage = new CreditPage();
        creditPage.insertCreditCardDataForBank(cardInfo);
        creditPage.approvedMessFromBank();
    }

    //с картой, которой будет отказано
    @Test
    @DisplayName("Should decline credit card with declined test card")
    void shouldNotSuccessTransactionWithCreditCard() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclineCard();
        var creditPage = new CreditPage();
        creditPage.insertCreditCardDataForBank(cardInfo);
        creditPage.errorMessDeclineFromBank();
    }

//    граничные значения срока действия карты "ГОД" (максимум 5 лет)
//    граничные значения срока действия карты "ГОД" (минимум 0 мес, в текущем месяце карта ещё должна быть действительна)
//    граничные значения по длине имени владельца карты (максимум 21 символ, включая пробел)
//    граничные значения по длине имени владельца карты (минимум 3 символа, включая пробел) - здесь я уже фантазирую, но мне кажется в этом есть смысл.

// негативные

    //со случайным номером карты
    @Test
    @DisplayName("Should decline payment card with random test card")
    void shouldDeclineWithRandomCreditCard() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithRandomCardNumber();
        var creditPage = new CreditPage();
        creditPage.insertCreditCardDataForBank(cardInfo);
        creditPage.errorMessDeclineFromBank();
    }
//    дата с истёкшим сроком действия карты
//    оставить поля пустыми (проверить все по очереди, кроме поля "Владелец")
//    некорректный месяц (например, "19")
//    граничные значения срока действия карты "ГОД" (максимум 3 года)
//    граничные значения срока действия карты "ГОД" (минимум 0 мес, в текущем месяце карта ещё должна быть действительна)
//    граничные значения по длине имени владельца карты (максимум 21 символ, включая пробел)
//    граничные значения по длине имени владельца карты (минимум 3 символа, включая пробел)
}
