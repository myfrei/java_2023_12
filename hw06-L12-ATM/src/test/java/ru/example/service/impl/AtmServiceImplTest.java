package ru.example.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.example.model.enums.Banknote;
import ru.example.service.AtmService;
import ru.example.service.CashCassette;

import static org.junit.jupiter.api.Assertions.*;

class AtmServiceImplTest {

    private AtmService atmService;

    @BeforeEach
    void setUp() {
        atmService = new AtmServiceImpl();
    }

    @Test
    void testAddCashCassette() {
        CashCassette cassette = new CashCassetteImpl(Banknote.DENOMINATION_100, 100);
        atmService.addCashCassette(cassette);
        atmService.deposit(Banknote.DENOMINATION_100, 50);
        assertEquals(5000, atmService.getBalance()); // 50 * 100
    }

    @Test
    void testRemoveCashCassette() {
        CashCassette cassette = new CashCassetteImpl(Banknote.DENOMINATION_100, 100);
        atmService.addCashCassette(cassette);
        atmService.removeCashCassette(Banknote.DENOMINATION_100);
        assertThrows(RuntimeException.class, () -> atmService.deposit(Banknote.DENOMINATION_100, 50));
    }

    @Test
    void testDeposit() {
        CashCassette cassette = new CashCassetteImpl(Banknote.DENOMINATION_100, 100);
        atmService.addCashCassette(cassette);
        atmService.deposit(Banknote.DENOMINATION_100, 50);
        assertEquals(5000, atmService.getBalance());
    }

    @Test
    void testWithdrawSuccessful() {
        CashCassette cassette100 = new CashCassetteImpl(Banknote.DENOMINATION_100, 100);
        CashCassette cassette500 = new CashCassetteImpl(Banknote.DENOMINATION_500, 100);

        atmService.addCashCassette(cassette100);
        atmService.addCashCassette(cassette500);

        atmService.deposit(Banknote.DENOMINATION_100, 50);
        atmService.deposit(Banknote.DENOMINATION_500, 20);

        atmService.getBalance();
        assertTrue(atmService.withdraw(2000));
        assertEquals(13000, atmService.getBalance());
    }

    @Test
    void testWithdrawUnsuccessful() {
        CashCassette cassette100 = new CashCassetteImpl(Banknote.DENOMINATION_100, 100);
        atmService.addCashCassette(cassette100);
        atmService.deposit(Banknote.DENOMINATION_100, 20);
        assertFalse(atmService.withdraw(3000));
    }

    @Test
    void testGetBalance() {
        CashCassette cassette100 = new CashCassetteImpl(Banknote.DENOMINATION_100, 100);
        CashCassette cassette500 = new CashCassetteImpl(Banknote.DENOMINATION_500, 100);

        atmService.addCashCassette(cassette100);
        atmService.addCashCassette(cassette500);

        atmService.deposit(Banknote.DENOMINATION_100, 50);
        atmService.deposit(Banknote.DENOMINATION_500, 20);

        assertEquals(15000, atmService.getBalance());
    }
}