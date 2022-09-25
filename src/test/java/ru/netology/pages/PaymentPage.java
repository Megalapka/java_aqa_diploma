package ru.netology.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;

public class PaymentPage {
    private SelenideElement heading = Selenide.$x("//h3[text()='Оплата по карте']");

    private SelenideElement cardNumber = Selenide.$x("//span[text()='Номер карты']/following-sibling::span/input");
    private SelenideElement month = Selenide.$x("//span[text()='Месяц']/following-sibling::span/input");
    private SelenideElement year = Selenide.$x("//span[text()='Год']/following-sibling::span/input");
    private SelenideElement cardOwner = Selenide.$x("//span[text()='Владелец']/following-sibling::span/input");
    private SelenideElement cvc = Selenide.$x("//span[text()='CVC/CVV']/following-sibling::span/input");

    private SelenideElement proceedBtn = Selenide.$x("//span[text()='Продолжить']");
    private SelenideElement errorMessDecline = Selenide.$x("//div[text()='Ошибка! Банк отказал в проведении операции.']");
    private SelenideElement approvedMess = Selenide.$x("//div[text()='Операция одобрена Банком.']");

    public PaymentPage() {
        heading.shouldBe(visible);
    }

    public void insertPaymentCardDataForBank(DataHelper.CardInfo cardInfo) {
        cardNumber.setValue(cardInfo.getCardNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        cardOwner.setValue(cardInfo.getCardOwner());
        cvc.setValue(cardInfo.getCvc());
        proceedBtn.click();
    }

    public void errorMessDeclineFromBank() {
        errorMessDecline.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void approvedMessFromBank() {
        approvedMess.shouldBe(visible, Duration.ofSeconds(15));
    }
}
