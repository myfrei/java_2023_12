package ru.example.object;

public final class Address extends BaseObject {
    private static int nextId = 0;
    private final String city;
    private final String street;
    private final int houseNumber;

    public Address(String city, String street, int houseNumber) {
        super();
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    @Override
    protected int getNextId() {
        return ++nextId;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    @Override
    public String toString() {
        return "Address{" + "id="
                + getId() + ", city='"
                + city + '\'' + ", street='"
                + street + '\'' + ", houseNumber="
                + houseNumber + '}';
    }
}
