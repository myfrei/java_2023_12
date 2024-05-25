package ru.example;

import ru.example.model.enums.Banknote;
import ru.example.service.AtmService;
import ru.example.service.impl.AtmServiceImpl;
import ru.example.service.CashCassette;
import ru.example.service.impl.CashCassetteImpl;

public class Main {
    public static void main(String[] args) {
        AtmService atm = new AtmServiceImpl();

        CashCassette cassette100 = new CashCassetteImpl(Banknote.DENOMINATION_100, 100);
        CashCassette cassette500 = new CashCassetteImpl(Banknote.DENOMINATION_500, 100);
        CashCassette cassette1000 = new CashCassetteImpl(Banknote.DENOMINATION_1000, 100);
        CashCassette cassette5000 = new CashCassetteImpl(Banknote.DENOMINATION_5000, 50);

        atm.addCashCassette(cassette100);
        atm.addCashCassette(cassette500);
        atm.addCashCassette(cassette1000);
        atm.addCashCassette(cassette5000);

        atm.deposit(Banknote.DENOMINATION_100, 5);
        atm.deposit(Banknote.DENOMINATION_500, 10);
        atm.deposit(Banknote.DENOMINATION_1000, 7);
        atm.deposit(Banknote.DENOMINATION_5000, 3);

        System.out.println("Current Balance: " + atm.getBalance());

        atm.withdraw(2700);
        System.out.println("Current Balance: " + atm.getBalance());

        atm.withdraw(6000);
        System.out.println("Current Balance: " + atm.getBalance());

        atm.removeCashCassette(Banknote.DENOMINATION_100);
    }
}