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

public class PaymentTest {

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
    @DisplayName("Should approved payment card with approved test card")
    void shouldSuccessTransactionWithPaymentCard() {
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkApprovedMessFromBank();
    }


    @Test
    @DisplayName("Should decline payment card with declined test card")
    void shouldNotSuccessTransactionWithPaymentCard() {
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithDeclineCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkErrorMessDeclineFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with month by one digit")
    void shouldSuccessTransactionWithMonthWithoutZero() {
        mainPage.paymentPage();
        var validYear = Integer.parseInt(DataHelper.getCurrentYear()) + 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                ("5", String.valueOf(validYear));
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and max date")
    void shouldSuccessTransactionWithMaxAllowedDate() {
        mainPage.paymentPage();
        var currentMonth = DataHelper.getCurrentMonth();
        var maxYear = Integer.parseInt(DataHelper.getCurrentYear()) + 5;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear(currentMonth,
                String.valueOf(maxYear));
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and max date minus 1 month")
    void shouldSuccessTransactionWithPreMaxAllowedDate() {
        mainPage.paymentPage();
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
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and min date(current month)")
    void shouldSuccessTransactionWithMinAllowedDate() {
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                (DataHelper.getCurrentMonth(),DataHelper.getCurrentYear());
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and min date next month)")
    void shouldSuccessTransactionWithPreMinAllowedDate() {
        mainPage.paymentPage();
        var nextMonth = Integer.parseInt(DataHelper.getCurrentMonth()) + 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                (String.valueOf(nextMonth),DataHelper.getCurrentYear());
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and max length card owner's name")
    void shouldSuccessTransactionMaxLengthCardOwnerName() {
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(21);
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and min length card owner's name")
    void shouldSuccessTransactionMinLengthCardOwnerName() {
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(3);
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should decline payment card with random test card")
    void shouldDeclineWithRandomPaymentCard() {
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithRandomCardNumber();
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkErrorMessDeclineFromBank();
    }

    @Test
    @DisplayName("Should to show red warning with empty card number field")
    void shouldShowMessWithEmptyCardNumberField() {
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataWithEmptyCardNumberField(cardInfo);
        paymentPage.checkWarningUnderCardNumberField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty month field")
    void shouldShowMessWithEmptyMonthField() {
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataWithEmptyMonthField(cardInfo);
        paymentPage.checkWarningUnderMonthField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty year field")
    void shouldShowMessWithEmptyYearField() {
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataWithEmptyYearField(cardInfo);
        paymentPage.checkWarningUnderYearField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty card owner field")
    void shouldShowMessWithEmptyCardOwnerField() {
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataWithEmptyCardOwnerField(cardInfo);
        paymentPage.checkWarningUnderCardOwnerField("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Should to show red warning with empty cvc field")
    void shouldShowMessWithEmptyCvcField() {
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        var paymentPage = new PaymentPage();
        paymentPage.insertPaymentCardDataWithEmptyCvcField(cardInfo);
        paymentPage.checkWarningUnderCvcField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty all field")
    void shouldShowMessWithEmptyAllField() {
        mainPage.paymentPage();
        var paymentPage = new PaymentPage();
        paymentPage.clickProceedButton();
        paymentPage.checkWarningUnderCardNumberField("Неверный формат");
        paymentPage.checkWarningUnderMonthField("Неверный формат");
        paymentPage.checkWarningUnderYearField("Неверный формат");
        paymentPage.checkWarningUnderCardOwnerField("Поле обязательно для заполнения");
        paymentPage.checkWarningUnderCvcField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with expired card for year")
    void shouldShowMessWithExpiredCardForYear() {
        mainPage.paymentPage();
        var currentMonth = DataHelper.getCurrentMonth();
        var lastYear = Integer.parseInt(DataHelper.getCurrentYear()) - 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear(currentMonth,
                String.valueOf(lastYear));
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkWarningUnderYearField("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Should to show red warning with expired card for month")
    void shouldShowMessWithExpiredCardForMonth() {
        mainPage.paymentPage();
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
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkWarningUnderMonthField("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should to show red warning with 00 month")
    void shouldShowMessWithZeroZeroMonth() {
        mainPage.paymentPage();
        var validYear = Integer.parseInt(DataHelper.getCurrentYear()) + 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                ("00", String.valueOf(validYear));
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkWarningUnderMonthField("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should to show red warning with invalid month data")
    void shouldShowMessWithInvalidMonthData() {
        mainPage.paymentPage();
        var currentYear = DataHelper.getCurrentYear();

        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear("78",
                currentYear);
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkWarningUnderMonthField("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should o show red warning with more max length card owner's name by one char")
    void shouldShowMessWithMoreMaxLengthCardOwnerName() {
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(22);
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkWarningUnderCardOwnerField("Имя не должно быть длинее 21 символа");
    }

    @Test
    @DisplayName("Should o show red warning with less min length card owner's name by one char")
    void shouldShowMessWithLessMinLengthCardOwnerName() {
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(2);
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkWarningUnderCardOwnerField("Имя не должно быть короче 3 символов");
    }

    @Test
    @DisplayName("Should o show red warning with card owner's name is written in Cyrillic")
    void shouldShowMessWithCyrillicCardOwnerName() {
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerName("ИВАНОВ ФЁДОР");
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkWarningUnderCardOwnerField("Неверный формат");
    }

    @Test
    @DisplayName("Should o show red warning with card owner's name with numbers")
    void shouldShowMessWithNumbersCardOwnerName() {
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerName("ИВАН08456 ФЁДОР");
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkWarningUnderCardOwnerField("Неверный формат");
    }

    @Test
    @DisplayName("Should o show red warning with card owner's name with special characters")
    void shouldShowMessWithSpecCharactersCardOwnerName() {
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerName("@#%$^$%&>??<");
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkWarningUnderCardOwnerField("Неверный формат");
    }

}
