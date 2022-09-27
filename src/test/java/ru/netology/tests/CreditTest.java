package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pages.CreditPage;
import ru.netology.pages.MainPage;

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
//    дата с истёкшим сроком действия карты
//    оставить поля пустыми (проверить все по очереди, кроме поля "Владелец")
//    некорректный месяц (например, "19")
//    граничные значения срока действия карты "ГОД" (максимум 3 года)
//    граничные значения срока действия карты "ГОД" (минимум 0 мес, в текущем месяце карта ещё должна быть действительна)
//    граничные значения по длине имени владельца карты (максимум 21 символ, включая пробел)
//    граничные значения по длине имени владельца карты (минимум 3 символа, включая пробел)
}
