package ru.example.object;

import java.util.ArrayList;
import java.util.List;

public final class User extends BaseObject {
    private static int nextId = 0;
    private String name;
    private Address address;
    private List<Phone> phones = new ArrayList<>();

    public User(String name) {
        super();
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" + "id="
                + getId() + ", name='"
                + name + '\'' + ", address="
                + address + ", phones="
                + phones + '}';
    }

    @Override
    protected int getNextId() {
        return ++nextId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void addPhone(Phone phone) {
        phones.add(phone);
    }
}
