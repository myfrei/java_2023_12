package ru.example.service;

import ru.example.model.enums.Banknote;

public interface AtmService {
    void addCashCassette(CashCassette cashCassette);
    void removeCashCassette(Banknote banknoteType);
    void deposit(Banknote denomination, int count);
    boolean withdraw(int amount);
    int getBalance();
}