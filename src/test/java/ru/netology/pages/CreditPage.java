package ru.netology.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class CreditPage {
    private SelenideElement heading = Selenide.$x("//h3[text()='Кредит по данным карты']");

    private SelenideElement cardNumberField = Selenide.$x("//span[text()='Номер карты']" +
            "/following-sibling::span/input");
    private SelenideElement monthField = Selenide.$x("//span[text()='Месяц']" +
            "/following-sibling::span/input");
    private SelenideElement yearField = Selenide.$x("//span[text()='Год']" +
            "/following-sibling::span/input");
    private SelenideElement cardOwnerField = Selenide.$x("//span[text()='Владелец']" +
            "/following-sibling::span/input");
    private SelenideElement cvcField = Selenide.$x("//span[text()='CVC/CVV']" +
            "/following-sibling::span/input");

    private SelenideElement proceedBtn = Selenide.$x("//span[text()='Продолжить']");
    private SelenideElement errorMessWithDecline = Selenide.$x("//div[text()='Ошибка!" +
            " Банк отказал в проведении операции.']");
    private SelenideElement approvedMess = Selenide.$x("//div[text()='Операция одобрена Банком.']");

    private SelenideElement warningCardNumberField = Selenide.$x("//span[text()='Номер карты']" +
            "/following-sibling::span[@class='input__sub']");
    private SelenideElement warningMonthField = Selenide.$x("//span[text()='Месяц']" +
            "/following-sibling::span[@class='input__sub']");
    private SelenideElement warningYearField = Selenide.$x("//span[text()='Год']" +
            "/following-sibling::span[@class='input__sub']");
    private SelenideElement warningCardOwnerField = Selenide.$x("//span[text()='Владелец']" +
            "/following-sibling::span[@class='input__sub']");
    private SelenideElement warningCvcField = Selenide.$x("//span[text()='CVC/CVV']" +
            "/following-sibling::span[@class='input__sub']");

    public CreditPage() {
        heading.shouldBe(visible);
    }

    public void insertValidCreditCardDataForBank(DataHelper.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getCardNumber());
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        cardOwnerField.setValue(cardInfo.getCardOwner());
        cvcField.setValue(cardInfo.getCvc());
        proceedBtn.click();
    }

    public  void clickProceedButton() {
        proceedBtn.click();
    }
    public void checkErrorMessDeclineFromBank() {
        errorMessWithDecline.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void checkApprovedMessFromBank() {
        approvedMess.shouldBe(visible, Duration.ofSeconds(15));
    }


    public void insertCreditCardDataWithEmptyCardNumberField(DataHelper.CardInfo cardInfo) {
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        cardOwnerField.setValue(cardInfo.getCardOwner());
        cvcField.setValue(cardInfo.getCvc());
        proceedBtn.click();
    }

    public void checkWarningUnderCardNumberField(String warningText) {
        warningCardNumberField.shouldBe(visible);
        warningCardNumberField.shouldHave(text(warningText));
    }

    public void insertCreditCardDataWithEmptyMonthField(DataHelper.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getCardNumber());
        yearField.setValue(cardInfo.getYear());
        cardOwnerField.setValue(cardInfo.getCardOwner());
        cvcField.setValue(cardInfo.getCvc());
        proceedBtn.click();
    }
    public void checkWarningUnderMonthField(String warningText) {
        warningMonthField.shouldBe(visible);
        warningMonthField.shouldHave(text(warningText));
    }

    public void insertCreditCardDataWithEmptyYearField(DataHelper.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getCardNumber());
        monthField.setValue(cardInfo.getMonth());
        cardOwnerField.setValue(cardInfo.getCardOwner());
        cvcField.setValue(cardInfo.getCvc());
        proceedBtn.click();
    }
    public void checkWarningUnderYearField(String warningText) {
        warningYearField.shouldBe(visible);
        warningYearField.shouldHave(text(warningText));
    }

    public void insertCreditCardDataWithEmptyCardOwnerField(DataHelper.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getCardNumber());
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        cvcField.setValue(cardInfo.getCvc());
        proceedBtn.click();
    }
    public void checkWarningUnderCardOwnerField(String warningText) {
        warningCardOwnerField.shouldBe(visible);
        warningCardOwnerField.shouldHave(text(warningText));
    }

    public void insertCreditCardDataWithEmptyCvcField(DataHelper.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getCardNumber());
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        cardOwnerField.setValue(cardInfo.getCardOwner());
        proceedBtn.click();
    }
    public void checkWarningUnderCvcField(String warningText) {
        warningCvcField.shouldBe(visible);
        warningCvcField.shouldHave(text(warningText));
    }

    public void notCheckWarningUnderAllFields() {
        warningCardNumberField.shouldNotBe(visible);
        warningMonthField.shouldNotBe(visible);
        warningYearField.shouldNotBe(visible);
        warningCardOwnerField.shouldNotBe(visible);
        warningCvcField.shouldNotBe(visible);
    }
}
