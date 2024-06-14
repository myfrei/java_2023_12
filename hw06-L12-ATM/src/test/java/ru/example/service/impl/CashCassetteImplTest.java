package ru.example.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.example.model.enums.Banknote;
import ru.example.service.CashCassette;

import static org.junit.jupiter.api.Assertions.*;

class CashCassetteImplTest {

    private CashCassette cashCassette;

    @BeforeEach
    void setUp() {
        cashCassette = new CashCassetteImpl(Banknote.DENOMINATION_100, 100);
    }

    @Test
    void testAddBanknotes() {
        cashCassette.addBanknotes(50);
        assertEquals(50, cashCassette.getCurrCountBanknote());
        cashCassette.addBanknotes(50);
        assertEquals(100, cashCassette.getCurrCountBanknote());
    }

    @Test
    void testAddBanknotesInvalid() {
        assertThrows(RuntimeException.class, () -> cashCassette.addBanknotes(-10));
        assertThrows(RuntimeException.class, () -> {
            cashCassette.addBanknotes(101);
        });
    }

    @Test
    void testGetBanknotes() {
        cashCassette.addBanknotes(50);
        cashCassette.getBanknotes(20);
        assertEquals(30, cashCassette.getCurrCountBanknote());
    }

    @Test
    void testGetBanknotesInvalid() {
        cashCassette.addBanknotes(10);
        assertThrows(RuntimeException.class, () -> cashCassette.getBanknotes(20));
    }

    @Test
    void testSetOwnerAtm() {
        cashCassette.setOwnerAtm(1);
        assertEquals(1, cashCassette.getOwnerAtm());
    }

    @Test
    void testRemovedFromAtmCassette() {
        cashCassette.setOwnerAtm(1);
        cashCassette.removedFromAtmCassette();
        assertThrows(RuntimeException.class, () -> cashCassette.getOwnerAtm());
    }

    @Test
    void testClone() {
        cashCassette.addBanknotes(50);
        cashCassette.setOwnerAtm(1);
        CashCassette cloned = cashCassette.clone();
        assertEquals(cashCassette.getBanknoteType(), cloned.getBanknoteType());
        assertEquals(cashCassette.getMaxCountBanknotes(), cloned.getMaxCountBanknotes());
        assertEquals(cashCassette.getCurrCountBanknote(), cloned.getCurrCountBanknote());
        assertEquals(cashCassette.getOwnerAtm(), cloned.getOwnerAtm());
    }

    @Test
    void testIsInsideAtm() {
        cashCassette.setOwnerAtm(1);
        assertTrue(cashCassette.isInsideAtm());
        cashCassette.removedFromAtmCassette();
        assertFalse(cashCassette.isInsideAtm());
    }
}