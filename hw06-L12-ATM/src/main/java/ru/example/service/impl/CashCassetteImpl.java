package ru.example.service.impl;

import ru.example.model.CounterBanknotes;
import ru.example.model.enums.Banknote;
import ru.example.service.CashCassette;

public class CashCassetteImpl implements CashCassette {
    private static int globalID = 1;
    private final int id;
    private final CounterBanknotes counterBanknotes;
    private int idAtm;
    private boolean insideAtm = false;
    private final int maxCountBanknotes;

    public CashCassetteImpl(Banknote banknoteType, int maxCountBanknotes) {
        this.id = globalID++;
        if (maxCountBanknotes < 0) {
            throw new RuntimeException("The number of banknotes cannot be negative");
        }
        this.counterBanknotes = new CounterBanknotes(banknoteType, 0);
        this.maxCountBanknotes = maxCountBanknotes;
    }

    @Override
    public void setOwnerAtm(int idAtm) {
        if (insideAtm) {
            throw new RuntimeException("The cartridge is already installed in the terminal");
        } else {
            this.idAtm = idAtm;
            insideAtm = true;
        }
    }

    @Override
    public int getOwnerAtm() {
        if (!insideAtm) {
            throw new RuntimeException("The cartridge doesn't have an owner");
        }
        return idAtm;
    }

    @Override
    public void removedFromAtmCassette() {
        if (!insideAtm) {
            throw new RuntimeException("An attempt to remove a non-existent owner.");
        }
        insideAtm = false;
    }

    @Override
    public void addBanknotes(int count) {
        if (count < 0) {
            throw new RuntimeException("A negative number of bills has been received. count=" + count);
        }
        if (counterBanknotes.countBanknotes() + count > maxCountBanknotes) {
            throw new RuntimeException("The number of bills exceeds the available space");
        }
        counterBanknotes.addBanknotes(count);
    }

    @Override
    public void getBanknotes(int count) {
        counterBanknotes.getBanknotes(count);
    }

    @Override
    public int getMaxCountBanknotes() {
        return maxCountBanknotes;
    }

    @Override
    public Banknote getBanknoteType() {
        return counterBanknotes.getBanknoteType();
    }

    @Override
    public int getCurrCountBanknote() {
        return counterBanknotes.countBanknotes();
    }

    @Override
    public CashCassette clone() {
        CashCassetteImpl cashCassette = new CashCassetteImpl(getBanknoteType(), getMaxCountBanknotes());
        cashCassette.setOwnerAtm(this.idAtm);
        cashCassette.counterBanknotes.addBanknotes(this.counterBanknotes.countBanknotes());
        return cashCassette;
    }

    @Override
    public String toString() {
        return "id=" + this.id + " " + getCurrCountBanknote() + "(" + getBanknoteType().getDenomination() + ")";
    }

    @Override
    public boolean isInsideAtm() {
        return insideAtm;
    }
}