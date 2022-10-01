package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pages.MainPage;
import ru.netology.pages.PaymentPage;

import java.util.Locale;

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
//        SQLHelper.cleanDatabase();
        Faker faker = new Faker(new Locale("en"));
        var random = faker.lorem().fixedString(21);
        var random1 = faker.lorem().fixedString(21);
        System.out.println(random1);


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
    @DisplayName("Should approved payment card with approved test card and max date")
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

    @Test
    @DisplayName("Should approved payment card with approved test card and max date minus 1 month")
    void shouldSuccessTransactionWithPreMaxAllowedDate() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
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
        var mainPage = open("http://localhost:8080/", MainPage.class);
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
        var mainPage = open("http://localhost:8080/", MainPage.class);
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
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithMaxLengthCardOwnerName();
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and min length card owner's name")
    void shouldSuccessTransactionMinLengthCardOwnerName() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithMinLengthCardOwnerName();
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkApprovedMessFromBank();
    }

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

    @Test
    @DisplayName("Should to show red warning with expired card for year")
    void shouldShowMessWithExpiredCardForYear() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
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
        var mainPage = open("http://localhost:8080/", MainPage.class);
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
    @DisplayName("Should to show red warning with invalid month data")
    void shouldShowMessWithInvalidMonthData() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        mainPage.paymentPage();
        var currentYear = DataHelper.getCurrentYear();

        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear("78",
                currentYear);
        var paymentPage = new PaymentPage();
        paymentPage.insertValidPaymentCardDataForBank(cardInfo);
        paymentPage.checkWarningUnderMonthField("Неверно указан срок действия карты");
    }

//    граничные значения срока действия карты "ГОД" (максимум 5 лет)
//    граничные значения срока действия карты "ГОД" (минимум 0 мес, в текущем месяце карта ещё должна быть действительна)
//    граничные значения по длине имени владельца карты (максимум 21 символ, включая пробел)
//    граничные значения по длине имени владельца карты (минимум 3 символа, включая пробел)
//    ввод в поле "Владелец" имя на кирилице
//    ввод в поле "Владелец" цифры
//    ввод в поле "Владелец" спецсимволы

}
