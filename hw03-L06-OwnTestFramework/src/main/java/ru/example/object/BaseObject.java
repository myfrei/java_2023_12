package ru.example.object;

import java.util.Date;

public abstract class BaseObject {
    private final int id;
    private final Date createDate;

    public BaseObject() {
        this.id = getNextId();
        this.createDate = new Date();
    }

    protected abstract int getNextId();

    public int getId() {
        return id;
    }

    public Date getCreateDate() {
        return createDate;
    }
}