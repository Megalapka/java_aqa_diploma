package ru.netology.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;


public class MainPage {
    private SelenideElement paymentBtn = Selenide.$x("//span[text()='Купить']");
    private SelenideElement creditBtn = Selenide.$x("//span[text()='Купить в кредит']");


    public PaymentPage paymentPage() {

        return new PaymentPage();
    }

    public CreditPage creditPage() {

        return new CreditPage();
    }
}
