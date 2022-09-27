package ru.netology.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;

public class PaymentPage {
    private SelenideElement heading = Selenide.$x("//h3[text()='Оплата по карте']");

    private SelenideElement cardNumberField = Selenide.$x("//span[text()='Номер карты']/following-sibling::span/input");
    private SelenideElement monthField = Selenide.$x("//span[text()='Месяц']/following-sibling::span/input");
    private SelenideElement yearField = Selenide.$x("//span[text()='Год']/following-sibling::span/input");
    private SelenideElement cardOwnerField = Selenide.$x("//span[text()='Владелец']/following-sibling::span/input");
    private SelenideElement cvcField = Selenide.$x("//span[text()='CVC/CVV']/following-sibling::span/input");

    private SelenideElement proceedBtn = Selenide.$x("//span[text()='Продолжить']");
    private SelenideElement errorMessWithDecline = Selenide.$x("//div[text()='Ошибка! Банк отказал в проведении операции.']");
    private SelenideElement approvedMess = Selenide.$x("//div[text()='Операция одобрена Банком.']");

    public PaymentPage() {
        heading.shouldBe(visible);
    }

    public void insertPaymentCardDataForBank(DataHelper.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getCardNumber());
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        cardOwnerField.setValue(cardInfo.getCardOwner());
        cvcField.setValue(cardInfo.getCvc());
        proceedBtn.click();
    }

    public void checkErrorMessDeclineFromBank() {
        errorMessWithDecline.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void checkApprovedMessFromBank() {
        approvedMess.shouldBe(visible, Duration.ofSeconds(15));
    }
}
