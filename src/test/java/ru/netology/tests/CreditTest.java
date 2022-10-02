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
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkApprovedMessFromBank();
    }


    @Test
    @DisplayName("Should decline credit card with declined test card")
    void shouldNotSuccessTransactionWithCreditCard() {
        var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclineCard();
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkErrorMessDeclineFromBank();
    }

    @Test
    @DisplayName("Should approved credit card with approved test card and max date")
    void shouldSuccessTransactionWithMaxAllowedDate() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.creditPage();
        var currentMonth = DataHelper.getCurrentMonth();
        var maxYear = Integer.parseInt(DataHelper.getCurrentYear()) + 5;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear(currentMonth,
                String.valueOf(maxYear));
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkApprovedMessFromBank();
    }


    @Test
    @DisplayName("Should approved credit card with approved test card and max date minus 1 month")
    void shouldSuccessTransactionWithPreMaxAllowedDate() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.creditPage();
        var currentMonth = Integer.parseInt(DataHelper.getCurrentMonth());
        var preMaxMonth = 0;
        var maxYear = Integer.parseInt(DataHelper.getCurrentYear()) + 5;

        if (currentMonth == 1) {
            preMaxMonth = 12;
            maxYear = maxYear - 1;
        } else preMaxMonth = currentMonth - 1;

        String strPreMaxMonth = "";
        if (preMaxMonth < 10) {
            strPreMaxMonth = "0" + preMaxMonth;
        }

        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear(strPreMaxMonth,
                String.valueOf(maxYear));
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved credit card with approved test card and min date(current month)")
    void shouldSuccessTransactionWithMinAllowedDate() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                (DataHelper.getCurrentMonth(),DataHelper.getCurrentYear());
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved credit card with approved test card and min date next month)")
    void shouldSuccessTransactionWithPreMinAllowedDate() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.creditPage();
        var nextMonth = Integer.parseInt(DataHelper.getCurrentMonth()) + 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                (String.valueOf(nextMonth),DataHelper.getCurrentYear());
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should credit payment card with approved test card and max length card owner's name")
    void shouldSuccessTransactionMaxLengthCardOwnerName() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(21);
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved credit card with approved test card and min length card owner's name")
    void shouldSuccessTransactionMinLengthCardOwnerName() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(3);
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should decline credit card with random test card")
    void shouldDeclineWithRandomCreditCard() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithRandomCardNumber();
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkErrorMessDeclineFromBank();
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

    @Test
    @DisplayName("Should to show red warning with empty all field")
    void shouldShowMessWithEmptyAllField() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.creditPage();
        var creditPage = new CreditPage();
        creditPage.clickProceedButton();
        creditPage.checkWarningUnderCardNumberField("Неверный формат");
        creditPage.checkWarningUnderMonthField("Неверный формат");
        creditPage.checkWarningUnderYearField("Неверный формат");
        creditPage.checkWarningUnderCardOwnerField("Поле обязательно для заполнения");
        creditPage.checkWarningUnderCvcField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with expired card for year")
    void shouldShowMessWithExpiredCardForYear() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.creditPage();
        var currentMonth = DataHelper.getCurrentMonth();
        var lastYear = Integer.parseInt(DataHelper.getCurrentYear()) - 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear(currentMonth,
                String.valueOf(lastYear));
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkWarningUnderYearField("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Should to show red warning with expired card for month")
    void shouldShowMessWithExpiredCardForMonth() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.creditPage();
        var currentMonth = Integer.parseInt(DataHelper.getCurrentMonth());
        var currentYear = Integer.parseInt(DataHelper.getCurrentYear());
        if (currentMonth == 1) {
            currentMonth = 12;
            currentYear = currentYear - 1;
        } else currentMonth = currentMonth - 1;

        String strCurrentMonth = "";
        if (currentMonth < 10) {
            strCurrentMonth = "0" + currentMonth;
        }

        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear(strCurrentMonth,
                String.valueOf(currentYear));
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkWarningUnderMonthField("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should to show red warning with invalid month data")
    void shouldShowMessWithInvalidMonthData() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.creditPage();
        var currentYear = DataHelper.getCurrentYear();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear("19",
                currentYear);
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkWarningUnderMonthField("Неверно указан срок действия карты");
    }

//    граничные значения по длине имени владельца карты (максимум 21 символ, включая пробел)
//    граничные значения по длине имени владельца карты (минимум 3 символа, включая пробел)
//    ввод в поле "Владелец" имя на кирилице
//    ввод в поле "Владелец" цифры
//    ввод в поле "Владелец" спецсимволы
}
