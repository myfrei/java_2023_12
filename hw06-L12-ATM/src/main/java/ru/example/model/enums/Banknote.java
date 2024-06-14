package ru.example.model.enums;

public enum Banknote {
    DENOMINATION_100 {
        @Override
        public int getDenomination() {
            return 100;
        }
    },
    DENOMINATION_500 {
        @Override
        public int getDenomination() {
            return 500;
        }
    },
    DENOMINATION_1000 {
        @Override
        public int getDenomination() {
            return 1000;
        }
    },
    DENOMINATION_5000 {
        @Override
        public int getDenomination() {
            return 5000;
        }
    };

    public abstract int getDenomination();
}