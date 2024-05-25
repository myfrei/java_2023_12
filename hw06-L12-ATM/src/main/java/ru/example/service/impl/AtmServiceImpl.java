package ru.example.service.impl;

import ru.example.model.enums.Banknote;
import ru.example.service.AtmService;
import ru.example.service.CashCassette;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AtmServiceImpl implements AtmService {
    private final int id;
    private static int globalID = 1;
    private final Map<Banknote, CashCassette> cashCassettes = new HashMap<>();

    public AtmServiceImpl() {
        this.id = globalID++;
    }

    @Override
    public void addCashCassette(CashCassette cashCassette) {
        if (cashCassette.isInsideAtm()) {
            throw new RuntimeException("This cartridge is already installed in the ATM");
        }
        cashCassette.setOwnerAtm(this.id);
        cashCassettes.put(cashCassette.getBanknoteType(), cashCassette);
    }

    @Override
    public void removeCashCassette(Banknote banknoteType) {
        CashCassette cassette = cashCassettes.get(banknoteType);
        if (cassette != null) {
            cassette.removedFromAtmCassette();
            cashCassettes.remove(banknoteType);
        }
    }

    @Override
    public void deposit(Banknote denomination, int count) {
        CashCassette cassette = cashCassettes.get(denomination);
        if (cassette != null) {
            cassette.addBanknotes(count);
        } else {
            throw new RuntimeException("No cassette found for denomination: " + denomination.getDenomination());
        }
    }

    @Override
    public boolean withdraw(int amount) {
        List<Banknote> denominations = new ArrayList<>(cashCassettes.keySet());
        denominations.sort((a, b) -> Integer.compare(b.getDenomination(), a.getDenomination()));

        Map<Banknote, Integer> withdrawal = new HashMap<>();
        int remainingAmount = amount;

        for (Banknote denomination : denominations) {
            CashCassette cassette = cashCassettes.get(denomination);
            int count = Math.min(remainingAmount / denomination.getDenomination(), cassette.getCurrCountBanknote());
            if (count > 0) {
                withdrawal.put(denomination, count);
                remainingAmount -= count * denomination.getDenomination();
            }
        }

        if (remainingAmount != 0) {
            System.out.println("Error: Cannot dispense the requested amount with available denominations.");
            return false;
        }

        withdrawal.forEach((denomination, count) -> cashCassettes.get(denomination).getBanknotes(count));
        System.out.println("Withdrawal successful: " + formatWithdrawal(withdrawal));
        return true;
    }

    private String formatWithdrawal(Map<Banknote, Integer> withdrawal) {
        StringBuilder sb = new StringBuilder();
        withdrawal.forEach((denomination, count) -> {
            sb.append(String.format("Banknote: %d, count: %d\n", denomination.getDenomination(), count));
        });
        return sb.toString();
    }

    @Override
    public int getBalance() {
        return cashCassettes.values().stream()
                .mapToInt(cassette -> cassette.getCurrCountBanknote() * cassette.getBanknoteType().getDenomination())
                .sum();
    }
}