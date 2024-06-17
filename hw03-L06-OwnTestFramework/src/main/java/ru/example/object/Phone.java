package ru.example.object;

public final class Phone extends BaseObject {
    private static int nextId = 0;
    private final String number;

    public Phone(String number) {
        super();
        this.number = number;
    }

    @Override
    protected int getNextId() {
        return ++nextId;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Phone{" + "id=" + getId() + ", number='" + number + '\'' + '}';
    }
}
