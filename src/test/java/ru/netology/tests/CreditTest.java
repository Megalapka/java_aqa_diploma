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

    MainPage mainPage = open("http://localhost:8080/", MainPage.class);


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
        //var mainPage = open("http://localhost:8080/", MainPage.class);

        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkApprovedMessFromBank();
    }


    @Test
    @DisplayName("Should decline credit card with declined test card")
    void shouldNotSuccessTransactionWithCreditCard() {
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclineCard();
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkErrorMessDeclineFromBank();
    }

    @Test
    @DisplayName("Should approved credit card with approved test card and max date")
    void shouldSuccessTransactionWithMaxAllowedDate() {
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
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(21);
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved credit card with approved test card and min length card owner's name")
    void shouldSuccessTransactionMinLengthCardOwnerName() {
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(3);
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should decline credit card with random test card")
    void shouldDeclineWithRandomCreditCard() {
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithRandomCardNumber();
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkErrorMessDeclineFromBank();
    }

    @Test
    @DisplayName("Should to show red warning with empty card number field")
    void shouldShowMessWithEmptyCardNumberField() {
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var creditPage = new CreditPage();
        creditPage.insertCreditCardDataWithEmptyCardNumberField(cardInfo);
        creditPage.checkWarningUnderCardNumberField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty month field")
    void shouldShowMessWithEmptyMonthField() {
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var creditPage = new CreditPage();
        creditPage.insertCreditCardDataWithEmptyMonthField(cardInfo);
        creditPage.checkWarningUnderMonthField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty year field")
    void shouldShowMessWithEmptyYearField() {
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var creditPage = new CreditPage();
        creditPage.insertCreditCardDataWithEmptyYearField(cardInfo);
        creditPage.checkWarningUnderYearField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty card owner field")
    void shouldShowMessWithEmptyCardOwnerField() {
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var creditPage = new CreditPage();
        creditPage.insertCreditCardDataWithEmptyCardOwnerField(cardInfo);
        creditPage.checkWarningUnderCardOwnerField("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Should to show red warning with empty cvc field")
    void shouldShowMessWithEmptyCvcField() {
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var creditPage = new CreditPage();
        creditPage.insertCreditCardDataWithEmptyCvcField(cardInfo);
        creditPage.checkWarningUnderCvcField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty all field")
    void shouldShowMessWithEmptyAllField() {
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
        mainPage.creditPage();
        var currentYear = DataHelper.getCurrentYear();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear("19",
                currentYear);
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkWarningUnderMonthField("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should o show red warning with more max length card owner's name by one char")
    void shouldShowMessWithMoreMaxLengthCardOwnerName() {
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(22);
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkWarningUnderCardOwnerField("Имя не должно быть длинее 21 символа");
    }

    @Test
    @DisplayName("Should o show red warning with less min length card owner's name by one char")
    void shouldShowMessWithLessMinLengthCardOwnerName() {
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(2);
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkWarningUnderCardOwnerField("Имя не должно быть короче 3 символов");
    }
    @Test
    @DisplayName("Should o show red warning with card owner's name is written in Cyrillic")
    void shouldShowMessWithCyrillicCardOwnerName() {
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerName("ИВАНОВ ФЁДОР");
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkWarningUnderCardOwnerField("Неверный формат");
    }

    @Test
    @DisplayName("Should o show red warning with card owner's name with numbers")
    void shouldShowMessWithNumbersCardOwnerName() {
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerName("ИВАН08456 ФЁДОР");
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkWarningUnderCardOwnerField("Неверный формат");
    }

    @Test
    @DisplayName("Should o show red warning with card owner's name with special characters")
    void shouldShowMessWithSpecCharactersCardOwnerName() {
        mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerName("@#%$^$%&>??<");
        var creditPage = new CreditPage();
        creditPage.insertValidCreditCardDataForBank(cardInfo);
        creditPage.checkWarningUnderCardOwnerField("Неверный формат");
    }
}
