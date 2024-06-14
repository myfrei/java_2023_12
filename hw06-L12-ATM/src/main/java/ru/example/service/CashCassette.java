package ru.example.service;

import ru.example.model.enums.Banknote;

public interface CashCassette {
    void setOwnerAtm(int idAtm);
    int getOwnerAtm();
    void addBanknotes(int banknotesCount);
    void getBanknotes(int banknotesCount);
    int getMaxCountBanknotes();
    CashCassette clone();
    boolean isInsideAtm();
    Banknote getBanknoteType();
    int getCurrCountBanknote();
    void removedFromAtmCassette();
}